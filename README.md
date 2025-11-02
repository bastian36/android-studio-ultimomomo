# 🎮 Gaming Store - Aplicación Móvil Android

## 📱 Descripción del Proyecto
Aplicación móvil nativa para Android desarrollada en **Kotlin** con **Jetpack Compose** que simula una tienda gaming. La app permite a los usuarios registrarse, iniciar sesión, explorar productos gaming, leer noticias del blog y gestionar su perfil con funcionalidades nativas del dispositivo.

## 👥 Equipo de Desarrollo
- **Bastián Guajardo** - ba.guajardoh@duocuc.cl
- **Maximiliano Sanchez - max.sanchezs@duocuc.cl
- **Bastian Concha - ba.conchar@duocuc.cl

## ✨ Funcionalidades Implementadas

### 🔐 Autenticación
- **Registro de usuarios** con Firebase Authentication
- **Inicio de sesión** con validación de credenciales
- **Recuperación de contraseña** por email
- **Logout** seguro

### 🎯 Navegación Principal
- **Pantalla de bienvenida** con diseño gaming
- **Menú de 3 puntos** con opciones: Perfil, Configuración, Cerrar Sesión
- **Navegación fluida** entre pantallas con Navigation Compose

### 🛍️ Catálogo de Productos
- **Lista de productos gaming** (consolas, periféricos, juegos de mesa)
- **Diseño tipo tarjeta** con imágenes, precios y categorías
- **Interfaz responsive** con LazyColumn

### 📰 Blog Gaming
- **Noticias gaming** con imágenes y fechas
- **Artículos informativos** sobre productos y tendencias
- **Diseño consistente** con el tema de la aplicación

### 👤 Perfil de Usuario
- **Información del usuario logueado** (nombre y email desde Firebase)
- **Foto de perfil** con acceso a cámara y galería
- **Ubicación GPS** opcional
- **Gestión de permisos** nativa

### ⚙️ Configuración
- **Ajustes de notificaciones** con switches
- **Modo oscuro gaming** 
- **Configuración de idioma**

## 🛠️ Tecnologías Utilizadas

### Frontend
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - UI moderna y declarativa
- **Material Design 3** - Sistema de diseño
- **Navigation Compose** - Navegación entre pantallas

### Backend y Persistencia
- **Firebase Authentication** - Autenticación de usuarios
- **Firebase Analytics** - Análisis de uso
- **SharedPreferences** - Persistencia local

### Arquitectura
- **MVVM** - Patrón de arquitectura
- **StateFlow** - Manejo de estado reactivo
- **ViewModel** - Lógica de negocio desacoplada

### Recursos Nativos
- **Cámara** - Captura de fotos de perfil
- **Galería** - Selección de imágenes
- **GPS** - Geolocalización
- **FileProvider** - Gestión segura de archivos
- **Permisos** - Manejo con Accompanist Permissions

## 🎨 Diseño Visual
- **Tema gaming** con gradientes púrpura y azul
- **Colores neón** (verde #00FF88, rosa #FF6B9D)
- **Animaciones fluidas** con AnimatedContent y AnimatedVisibility
- **Iconografía gaming** con emojis y Material Icons
- **Tarjetas elevadas** con bordes redondeados

## 📋 Validaciones Implementadas
- **Email** - Formato válido con Patterns.EMAIL_ADDRESS
- **Contraseña** - Mínimo 6 caracteres
- **Formularios** - Retroalimentación visual con colores y mensajes
- **Estados de carga** - Indicadores de progreso
- **Manejo de errores** - Mensajes claros al usuario

## 🚀 Pasos para Ejecutar

### Prerrequisitos
- **Android Studio** Hedgehog | 2023.1.1 o superior
- **JDK 17** o superior
- **Android SDK** API 24+ (Android 7.0)
- **Dispositivo Android** o emulador configurado

### Instalación
1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/[usuario]/gaming-store-android.git
   cd gaming-store-android
   ```

2. **Abrir en Android Studio**
   - File → Open → Seleccionar carpeta del proyecto
   - Esperar sincronización de Gradle

3. **Configurar Firebase**
   - El archivo `google-services.json` ya está incluido
   - Proyecto Firebase: `myapplication-c6042`

4. **Ejecutar la aplicación**
   - Conectar dispositivo Android o iniciar emulador
   - Presionar "Run" (▶️) o `Shift + F10`

### Credenciales de Prueba
- **Email:** ba.guajardoh@duocuc.cl
- **Email:** ba.conchar@duocuc.cl
- **Contraseña:** [crear usuario desde la app o Firebase Console]

## 📁 Estructura y Ordeb del Proyecto
```
app/
├── src/main/java/
│   ├── cl/duoc/example/myapplication/
│   │   ├── MainActivity.kt
│   │   └── MyApplication.kt
│   ├── ui/
│   │   ├── app/           # Navegación y rutas
│   │   ├── login/         # Autenticación
│   │   ├── register/      # Registro
│   │   ├── recover/       # Recuperar contraseña
│   │   ├── principal/     # Dashboard principal
│   │   ├── products/      # Catálogo de productos
│   │   ├── blog/          # Blog gaming
│   │   ├── profile/       # Perfil de usuario
│   │   ├── settings/      # Configuración
│   │   └── components/    # Componentes reutilizables
│   ├── model/             # Modelos de datos
│   └── utils/             # Utilidades
├── src/main/res/
│   ├── drawable/          # Imágenes y recursos gráficos
│   ├── values/            # Colores, strings, temas
│   └── xml/               # Configuraciones XML
└── google-services.json   # Configuración Firebase
```

## 🔧 Configuración de Desarrollo

### Dependencias Principales
```kotlin
// UI y Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose")
implementation("androidx.navigation:navigation-compose")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
implementation("com.google.firebase:firebase-analytics")
implementation("com.google.firebase:firebase-auth-ktx")

// Recursos nativos
implementation("com.google.accompanist:accompanist-permissions")
implementation("io.coil-kt:coil-compose")
```

### Permisos Requeridos
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

## 📱 Capturas de Pantalla
- **Login:** Diseño gaming con gradientes
- **Principal:** Dashboard con menú de 3 puntos
- **Productos:** Catálogo con tarjetas elevadas
- **Blog:** Noticias gaming con imágenes
- **Perfil:** Información del usuario con foto

## 🎯 Cumplimiento de Requisitos
- ✅ **Interfaz visual organizada** con navegación clara
- ✅ **Formularios validados** con íconos y mensajes
- ✅ **Lógica desacoplada** con MVVM y ViewModel
- ✅ **Animaciones funcionales** en transiciones
- ✅ **Estructura modular** con separación de responsabilidades
- ✅ **Persistencia local** con Firebase y SharedPreferences
- ✅ **Recursos nativos** (cámara, galería, GPS)

## 📞 Contacto
Para consultas sobre el proyecto:
- **Email:** ba.guajardoh@duocuc.cl
- **Email:** ba.conchar@duocuc.cl
- **Email:** max.sanchezs@duocuc.cl
- **Institución:** DUOC UC

---
**Desarrollado para la asignatura de Desarrollo Móvil**
