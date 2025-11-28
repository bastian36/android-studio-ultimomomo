package model

import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para el modelo Product
 * Valida la creación y propiedades de productos
 */
class ProductTest {

    @Test
    fun `crear producto con datos validos`() {
        val product = Product(
            id = 1,
            name = "PlayStation 5",
            description = "Consola de última generación",
            price = 499.99,
            imageUrl = "https://example.com/ps5.jpg",
            category = "Consolas"
        )
        
        assertEquals(1, product.id)
        assertEquals("PlayStation 5", product.name)
        assertEquals("Consola de última generación", product.description)
        assertEquals(499.99, product.price, 0.01)
        assertEquals("https://example.com/ps5.jpg", product.imageUrl)
        assertEquals("Consolas", product.category)
    }

    @Test
    fun `dos productos con mismo id son iguales`() {
        val product1 = Product(1, "PS5", "Consola", 499.99, "url", "Consolas")
        val product2 = Product(1, "PS5", "Consola", 499.99, "url", "Consolas")
        
        assertEquals(product1, product2)
    }

    @Test
    fun `dos productos con diferente id son diferentes`() {
        val product1 = Product(1, "PS5", "Consola", 499.99, "url", "Consolas")
        val product2 = Product(2, "PS5", "Consola", 499.99, "url", "Consolas")
        
        assertNotEquals(product1, product2)
    }

    @Test
    fun `producto con precio cero es valido`() {
        val product = Product(1, "Gratis", "Producto gratis", 0.0, "url", "Promo")
        
        assertEquals(0.0, product.price, 0.01)
    }

    @Test
    fun `producto puede tener descripcion vacia`() {
        val product = Product(1, "Producto", "", 10.0, "url", "Varios")
        
        assertEquals("", product.description)
    }
}
