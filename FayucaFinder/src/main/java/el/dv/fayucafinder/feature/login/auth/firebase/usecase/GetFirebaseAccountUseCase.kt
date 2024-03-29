package el.dv.fayucafinder.feature.login.auth.firebase.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.fayucafinder.feature.login.auth.firebase.FirebaseAuthenticationProvider
import el.dv.fayucafinderdata.network.auth.model.AuthAccount
import el.dv.fayucafinderdata.network.auth.model.AuthData

class GetFirebaseAccountUseCase(
    private val authProvider: FirebaseAuthenticationProvider
) : SuspendUseCase<AuthData<Task<AuthResult>>, Result<AuthAccount<FirebaseUser>>> {

    override suspend fun run(authData: AuthData<Task<AuthResult>>): Result<AuthAccount<FirebaseUser>> {
        return authProvider.getAuthAccount(authData = authData)
    }
}
