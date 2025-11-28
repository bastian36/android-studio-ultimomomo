package ui.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.BlogData

class BlogViewModel : ViewModel() {
    private val _apiBlogs = MutableStateFlow<List<BlogData>>(emptyList())
    val apiBlogs: StateFlow<List<BlogData>> = _apiBlogs
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadBlogs()
    }

    private fun loadBlogs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = BlogRetrofitClient.apiService.getBlogs()
                if (response.success) {
                    _apiBlogs.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
