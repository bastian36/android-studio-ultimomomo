package ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import model.Product
import model.ApiProduct
import cl.duoc.example.myapplication.R
import java.util.Locale

@Composable
fun ProductsScreen(viewModel: ProductsViewModel = viewModel()) {
    val staticProducts = remember {
        listOf(
            Product(1, "PlayStation 5", "Consola de videojuegos de última generación", 699990.0, "playstation5", "Consolas"),
            Product(2, "Audífono HyperX", "Audífonos gaming de alta calidad", 69990.0, "audifono_hyper_x", "Audio"),
            Product(3, "Mouse Gamer RGB", "Mouse gaming con iluminación RGB", 29990.0, "mouse_gamer", "Periféricos"),
            Product(4, "Mouse Pad XL", "Mouse pad gaming tamaño extra grande", 14990.0, "mousepad", "Periféricos"),
            Product(5, "Silla Gamer", "Silla ergonómica para gaming", 129990.0, "silla_gamer", "Mobiliario"),
            Product(6, "Rog Strix GPU", "Tarjeta gráfica de alto rendimiento", 549990.0, "rog_strix", "Componentes"),
            Product(7, "Polera LevelUp", "Polera oficial LevelUp", 12990.0, "camisa_levelup", "Merch"),
            Product(8, "Catan", "Juego de mesa estratégico", 37990.0, "catan", "Juegos de mesa"),
            Product(9, "Carcassonne", "Juego de mesa de construcción", 34990.0, "carcassonne", "Juegos de mesa"),
            Product(10, "Control Xbox", "Control inalámbrico Xbox", 59990.0, "control_xbox", "Periféricos")
        )
    }
    
    val apiProducts by viewModel.apiProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_android),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Mis Productos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(staticProducts) { product ->
                    ProductCard(product = product)
                }
                
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                
                items(apiProducts) { apiProduct ->
                    ApiProductCard(product = apiProduct)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                val imageRes = when(product.imageUrl) {
                    "playstation5" -> R.drawable.playstation5
                    "audifono_hyper_x" -> R.drawable.audifono_hyper_x
                    "mouse_gamer" -> R.drawable.mouse_gamer
                    "mousepad" -> R.drawable.mousepad
                    "silla_gamer" -> R.drawable.silla_gamer
                    "rog_strix" -> R.drawable.rog_strix
                    "camisa_levelup" -> R.drawable.camisa_levelup
                    "catan" -> R.drawable.catan
                    "carcassonne" -> R.drawable.carcassonne
                    "control_xbox" -> R.drawable.control_xbox
                    else -> R.drawable.logo_android
                }
                
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = product.category,
                    fontSize = 10.sp,
                    color = Color(0xFF00AA66)
                )
            }

            Text(
                text = "$${String.format(Locale.getDefault(), "%,.0f", product.price)} CLP",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6B9D)
            )
        }
    }
}