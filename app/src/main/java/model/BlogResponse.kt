package model

data class BlogResponse(
    val success: Boolean,
    val message: String,
    val data: List<BlogData>
)

data class BlogData(
    val id_blog: Int,
    val titulo: String,
    val texto: String,
    val imagen: String?,
    val created_at: String,
    val updated_at: String
)
