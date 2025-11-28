package ui.login

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.components.ValidatedTextField

@Composable
fun LoginScreen(
    onNavigateToHome: (String) -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.ui.collectAsState()

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
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎮",
                        fontSize = 48.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        ValidatedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = "Email",
            leadingIcon = Icons.Default.Email,
            isError = uiState.error?.contains("Email") == true,
            errorMessage = if (uiState.error?.contains("Email") == true) uiState.error else null,
            isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches(),
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ValidatedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Contraseña",
            leadingIcon = Icons.Default.Lock,
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.error?.contains("clave") == true,
            errorMessage = if (uiState.error?.contains("clave") == true) uiState.error else null,
            isValid = uiState.password.length >= 6,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Button(
                onClick = viewModel::submit,
                enabled = !uiState.loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedContent(
                    targetState = uiState.loading,
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { loading ->
                    if (loading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Text("Ingresar")
                    }
                }
            }
        }
        
            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = error,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            LaunchedEffect(uiState.loggedIn) {
                if (uiState.loggedIn) {
                    onNavigateToHome(uiState.userEmail)
                }
            }
        }
    }
}