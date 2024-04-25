package com.example.miseya

import DustItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    init {
        loadDustData()
    }

    private fun loadDustData() {
//        viewModelScope.launch {
//            val dustData = repository.getDustData() // 서버에서 데이터 가져오기
//            _state.value = state.value.copy(items = dustData)
//        }
    }
}

data class MainState(
    val items: List<DustItem> = emptyList()
)