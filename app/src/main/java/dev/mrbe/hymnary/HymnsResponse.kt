package dev.mrbe.hymnary

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class HymnsResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?) : HymnsResponse()
data class OnError(val exception: FirebaseFirestoreException?) : HymnsResponse()


data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)