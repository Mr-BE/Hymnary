package dev.mrbe.hymnary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HymnsViewModel(val hymnsRepo: HymnRepo) : ViewModel() {

    val hymnsStateFlow = MutableStateFlow<HymnsResponse?>(null)

    init {
        viewModelScope.launch {
            hymnsRepo.getHymnDetails().collect {
                hymnsStateFlow.value = it
            }
        }
    }

    fun getHymnsInfo() = hymnsRepo.getHymnDetails()
}