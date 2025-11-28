package ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.ImageUploadRequest
import utils.ImageUtils

class ProfileViewModel : ViewModel() {
    private val apiService = ImageApiService.create()
    
    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState
    
    fun uploadImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                _uploadState.value = UploadState.Loading
                
                val base64Image = ImageUtils.uriToBase64(context, uri)
                if (base64Image == null) {
                    _uploadState.value = UploadState.Error("Error al convertir imagen")
                    return@launch
                }
                
                val fileName = "profile_${System.currentTimeMillis()}.png"
                val request = ImageUploadRequest(
                    archivo = base64Image,
                    nombre = fileName,
                    tipo = "image/png"
                )
                
                val response = apiService.uploadImage(request)
                
                if (response.success) {
                    ImageUtils.saveImageToGallery(context, uri)
                    _uploadState.value = UploadState.Success(response.message)
                } else {
                    _uploadState.value = UploadState.Error(response.message)
                }
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun resetState() {
        _uploadState.value = UploadState.Idle
    }
}

sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val message: String) : UploadState()
    data class Error(val message: String) : UploadState()
}
