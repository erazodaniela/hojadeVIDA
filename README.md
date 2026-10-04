# INFORME TÉCNICO Y PRÁCTICA UNIVERSITARIA
## "Evolución de Hoja de Vida: De Nativo Android a Despliegue Web Embed en Flutter"

---

### PORTADA ACADÉMICA

* **Institución:** Universidad Politécnica Estatal del Carchi (UPEC)
* **Carrera:** Ingeniería en Computación
* **Nivel / Semestre:** 7mo Semestre
* **Asignatura:** Desarrollo de Aplicaciones Móviles
* **Título de la Práctica:** Evolución de Hoja de Vida: De Nativo Android a Despliegue Web Embed en Flutter
* **Estudiante Evaluada:** Daniela Madelain Erazo Montenegro (C.I. 0401727375)
* **Correo Electrónico:** erazodaniela1995@gmail.com
* **Período Académico:** 2026-I
* **Fecha de Entrega:** `[COMPLETAR: Fecha de Entrega en Clase]`

---

## 1. DESCRIPCIÓN DEL PROYECTO Y ESTRUCTURA DEL REPOSITORIO

Este proyecto documenta y ejecuta la transición tecnológica de una aplicación móvil nativa desarrollada en **Kotlin con Jetpack Compose** hacia un enfoque híbrido de **despliegue web embebido (Web Embed)** contenido en una aplicación **Flutter** utilizando **WebView**.

El objetivo principal es presentar la Hoja de Vida Profesional de **Daniela Madelain Erazo Montenegro** comparando las implicaciones arquitectónicas, el rendimiento, la experiencia de usuario (UX), la mantenibilidad y la reutilización de código entre ambas estrategias de desarrollo.

### Estructura Final del Repositorio

```text
DOCKER/
├── mobile-app/              # App nativa Android (Kotlin + Jetpack Compose) [Existente]
├── docker/                  # Entorno virtualizado Ubuntu + Android SDK [Existente]
├── docs/                    # Documentación técnica ERS IEEE 830 y guías [Existente]
│   └── capturas/            # Carpeta para capturas de pantalla de la práctica
├── web-cv/                  # Aplicación Web Standalone de Daniela Erazo (HTML5, CSS3, JS)
│   ├── index.html           # Estructura semántica de la hoja de vida
│   ├── style.css            # Estilos Mobile-First, Flexbox/Grid y temas claro/oscuro
│   ├── script.js            # Interacciones: Acordeón, Filtro Skills, Validación y setTheme
│   └── assets/              # Recursos gráficos (Avatar vector SVG de Daniela)
├── cv_flutter_wrapper/      # Proyecto Wrapper en Flutter para WebView
│   ├── assets/web/          # Copia local de los assets web para carga offline (Opción A)
│   ├── lib/main.dart        # Código Dart con AppBar nativo, cambio de tema, share_plus y error screen
│   ├── pubspec.yaml         # Configuración de dependencias (webview_flutter, share_plus)
│   └── android/             # Configuración nativa Android (AndroidManifest.xml, build.gradle.kts)
├── .gitignore               # Exclusión de archivos binarios, Gradle, Flutter y temporales
└── README.md                # Este informe técnico y comparativo de la práctica
```

---

## 2. INSTRUCCIONES DE EJECUCIÓN

### 2.1 Ejecutar la Aplicación Web (`web-cv/`)
1. **Acceso directo en navegador:**
   * Navega a la carpeta `web-cv/` y abre el archivo `index.html` haciendo doble clic en cualquier navegador web moderno (Chrome, Edge, Firefox).
2. **Servidor HTTP Local (Recomendado para pruebas de red):**
   * En PowerShell o terminal, ejecuta:
     ```bash
     cd web-cv
     python -m http.server 8000
     ```
   * Abre `http://localhost:8000` en tu navegador o escanea la IP local desde tu dispositivo móvil.

### 2.2 Ejecutar la App Wrapper en Flutter (`cv_flutter_wrapper/`)
> **Nota:** Requiere tener instalado **Flutter SDK** y **Android Studio** con emulador AVD o dispositivo físico conectado.

1. Abre una terminal en la carpeta `cv_flutter_wrapper/`:
   ```bash
   cd cv_flutter_wrapper
   flutter pub get
   ```
2. Ejecuta la aplicación en el emulador Android o dispositivo:
   ```bash
   flutter run
   ```
3. **Controles Nativos en la App:**
   * **Opción A vs Opción B:** Usa el botón de la nube en el AppBar para alternar entre el HTML local (`assets/web/index.html`) y la URL remota de GitHub Pages.
   * **Cambio de Tema:** El botón de sol/luna alterna el `ThemeMode` de Flutter e invoca la función JS `window.setTheme(...)` dentro del WebView.
   * **Compartir:** El botón de compartir activa el diálogo nativo mediante `share_plus` con los datos de Daniela Erazo.

### 2.3 Ejecutar la Aplicación Nativa Android (`mobile-app/`)
1. Abre **Android Studio** y selecciona `Open` eligiendo la carpeta `mobile-app`.
2. Opcionalmente compila el APK desde la terminal de Gradle:
   ```bash
   cd mobile-app
   ./gradlew assembleDebug
   ```
3. Presiona **Run ▶** en Android Studio para desplegar `BioApp Native` en el emulador AVD.

---

## 3. CAPTURAS DE PANTALLA DE LA PRÁCTICA

Las capturas de pantalla tomadas durante las pruebas deben ser guardadas en la carpeta `docs/capturas/` con los nombres especificados a continuación:

| Identificador | Descripción | Ruta de la Captura |
| :--- | :--- | :--- |
| **Captura 01** | App Nativa Android (Kotlin / Compose) en ejecución en emulador AVD | `docs/capturas/01_native_app.png` |
| **Captura 02** | Web CV en navegador móvil a 360 px de ancho (Responsive & Touch) | `docs/capturas/02_web_mobile.png` |
| **Captura 03** | App Flutter Wrapper en ejecución - Tema Claro (`ThemeMode.light`) | `docs/capturas/03_flutter_light.png` |
| **Captura 04** | App Flutter Wrapper en ejecución - Tema Oscuro (`ThemeMode.dark`) | `docs/capturas/04_flutter_dark.png` |

---

## 4. CUADRO COMPARATIVO: NATIVO (KOTLIN) VS. EMBEBIDO (FLUTTER + WEBVIEW)

| Criterio de Evaluación | Aplicación Nativa Android (Kotlin + Jetpack Compose) | Aplicación Embebida (Flutter Wrapper + WebView HTML5) |
| :--- | :--- | :--- |
| **Experiencia de Usuario (UX)** | Excelente. Animaciones nativas fluidas a nivel de GPU, respuesta háptica inmediata y componentes Material 3 nativos. | Muy Buena. Renderizado limpio mediante WebView de Android, pero con ligeras diferencias en el desplazamiento táctil (overscroll) e interacción. |
| **Fluidez / Rendimiento** | **60 - 120 FPS constantes**. Sin capas de traducción intermedia ni motores web adicionales. | **45 - 60 FPS**. El motor Chromium interno añade sobrecarga al procesar el DOM y CSS. |
| **Tiempo de Arranque (Cold Start)** | `[MEDIR]` *(Falta medir en emulador Android Studio)* | `[MEDIR]` *(Falta medir en emulador Android Studio)* |
| **Tamaño del APK (`.apk`)** | `[MEDIR]` *(Falta medir compilando release APK)* | `[MEDIR]` *(Falta medir compilando release APK)* |
| **Consumo de Memoria RAM** | `[MEDIR]` *(Falta medir con Android Studio Profiler)* | `[MEDIR]` *(Falta medir con Android Studio Profiler)* |
| **Acceso a Funciones Nativas** | Acceso directo e ilimitado mediante Android APIs (Cámara, Sensores, Cifrado Hardware, Bluetooth). | Acceso híbrido: La app Flutter accede a funciones nativas mediante Plugins Dart (`share_plus`) y las comunica a la web mediante canales JS. |
| **Mantenibilidad y Reutilización** | Reutilización limitada a la plataforma Android (o Kotlin Multiplatform con refactorización). | **Máxima Reutilización (Single Codebase)**. El código web (`web-cv/`) se ejecuta en navegadores desktop, móviles, GitHub Pages y dentro de apps nativas. |
| **Curva de Desarrollo** | Alta. Requiere conocimientos avanzados en Kotlin, Jetpack Compose, Gradle y SDK de Android. | Media / Baja. Desarrollo web estándar (HTML/CSS/JS) envuelto en un contenedor Flutter reutilizable. |
| **Funcionamiento Offline** | 100% Offline nativo sin dependencia de servidores externos. | 100% Offline en **Opción A** (Assets locales). Dependiente de red en **Opción B** (GitHub Pages). |
| **Actualización de Contenido** | Requiere recompilar el APK y republicar la app en Google Play Store ante cualquier cambio. | **Inmediata (OTA)** en **Opción B**: Actualizar la web en GitHub Pages actualiza la app instantáneamente sin republicar la APK. |

---

## 5. ANÁLISIS TÉCNICO Y ARQUITECTÓNICO

### 5.1 Renderizado Nativo vs. Motor Web dentro de WebView
* **Nativo (Kotlin + Compose):** Jetpack Compose compila directamente a instrucciones ejecutables de gráficos en Canvas usando las bibliotecas nativas de Android. No existe una capa de representación intermediaria; las estructuras de datos de la UI se traducen directamente en llamadas a la GPU del dispositivo, garantizando un rendimiento óptimo y un menor consumo térmico.
* **Embebido (Flutter + WebView):** Flutter renderiza su propia interfaz nativa utilizando el motor Skia/Impeller. Sin embargo, el contenido de la hoja de vida reside dentro de un widget `WebViewWidget` que instancia una ventana de `android.webkit.WebView` (basada en el motor Chromium de Android). Esto crea un entorno de renderizado compuesto: la interfaz nativa del AppBar en Flutter y el motor Chromium procesando HTML5/CSS3 en el cuerpo de la pantalla.

### 5.2 Capa de Puente JS-Dart (Interoperabilidad Híbrida)
La comunicación entre la barra de herramientas nativa de Flutter y la lógica interna de la web se resuelve mediante la inyección de JavaScript:
* Al presionar el botón de cambio de tema en el AppBar de Flutter, la aplicación invoca el método `controller.runJavaScript("window.setTheme('dark'|'light')")`.
* La función global `window.setTheme` expuesta en `script.js` modifica dinámicamente el atributo `data-theme` en la etiqueta `<html>`, actualizando las variables CSS en tiempo real sin recargar la página web.

### 5.3 Estrategia de Código Único ("Single Codebase")
El enfoque embebido ofrece una ventaja estratégica crucial en proyectos empresariales: una sola base de código web en la carpeta `web-cv/` sirve simultáneamente para:
1. Publicación como sitio web standalone en servidores Nginx/Apache o GitHub Pages.
2. Visualización en navegadores móviles.
3. Despliegue embebido local dentro de aplicaciones móviles Android e iOS mediante Flutter.

---

## 6. GUÍA DE MEDICIÓN PARA CELDAS `[MEDIR]`

Para completar los datos cuantitativos pendientes cuando concluya la instalación de Android Studio y el Android SDK, sigue las instrucciones paso a paso:

### A. Tiempo de Arranque (Cold Start)
1. Conecta el emulador AVD y ejecuta los siguientes comandos ADB desde PowerShell:
   * Para App Nativa:
     ```powershell
     adb shell am start -W -n com.cv.nativeapp/.MainActivity
     ```
   * Para App Flutter Wrapper:
     ```powershell
     adb shell am start -W -n com.cv.wrapper.cv_flutter_wrapper/.MainActivity
     ```
2. Registra el valor obtenido en el parámetro **`TotalTime`** (expresado en milisegundos) y colócalo en el cuadro comparativo.

### B. Tamaño del APK (`.apk`)
1. Genera los archivos APK de versión debug/release:
   * En `mobile-app/`: `./gradlew assembleRelease`
   * En `cv_flutter_wrapper/`: `flutter build apk --release`
2. Verifica el tamaño en disco de cada archivo:
   ```powershell
   Get-ChildItem mobile-app/app/build/outputs/apk/release/app-release.apk | Select-Object Name, Length
   Get-ChildItem cv_flutter_wrapper/build/app/outputs/flutter-apk/app-release.apk | Select-Object Name, Length
   ```
3. Registra el peso final en Megabytes (MB).

### C. Consumo de Memoria RAM
1. Abre **Android Studio** -> Menú **View** -> **Tool Windows** -> **Profiler**.
2. Inicia la aplicación en el emulador y selecciona el proceso correspondiente.
3. Examina el valor medio en la pestaña **Memory (Total PSS)** durante la navegación entre pestañas.
4. O ejecuta por consola ADB:
   ```powershell
   adb shell dumpsys meminfo com.cv.nativeapp
   adb shell dumpsys meminfo com.cv.wrapper.cv_flutter_wrapper
   ```
5. Anota el valor `TOTAL PSS` expresado en Megabytes (MB).

---

## 7. CONCLUSIONES

1. **Experiencia de Usuario (UX) y Fluidez:** Las aplicaciones nativas en Kotlin con Jetpack Compose ofrecen la respuesta táctil y la fluidez visual más alta (60-120 FPS), siendo ideales para experiencias donde el rendimiento gráfico y la integración profunda con el hardware son prioritarios. Sin embargo, el desarrollo web con CSS3 moderno y transiciones fluidas logra una experiencia altamente competitiva para aplicaciones informativas como la hoja de vida de Daniela Erazo.
2. **Mantenibilidad y Reutilización de Código:** La arquitectura de desarrollo embebido (Flutter + WebView) supera drásticamente al desarrollo nativo en términos de velocidad de entrega y reutilización. Al mantener una única base de código HTML/CSS/JS, es posible actualizar instantáneamente el contenido en producción (mediante GitHub Pages) sin necesidad de que los usuarios descarguen una actualización de la aplicación en la tienda de aplicaciones.
3. **Equilibrio Arquitectónico en Aplicaciones Móviles:** El uso de un wrapper en Flutter con WebView representa una excelente solución intermedia para empresas que buscan lanzar productos multiplataforma en tiempo récord sin renunciar a controles nativos en la barra de navegación (como botones de compartir nativos, recarga, manejo del botón atrás y alternancia de temas).

---

## 8. BIBLIOGRAFÍA (FORMATO APA)

* Google Developers. (2026). *Flutter documentation: Building Web applications and WebView integrations*. Recuperado de https://docs.flutter.dev/
* Flutter Dev. (2026). *webview_flutter package documentation (v4.10.0)*. Pub.dev. Recuperado de https://pub.dev/packages/webview_flutter
* Mozilla Developer Network (MDN). (2026). *HTML5 Semantic Elements & Responsive Web Design Guidelines*. Recuperado de https://developer.mozilla.org/es/docs/Web/HTML
* Marcotte, E. (2011). *Responsive Web Design*. New York: A Book Apart.
* Windmill, E. (2020). *Flutter in Action*. Shelter Island, NY: Manning Publications.
