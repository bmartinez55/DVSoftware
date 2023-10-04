package el.dv.data.fayucafinder.di

import com.google.firebase.database.FirebaseDatabase
import el.dv.data.fayucafinder.network.truck.api.FayucaFinderTruckApi
import el.dv.data.fayucafinder.network.truck.api.TruckApi
import el.dv.data.fayucafinder.network.user.api.UserApi
import el.dv.data.fayucafinder.network.user.api.firebase.FirebaseUserApi
import el.dv.data.util.Const
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This module will hold all api level services
 */
val fayucaFinderServiceModule = module {
    /**
     * Firebase Databases references
     */
    single(named("truckDB")) {
        FirebaseDatabase.getInstance(Const.FIREBASE_DATABASE_TRUCK_URL)
    }

    single(named("truckDetailsDB")) {
        FirebaseDatabase.getInstance(Const.FIREBASE_DATABASE_TRUCK_DETAILS_URL)
    }

    single(named("userDB")) {
        FirebaseDatabase.getInstance(Const.FIREBASE_DATABASE_USER_URL)
    }

    /**
     * Api service classes
     */
    single<TruckApi> {
        FayucaFinderTruckApi(truckDatabase = get(named("truckDB")), truckDetailsDatabase = get(named("truckDetailsDB")))
    }

    single<UserApi> {
        FirebaseUserApi(userDatabase = get(named("userDB")))
    }
}
