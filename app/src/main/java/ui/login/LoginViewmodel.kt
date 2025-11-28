package ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui

    fun onEmailChange(v: String) = _ui.update { it.copy(email = v, error = null) }
    fun onPasswordChange(v: String) = _ui.update { it.copy(password = v, error = null) }

    private fun validar(): String? {
        val s = _ui.value
        if (!Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) return "Email inválido"
        if (s.password.length < 6) return "La clave debe tener al menos 6 caracteres"
        return null
    }

    fun submit() {
        val err = validar()
        if (err != null) {
            _ui.update { it.copy(error = err) }
            return
        }
        
        _ui.update { it.copy(loading = true, error = null) }
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://10.0.2.2:3000/api/auth/login")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                
                val jsonBody = JSONObject().apply {
                    put("correo", _ui.value.email)
                    put("password", _ui.value.password)
                }
                
                connection.outputStream.use { it.write(jsonBody.toString().toByteArray()) }
                
                val responseCode = connection.responseCode
                
                withContext(Dispatchers.Main) {
                    if (responseCode == 200 || responseCode == 201) {
                        _ui.update { it.copy(loading = false, loggedIn = true) }
                    } else {
                        val error = connection.errorStream?.bufferedReader()?.readText() ?: "Error $responseCode"
                        _ui.update { it.copy(loading = false, error = error) }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _ui.update { it.copy(loading = false, error = e.message ?: "Error de conexión") }
                }
            }
        }
    }
}