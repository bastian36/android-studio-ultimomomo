package model

data class ImageUploadRequest(
    val archivo: String,
    val nombre: String,
    val tipo: String
)

data class ImageUploadResponse(
    val success: Boolean,
    val message: String,
    val data: ImageData?
)

data class ImageData(
    val id_imagen: Int,
    val nombre: String,
    val tipo: String,
    val archivo: String,
    val created_at: String,
    val updated_at: String
)
