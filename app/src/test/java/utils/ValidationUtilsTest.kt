package utils

import android.util.Patterns
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para validaciones comunes
 * Útil para validar formularios en toda la app
 */
class ValidationUtilsTest {

    @Test
    fun `email valido retorna true`() {
        val emails = listOf(
            "test@duocuc.cl",
            "ba.guajardoh@duocuc.cl",
            "usuario@gmail.com",
            "admin@gaming-store.com"
        )
        
        emails.forEach { email ->
            assertTrue("$email debería ser válido", isValidEmail(email))
        }
    }

    @Test
    fun `email invalido retorna false`() {
        val emails = listOf(
            "",
            "invalido",
            "@duocuc.cl",
            "test@",
            "test @duocuc.cl",
            "test..test@duocuc.cl"
        )
        
        emails.forEach { email ->
            assertFalse("$email debería ser inválido", isValidEmail(email))
        }
    }

    @Test
    fun `password con 6 caracteres es valido`() {
        assertTrue(isValidPassword("123456"))
        assertTrue(isValidPassword("abcdef"))
        assertTrue(isValidPassword("Pass12"))
    }

    @Test
    fun `password con menos de 6 caracteres es invalido`() {
        assertFalse(isValidPassword(""))
        assertFalse(isValidPassword("12345"))
        assertFalse(isValidPassword("abc"))
    }

    @Test
    fun `password con mas de 6 caracteres es valido`() {
        assertTrue(isValidPassword("1234567"))
        assertTrue(isValidPassword("MiPasswordSeguro123"))
    }

    @Test
    fun `precio valido es mayor a cero`() {
        assertTrue(isValidPrice(0.01))
        assertTrue(isValidPrice(10.0))
        assertTrue(isValidPrice(499.99))
    }

    @Test
    fun `precio invalido es cero o negativo`() {
        assertFalse(isValidPrice(0.0))
        assertFalse(isValidPrice(-1.0))
        assertFalse(isValidPrice(-499.99))
    }

    @Test
    fun `nombre de producto no puede estar vacio`() {
        assertFalse(isValidProductName(""))
        assertFalse(isValidProductName("   "))
        assertTrue(isValidProductName("PlayStation 5"))
    }

    // Funciones auxiliares de validación
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidPrice(price: Double): Boolean {
        return price > 0.0
    }

    private fun isValidProductName(name: String): Boolean {
        return name.trim().isNotEmpty()
    }
}
