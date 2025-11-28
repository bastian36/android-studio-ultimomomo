package model

data class ApiProductResponse(
    val success: Boolean,
    val message: String,
    val data: ProductData
)

data class ProductData(
    val productos: List<ApiProduct>,
    val pagination: Pagination
)

data class ApiProduct(
    val id_producto: Int,
    val nombre_producto: String,
    val cantidad_producto: Int,
    val valor_producto: Int,
    val id_categoria: Int,
    val imagen: String?,
    val categoria: Categoria
)

data class Categoria(
    val id_categoria: Int,
    val nombre_categoria: String
)

data class Pagination(
    val total: Int,
    val page: Int,
    val limit: Int,
    val totalPages: Int
)
