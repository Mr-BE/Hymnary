package dev.mrbe.hymnary

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class HymnRepo {

    private val firestore = FirebaseFirestore.getInstance()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getHymnDetails() = callbackFlow {

//        val collection = firestore.collection("hymns")
//            .document("rch")
//            .collection("rchHymns")

//        val collection = firestore.collection("books")
        val collection = firestore.collection("hymns")
            .document("rch")
            .collection("rchHymns")

        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                Timber.d("Tag, Response is not error. No of documents is ->  ${value?.documents?.size}")
                OnSuccess(value)
            } else {
                Timber.d("Tag, Response is error -> $error")
                OnError(error)
            }

            trySend(response)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}

//class BooksRepository(private val queryBooksByName: Query){
//
//    suspend fun getProductsFromFirestore(): DataOrException<List<Book>, Exception> {
//        val dataOrException = DataOrException<List<Book>, Exception>()
//
//        try {
//            dataOrException.data = queryBooksByName.get().map{}
//        }
//    }
//}