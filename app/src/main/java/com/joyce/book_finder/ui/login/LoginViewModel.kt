package com.joyce.book_finder.ui.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.joyce.book_finder.utils.InputWrapper
import com.joyce.book_finder.utils.validEmailFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    var emailInput by mutableStateOf(InputWrapper(onValueChange = ::onEnterEmail))
    var passwordInput by mutableStateOf(InputWrapper(onValueChange = ::onEnterPassword))

    private var _state = MutableStateFlow<LoginState?>(null)
    val state: StateFlow<LoginState?>
    get() = _state

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private var isPasswordEnable by mutableStateOf(false)

    var isEnableButton by mutableStateOf(false)

    private fun isEnableButton() {
        isEnableButton = !emailInput.inputError.hasError && emailInput.value.isNotEmpty()
                && !passwordInput.inputError.hasError && passwordInput.value.isNotEmpty()
                && isPasswordEnable
    }

    private fun onEnterEmail(value: String) {
        emailInput.value = value
        when {
            value.isEmpty() -> {
                emailInput.inputError.hasError = true
                emailInput.inputError.errorMessage = REQUIRED_FIELD
            }
            !value.validEmailFormat() -> {
                emailInput.inputError.hasError = true
                emailInput.inputError.errorMessage = EMAIL_INVALID
            }
            else -> {
                emailInput.inputError.hasError = false
                emailInput.inputError.errorMessage = null
            }
        }
        isEnableButton()
    }

    private fun onEnterPassword(value: String) {
        if (value.length >= 6) {
            isPasswordEnable = true
            isEnableButton()

        }else{
            isPasswordEnable = false
        }
        passwordInput.value = value
        when {
            value.isEmpty() || value.isBlank() -> {
                passwordInput.inputError.hasError = true
                passwordInput.inputError.errorMessage = REQUIRED_FIELD
            }
            else -> {
                passwordInput.inputError.hasError = false
                passwordInput.inputError.errorMessage = null
            }
        }
        isEnableButton()
    }

    fun login() = viewModelScope.launch{
        _loading.value = true
         val auth: FirebaseAuth = Firebase.auth
        auth.signInWithEmailAndPassword(emailInput.value.trim(), passwordInput.value.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loading.value = false
                    _state.value = LoginState.Success
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    _loading.value = false
                    _state.value = LoginState.Error(task.exception?.message!!)
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                }
            }
    }

    companion object{
        private const val REQUIRED_FIELD = "Campo obrigatorio"
        private const val EMAIL_INVALID = "Email invalido"
        private const val TAG = "FirebaseAuth"
    }
}
sealed class LoginState{
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}