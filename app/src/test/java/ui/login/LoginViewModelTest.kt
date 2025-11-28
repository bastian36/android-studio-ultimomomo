package ui.login

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Pruebas unitarias para LoginViewModel
 * Valida la lógica de autenticación sin necesidad de Firebase
 */
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `email valido actualiza el estado correctamente`() {
        val emailValido = "test@duocuc.cl"
        viewModel.onEmailChange(emailValido)
        
        assertEquals(emailValido, viewModel.ui.value.email)
        assertNull(viewModel.ui.value.error)
    }

    @Test
    fun `password valido actualiza el estado correctamente`() {
        val passwordValido = "123456"
        viewModel.onPasswordChange(passwordValido)
        
        assertEquals(passwordValido, viewModel.ui.value.password)
        assertNull(viewModel.ui.value.error)
    }

    @Test
    fun `estado inicial es correcto`() {
        assertEquals("", viewModel.ui.value.email)
        assertEquals("", viewModel.ui.value.password)
        assertFalse(viewModel.ui.value.loading)
        assertNull(viewModel.ui.value.error)
        assertFalse(viewModel.ui.value.loggedIn)
    }

    @Test
    fun `cambiar email limpia el error previo`() {
        viewModel.onEmailChange("invalido")
        viewModel.onEmailChange("nuevo@duocuc.cl")
        
        assertNull(viewModel.ui.value.error)
    }

    @Test
    fun `cambiar password limpia el error previo`() {
        viewModel.onPasswordChange("123")
        viewModel.onPasswordChange("123456")
        
        assertNull(viewModel.ui.value.error)
    }
}
