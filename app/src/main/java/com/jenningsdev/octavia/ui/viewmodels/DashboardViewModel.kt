package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenningsdev.octavia.data.repositories.WebSocketClient
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {
    val webSocketClient: WebSocketClient = WebSocketClient()

    init {
        viewModelScope.launch {
            webSocketClient.connectToStream()
        }
    }
}