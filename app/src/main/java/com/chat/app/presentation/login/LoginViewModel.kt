package com.chat.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chat.app.data.repository.ChatRepository
import com.chat.app.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val isSigninMode: Boolean = false   // false = login, true = rejestracja
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            if (repository.isLoggedIn()) {
                _uiState.update { it.copy(isLoggedIn = true) }
            }
        }
    }

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun toggleMode() {
        _uiState.update { it.copy(isSigninMode = !it.isSigninMode, error = null) }
    }

    fun submit() {
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password

        if (username.isBlank()) {
            _uiState.update { it.copy(error = "Username cannot be empty") }
            return
        }
        if (password.isBlank()) {
            _uiState.update { it.copy(error = "Password cannot be empty") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = if (_uiState.value.isSigninMode) {
                repository.signin(username, password)
            } else {
                repository.login(username, password)
            }
            when (result) {
                is Result.Success -> _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
                is Result.Error   -> _uiState.update { it.copy(isLoading = false, error = result.message) }
            }
        }
    }
}
