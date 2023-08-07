package el.dv.fayucafinder.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import el.dv.data.network.auth.model.AuthCallback
import el.dv.data.network.auth.model.AuthData
import el.dv.data.network.auth.model.IntentData
import el.dv.fayucafinder.R
import el.dv.fayucafinder.core.view.FayucaFinderVM
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationFactoryForGoogle
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProvider
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProviderForGoogleAccount
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.FirebaseGoogleSignOutUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.FirebaseSignOutUserUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.GetFirebaseAccountForGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.GetFirebaseAccountUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.usecase.SignInWithFirebaseGoogleUseCase
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationActivityResultContract
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationFactory
import el.dv.fayucafinder.feature.login.auth.google.GoogleAuthenticationProvider
import el.dv.fayucafinder.feature.login.auth.google.usecase.GetGoogleAccountUseCase
import el.dv.fayucafinder.feature.login.auth.google.usecase.SignInWithGoogleUseCase
import el.dv.fayucafinder.feature.login.view.LoginVM
import el.dv.fayucafinder.feature.map.FayucaFinderMapLifecycleObserver
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationVM
import el.dv.fayucafinder.feature.map.bottomsheet.viewreducer.GetMapConfigurationInitViewStateViewReducer
import el.dv.fayucafinder.feature.map.viewreducer.GetNavigationMapCenterLocationUpdateViewReducer
import el.dv.fayucafinder.util.Const
import el.dv.presentation.extension.ActionListener
import el.dv.presentation.sensor.AccelerometerSensor
import el.dv.presentation.sensor.GyroscopeSensor
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.dialog.DialogManagerVertical
import el.dv.presentation.view.manager.notification.NotificationManager
import el.dv.presentation.view.manager.notification.NotificationManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    /**
     * ViewModel related dependencies
     */
    viewModel {
        FayucaFinderVM(firebaseAuthFactoryForGoogle = get())
    }

    viewModel {
        LoginVM(appDictionary = get())
    }

    viewModel {
        FayucaFinderMapVM(
            getLocationUseCase = get(),
            stopLocationUseCase = get(),
            saveStringInSharedPreferencesUseCase = get(),
            loadStringFromSharedPreferencesUseCase = get(),
            startNetworkConnectivityMonitorUseCase = get(),
            getNavigationMapCenterLocationUpdateViewReducer = get(),
            permissionFactory = get(),
            appDictionary = get(),
            eventBus = get(),
            context = androidContext()
        )
    }

    viewModel {
        MapConfigurationVM(
            getMapConfigurationInitViewStateViewReducer = get(),
            eventBus = get()
        )
    }

    /**
     * Lifecycle related dependencies
     */

    factory { (lifecycleOwner: LifecycleOwner, OnResumeAction: ActionListener, OnPauseAction: ActionListener) ->
        FayucaFinderMapLifecycleObserver(lifecycleOwner = lifecycleOwner, onResumeAction = OnResumeAction, onPauseAction = OnPauseAction)
    }

    /**
     * Factory related dependencies
     */

    factory { (activityResultContract: GoogleAuthenticationActivityResultContract, authCallback: AuthCallback<AuthData<IntentData>>, fragment: Fragment, context: Context) ->
        GoogleAuthenticationProvider(activityResultContract = activityResultContract, authCallback = authCallback, fragment = fragment, context = context)
    }

    factory { (callback: AuthCallback<AuthData<Task<AuthResult>>>) ->
        FirebaseAuthenticationProviderForGoogleAccount(authCallback = callback)
    }

    single {
        GoogleAuthenticationFactory()
    }

    single {
        FirebaseAuthenticationFactoryForGoogle()
    }

    /**
     * Activity Contracts
     */

    factory {
        GoogleAuthenticationActivityResultContract()
    }

    /**
     * UseCases related dependencies
     */

    factory {
        SignInWithFirebaseGoogleUseCase(authenticationProviderForGoogleAccount = get())
    }

    factory { (authProvider: GoogleAuthenticationProvider) ->
        SignInWithGoogleUseCase(authenticationProvider = authProvider)
    }

    factory { (authProvider: FirebaseAuthenticationProvider) ->
        GetFirebaseAccountUseCase(authProvider = authProvider)
    }

    factory {
        GetFirebaseAccountForGoogleUseCase(authenticationProviderForGoogleAccount = get())
    }

    factory { (authProvider: GoogleAuthenticationProvider) ->
        GetGoogleAccountUseCase(authenticationProvider = authProvider)
    }

    single {
        FirebaseSignOutUserUseCase(authenticationProviderForGoogleAccount = get())
    }

    factory {
        FirebaseGoogleSignOutUseCase(context = get())
    }

    /**
     * ViewReducer related dependencies
     */

    factory {
        GetMapConfigurationInitViewStateViewReducer(appDictionary = get())
    }

    factory {
        GetNavigationMapCenterLocationUpdateViewReducer()
    }

    /**
     * Manager related dependencies
     */

    factory<DialogManager> {
        DialogManagerVertical(themeStyleResId = R.style.FayucaFinderDialogTheme)
    }

    factory<NotificationManager> {
        NotificationManagerImpl(notificationManager = get(), context = androidContext())
    }

    /**
     * Device related dependencies
     */

    single(named("accelerometer")) {
        AccelerometerSensor(context = androidContext())
    }

    single(named("gyroscope")) {
        GyroscopeSensor(context = androidContext())
    }

    single {
        androidContext().getSharedPreferences(Const.COMMON_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }
}
