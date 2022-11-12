package el.dv.data.network.truck.api

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import el.dv.data.network.truck.model.FirebaseTruck
import el.dv.data.network.truck.model.FirebaseTruckDetails
import el.dv.data.network.truck.model.toFirebaseTruck
import el.dv.data.network.truck.model.toFirebaseTruckDetails
import el.dv.data.network.truck.model.toFirebaseUpdateTruckOperationStatusRequest
import el.dv.domain.core.Geolocation
import el.dv.domain.core.Result
import el.dv.domain.extension.offerWhenOpen
import el.dv.domain.logging.AppLog
import el.dv.domain.truck.repository.Accessibility
import el.dv.domain.truck.repository.AddTruckRequest
import el.dv.domain.truck.repository.ServiceTime
import el.dv.domain.truck.repository.Truck
import el.dv.domain.truck.repository.TruckDetails
import el.dv.domain.truck.repository.TruckGenre
import el.dv.domain.truck.repository.UpdateTruckOperationStatusRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.suspendCoroutine

class FayucaFinderTruckApi(
    private val truckDatabase: FirebaseDatabase,
    private val truckDetailsDatabase: FirebaseDatabase
) : TruckApi {

    override suspend fun addTruck(addTruckRequest: AddTruckRequest): Result<Boolean> = suspendCoroutine { continuation ->
        try {
            val key = truckDatabase.reference.child(addTruckRequest.truck.ownerId).push().key
            key?.let {
                truckDatabase.reference
                    .child(addTruckRequest.truck.ownerId)
                    .child(it)
                    .setValue(addTruckRequest.truck.toFirebaseTruck())
                    .addOnSuccessListener {
                        AppLog.d(TAG, "addTruck success")
                        truckDetailsDatabase.reference
                            .child(addTruckRequest.truck.truckId)
                            .setValue(addTruckRequest.truckDetails.toFirebaseTruckDetails())
                            .addOnSuccessListener {
                                AppLog.d(TAG, "addTruckDetails sucess")
                                continuation.resumeWith(kotlin.Result.success(Result.Success(true)))
                            }
                            .addOnFailureListener { e ->
                                AppLog.e(TAG, "addTruckDetails failure error", e)
                                continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                            }
                    }
                    .addOnFailureListener { e ->
                        AppLog.e(TAG, "addTruck failure error", e)
                        continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                    }
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "addTruck catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    override suspend fun getTruckDetails(truckId: String): Result<TruckDetails> = suspendCoroutine { continuation ->
        try {
            truckDetailsDatabase.reference
                .child(truckId)
                .get()
                .addOnSuccessListener { data ->
                    data.getValue(FirebaseTruckDetails::class.java)?.let { firebaseTruckDetails ->
                        continuation.resumeWith(kotlin.Result.success(Result.Success(firebaseTruckDetails.toTruckDetails())))
                    }
                }
                .addOnFailureListener { e ->
                    AppLog.e(TAG, "getTruckDetails failure error", e)
                    continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                }
        } catch (e: Exception) {
            AppLog.e(TAG, "getTruckDetails catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    override suspend fun getTrucks(): Flow<Result<List<Truck>>> = callbackFlow {
        try {
            val truckListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    when (snapshot.hasChildren()) {
                        true -> offerWhenOpen(Result.Success(snapshot.toFirebaseTruck().toListOfTruck()))
                        false -> {}
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    AppLog.e(TAG, "getTrucks Valuelistener onCancelled error", Throwable(error.message))
                }
            }
            truckDatabase.reference.addValueEventListener(truckListener)
        } catch (e: Exception) {
            AppLog.e(TAG, "getTruckList catch exception error", e)
            offerWhenOpen(Result.Failure(e))
        }
    }

    override suspend fun getTrucksFromOwner(ownerId: String): Result<List<Truck>> = suspendCoroutine { continuation ->
        try {
            truckDatabase.reference
                .child(ownerId)
                .get()
                .addOnSuccessListener { data ->
                    when (data.hasChildren()) {
                        true -> {
                            AppLog.d(TAG, "getTrucksFromOwner success")
                            continuation.resumeWith(kotlin.Result.success(Result.Success(data.toFirebaseTruck().toListOfTruck())))
                        }
                        false -> {
                            AppLog.d(TAG, "getTruckListFromOwner No Truck Data")
                            continuation.resumeWith(kotlin.Result.success(Result.Failure(Exception("No Truck Data"))))
                        }
                    }
                }
                .addOnFailureListener { e ->
                    AppLog.e(TAG, "getTruckListFromOwner failure error", e)
                    continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                }
        } catch (e: Exception) {
            AppLog.e(TAG, "getTruckListFromOwner catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    override suspend fun updateTruckOperationStatus(
        updateTruckOperationStatusRequest: UpdateTruckOperationStatusRequest
    ): Result<Boolean> = suspendCoroutine { continuation ->
        try {
            truckDatabase.reference
                .child(updateTruckOperationStatusRequest.ownerId)
                .child(updateTruckOperationStatusRequest.truckId)
                .updateChildren(updateTruckOperationStatusRequest.toFirebaseUpdateTruckOperationStatusRequest().toMap())
                .addOnSuccessListener {
                    AppLog.d(TAG, "updateTruckOperationStatus success")
                    continuation.resumeWith(kotlin.Result.success(Result.Success(true)))
                }
                .addOnFailureListener { e ->
                    AppLog.e(TAG, "updateTruckOperationStatus failure error", e)
                    continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
                }
        } catch (e: Exception) {
            AppLog.e(TAG, "updateTruckOperationStatus catch exception error", e)
            continuation.resumeWith(kotlin.Result.success(Result.Failure(e)))
        }
    }

    companion object {
        const val TAG = "FayucaFinderTruckApi"
    }
}

private fun DataSnapshot.toFirebaseTruck(): List<FirebaseTruck> {
    return this.children.mapNotNull { it.getValue(FirebaseTruck::class.java) }
}

private fun List<FirebaseTruck>.toListOfTruck(): List<Truck> {
    return this.map { it.toTruck() }
}

private fun FirebaseTruck.toTruck(): Truck {
    return Truck(
        truckId = this.truckId,
        name = this.name,
        location = Geolocation(lat = this.latitude, lon = this.longitude),
        isOpen = this.isOpen,
        serviceTime = ServiceTime(startTime = this.startTime, endTime = this.endTime),
        ownerId = this.ownerId
    )
}

private fun FirebaseTruckDetails.toTruckDetails(): TruckDetails {
    return TruckDetails(
        truckId = this.truckId,
        description = this.description,
        genres = this.genres.toListOfTruckGenres(),
        accessibilities = this.accessibilities.toListOfAccessibilities(),
        ownerId = this.ownerId
    )
}

private fun String.toListOfTruckGenres(): List<TruckGenre> {
    return this.split(",").map { it.toTruckGenre() }
}

private fun String.toTruckGenre(): TruckGenre {
    return when (this) {
        TruckGenre.Mexican.name -> TruckGenre.Mexican
        else -> TruckGenre.Unspecified
    }
}

private fun String.toListOfAccessibilities(): List<Accessibility> {
    return this.split(",").map { it.toAccessibility() }
}

private fun String.toAccessibility(): Accessibility {
    return when (this) {
        Accessibility.WheelChair.name -> Accessibility.WheelChair
        else -> Accessibility.Unspecified
    }
}
