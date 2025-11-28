package ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.ApiProduct

class ProductsViewModel : ViewModel() {
    private val _apiProducts = MutableStateFlow<List<ApiProduct>>(emptyList())
    val apiProducts: StateFlow<List<ApiProduct>> = _apiProducts
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiService.getProducts()
                if (response.success) {
                    _apiProducts.value = response.data.productos
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
