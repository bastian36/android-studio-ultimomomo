package ui.profile

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import utils.rememberLocationState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    onLogout: () -> Unit = {},
    onNavigateToProducts: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var currentLocation by remember { mutableStateOf<String?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationState = rememberLocationState()
    val uploadState by viewModel.uploadState.collectAsState()
    
    val displayName = userName
    val email = userEmail
    
    // Permisos
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    
    // URI para la foto
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    
    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            profileImageUri = photoUri
            photoUri?.let { viewModel.uploadImage(context, it) }
        }
    }
    
    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            profileImageUri = it
            viewModel.uploadImage(context, it)
        }
    }
    
    // Observar estado de subida
    LaunchedEffect(uploadState) {
        when (uploadState) {
            is UploadState.Success -> {
                Toast.makeText(context, (uploadState as UploadState.Success).message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            is UploadState.Error -> {
                Toast.makeText(context, (uploadState as UploadState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "👤 Mi Perfil",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        
        // Foto de perfil
        Card(
            modifier = Modifier.size(120.dp),
            shape = CircleShape
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Sin foto",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
        
            // Botones de cámara y galería
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (permissionsState.allPermissionsGranted) {
                            photoUri = utils.CameraUtils.createImageUri(context)
                            photoUri?.let { cameraLauncher.launch(it) }
                        } else {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FF88)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Cámara", color = Color.White, fontWeight = FontWeight.Bold)
                }
                
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B9D)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = uploadState !is UploadState.Loading
                ) {
                    if (uploadState is UploadState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                    } else {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Galería", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

        
            // Información del usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Información Personal",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nombre: ",
                            color = Color.Black.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                        Text(
                            text = displayName,
                            color = Color(0xFF00FF88),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Email: ",
                            color = Color.Black.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                        Text(
                            text = email,
                            color = Color(0xFFFF6B9D),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    if (currentLocation != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ubicación: ",
                                color = Color.Black.copy(alpha = 0.7f),
                                fontSize = 16.sp
                            )
                            Text(
                                text = currentLocation!!,
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        
        // Botón de ubicación
        Button(
            onClick = {
                if (permissionsState.allPermissionsGranted) {
                    isLoadingLocation = true
                    scope.launch {
                        val location = locationState.getCurrentLocation()
                        currentLocation = if (location != null) {
                            "Lat: ${String.format("%.4f", location.latitude)}, " +
                            "Lng: ${String.format("%.4f", location.longitude)}"
                        } else {
                            "No se pudo obtener ubicación"
                        }
                        isLoadingLocation = false
                    }
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                }
            },
            enabled = !isLoadingLocation,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoadingLocation) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Obtener Ubicación GPS")
            }
        }
        
            // Botón de productos
            Button(
                onClick = onNavigateToProducts,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C63FF)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ver Productos 🎮",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(Modifier.weight(1f))
            
            // Botón de logout
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    fontSize = 16.sp
                )
            }
        }
    }
}