package dev.mrbe.hymnary.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mrbe.hymnary.Hymn

class ContentViewModel(hymn: Hymn) : ViewModel() {

    private val _receivedHymn = MutableLiveData<Hymn>()
    val receivedHymn: LiveData<Hymn>
        get() = _receivedHymn

    init {
        _receivedHymn.value = hymn
    }

//    fun getReceivedHymn(hymn: Hymn) {
//        _receivedHymn.postValue(hymn)
//    }


    class ContentViewModelFactory(private val hymn: Hymn) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <viewModel : ViewModel?> create(modelClass: Class<viewModel>): viewModel {
            if (modelClass.isAssignableFrom(ContentViewModel::class.java)) {
                return ContentViewModel(hymn) as viewModel
            }
            throw IllegalArgumentException("viewModel error")
        }
    }
}