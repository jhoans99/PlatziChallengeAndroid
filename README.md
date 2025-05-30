Platzi Challenge - Android Developer

Este repositorio contiene la prueba técnica de Android para Platzi, construida con Kotlin, Jetpack Compose y clean architecture.

Decisiones Técnicas

Arquitectura Multimódulo: Separación en módulos :core, :data, :domain y :features para aislar responsabilidades y optimizar tiempos de compilación.

Inyección de Dependencias: Uso de Hilt para gestionar ciclos de vida y dependencias en ViewModels, repositorios y data sources.

UI Declarativa: Jetpack Compose para pantallas de las dos funcionalidades.

Paginación: Implementación de Paging 3 para listas eficientes y manejo de errores con LoadState.

Reproducción Multimedia: Media3 integrado en Compose a través de AndroidView.

Local Storage: Room para persistir la lista de películas favoritas, con DataSource y Repository dedicados.

Clean Architecture: Capas domain (modelos y contratos para los repositorios), data (fuentes remotas con Retrofit y apiService y locales para room e implementacion de repositorios) y features (UI y ViewModels).

Cómo Correr el Proyecto

Clonar el repositorio:

git clone https://github.com/jhoans99/MlProducts.git

Abrir en Android Studio (Arctic Fox o superior).

Sincronizar Gradle.

Ejecutar la app en un emulador o dispositivo físico con Android 7.0+.

Funcionalidades Implementadas

Listado de Películas: Búsqueda y paginación de resultados desde TMDB e implementacion de favoritos para guardar en local con categoria deseada, adicional de poder eliminar o editar.

Detalle de Película: Pantalla detallada con imagen, descripción, metadata y reseñas.

Tráiler: Reproductor integrado con Media3, controles visibles y callback de fin de reproducción.

Favoritos: Agregar, editar categoría y eliminar películas favoritas, con persistencia en Room.

Offline Support: Cache de favoritos y manejo básico de errores de red.

Qué Quedó Pendiente

Internacionalización: Solo español, falta soporte multi-idioma.

ApiHandler para el manejo avazando de error de red

Alamcenaimiento del listado de peliculas en Room

Notas Adicionales

La elección de Multi-Módulo mejora la escalabilidad y la claridad en proyectos grandes, sin embargo al ser un proyecto pequeño se modifica los modulos para garntizar legibilidad de codigo.

Hilt facilita la inyección en ViewModels e inyeccion de contratos para los repositorios y datasource.

Jetpack Compose acelera el desarrollo de UI y ofrece animaciones simples (AnimatedVisibility).

Paging 3 con LoadState permite capturar errores generales sin crashear la app.

Media3 integrado directamente en Composables mediante DisposableEffect garantiza liberar recursos.
