package el.dv.data.di.scope

import com.google.firebase.database.FirebaseDatabase
import el.dv.data.network.truck.api.FayucaFinderTruckApi
import el.dv.data.network.truck.api.TruckApi
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

    /**
     * Api service classes
     */
    single<TruckApi> {
        FayucaFinderTruckApi(truckDatabase = get(), truckDetailsDatabase = get())
    }
}
