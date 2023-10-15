package el.dv.fayucafinder.core.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import el.dv.data.fayucafinder.di.SignInUserScope
import el.dv.data.fayucafinder.di.UserScopeData
import el.dv.data.fayucafinder.di.fayucaFinderUserScope
import el.dv.data.fayucafinder.network.auth.model.AuthCallback
import el.dv.data.fayucafinder.network.auth.model.AuthData
import el.dv.domain.core.Result
import el.dv.domain.extension.isNotNullAndNotEmpty
import el.dv.domain.logging.AppLog
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationFactoryForGoogle
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.GetSignedInFirebaseGoogleAccountUseCase
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FayucaFinderVM(
    private val firebaseAuthFactoryForGoogle: FirebaseAuthenticationFactoryForGoogle
) : ViewModel() {

    private val eventChannel = Channel<FayucaFinderViewEvent>(Channel.UNLIMITED)

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private lateinit var firebaseAuthProvider: FirebaseAuthenticationProviderForGoogleAccount
    private lateinit var checkIfUserIsAuthenticatedWithFirebaseGoogleUseCase: CheckIfUserIsAuthenticatedWithFirebaseGoogleUseCase
    private lateinit var getSignedInFirebaseGoogleAccountUseCase: GetSignedInFirebaseGoogleAccountUseCase

    init {
        eventChannel
            .consumeAsFlow()
            .catch { error ->
                AppLog.d(TAG, "event error $error")
            }
            .onEach { event ->
                AppLog.d(TAG, "event ${event.javaClass.simpleName}")
                when (event) {
                    is FayucaFinderViewEvent.Init -> handleInitEvent(event)
                }
            }.launchIn(viewModelScope)
    }

    fun handleEvent(event: FayucaFinderViewEvent) {
        eventChannel.trySend(event)
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect ${viewEffect.javaClass.simpleName}")
        internalViewEffect.value = viewEffect
    }

    private fun handleInitEvent(event: FayucaFinderViewEvent.Init) {
        AppLog.d(TAG, "handleInitEvent")
        val authCallback = AuthCallback<AuthData<Task<AuthResult>>> {}
        firebaseAuthProvider = firebaseAuthFactoryForGoogle.getAuthProvider(authCallback)
        checkIfUserIsAuthenticatedWithFirebaseGoogleUseCase = firebaseAuthFactoryForGoogle.getCheckIfUserIsAuthenticatedUseCase(firebaseAuthProvider)
        getSignedInFirebaseGoogleAccountUseCase = firebaseAuthFactoryForGoogle.getGetCurrentSignedInAccountUseCase(firebaseAuthProvider)

        checkAuthentication()
    }

    private fun checkAuthentication() {
        AppLog.d(TAG, "checkAuthentication")
        viewModelScope.launch {
            when (checkIfUserIsAuthenticatedWithFirebaseGoogleUseCase.run(Unit)) {
                true -> getAuthenticatedAccount()
                false -> handleAuthenticationFailure()
            }
        }
    }

    private fun getAuthenticatedAccount() {
        AppLog.d(TAG, "getAuthenticatedAccount")
        viewModelScope.launch {
            when (val firebaseResult = getSignedInFirebaseGoogleAccountUseCase.run(Unit)) {
                is Result.Success -> {
                    firebaseResult.data.userAccount.getIdToken(true).addOnCompleteListener { tokenResultTask ->
                        when {
                            tokenResultTask.isSuccessful && tokenResultTask.result?.token.isNotNullAndNotEmpty() -> {
                                fayucaFinderUserScope = SignInUserScope(
                                    UserScopeData.UserData(
                                        userTokenId = requireNotNull(tokenResultTask.result?.token),
                                        userEmail = requireNotNull(firebaseResult.data.userAccount.email),
                                        tokenIdExpirationEpochTime = tokenResultTask.result?.expirationTimestamp ?: 0
                                    )
                                )
                                handleAuthenticationSuccess(firebaseResult.data.userAccount.email ?: "")
                            }
                            else -> handleAuthenticationFailure()
                        }
                    }
                }
                is Result.Failure -> handleAuthenticationFailure()
            }
        }
    }

    private fun handleAuthenticationSuccess(email: String) {
        AppLog.d(TAG, "handleAuthenticationSuccess")
        sendViewEffect(ViewEffect.NavigateToDirection(FayucaFinderFragmentDirections.actionFayucaFinderFragmentToBottomNavigationFragment()))
        // TODO(Add UserApi to get User details from database)
    }

    private fun handleAuthenticationFailure() {
        AppLog.d(TAG, "handleAuthenticationFailure")
        sendViewEffect(ViewEffect.NavigateToDirection(FayucaFinderFragmentDirections.actionFayucaFinderFragmentToLoginFragment()))
    }

    companion object {
        const val TAG = "FayucaFinderVM"
    }
}
