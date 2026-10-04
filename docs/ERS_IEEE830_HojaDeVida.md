# ESPECIFICACIÓN DE REQUISITOS DE SOFTWARE (ERS) - ESTÁNDAR IEEE 830
## Proyecto: Aplicación Móvil Nativa de Hoja de Vida Interactiva ("BioApp Native")
**Documento Técnico Oficial para Evaluación Académica**

---

## 1. INTRODUCCIÓN Y ALCANCE

### 1.1 Propósito
El propósito de este documento es definir la Especificación de Requisitos de Software (ERS) bajo el estándar **IEEE 830-1998** para la aplicación móvil nativa "BioApp Native". Este documento sirve como guía para el desarrollo, auditoría de calidad y evaluación docente.

### 1.2 Alcance del Sistema
La aplicación permite presentar la trayectoria profesional, laboral, académica y habilidades técnicas de forma interactiva en la plataforma **Android**.
* **Problema que resuelve:** Sustituye el CV estático impreso o PDF por una experiencia nativa interactiva con elementos multimedia, acciones rápidas de contacto e insignias de certificación.
* **Público Objetivo:** Reclutadores IT, directores de talento humano y docentes evaluadores.

---

## 2. DESCRIPCIÓN GENERAL

### 2.1 Perspectiva del Producto
Aplicación nativa Android desarrollada en **Kotlin** con soporte de **Jetpack Compose**. Se ejecuta dentro de un entorno virtualizado con **Docker** que contiene el entorno de Android Studio y el emulador **AVD (Android Virtual Device)**.

### 2.2 Requerimientos Funcionales (RF)
* **RF01 (Autenticación y Perfil):** Autenticación/Gestión de perfil del propietario con fotografía HD y resumen ejecutivo.
* **RF02 (Visualización Estructurada):** Secciones navegables (Perfil, Experiencia Laboral, Educación y Habilidades Técnicas).
* **RF03 (Integración Multimedia):** Soporte de imágenes vectoriales, insignias de certificación y enlaces dinámicos.
* **RF04 (Navegación e Interacción):** Interfaz fluida con botones interactivos (llamada directa, email, repositorios GitHub).

### 2.3 Requerimientos No Funcionales (RNF)
* **RNF01 (Seguridad):** Almacenamiento local seguro cifrado con *EncryptedSharedPreferences* (AES-256).
* **RNF02 (Usabilidad / HCI):** Diseño bajo directrices *Material Design 3*. Información legible en **menos de 2 minutos**.
* **RNF03 (Rendimiento):** Tiempo de inicio < 1.5s, 60 FPS, consumo de memoria < 120 MB.
* **RNF04 (Compatibilidad y Despliegue):** Compilación APK ejecutable en emulador AVD en Docker.

---

## 3. PLANIFICACIÓN DEL PROYECTO (PARTE B)

### 3.1 Desglose de Trabajo (WBS / EDT)
1. **1.0 Configuración del Entorno Docker:** Dockerfile, SDK Android, AVD Virtual Machine.
2. **2.0 Desarrollo de Aplicación Nativa:** Capa de datos, Componentes UI Compose, Módulos RF01-RF04.
3. **3.0 Compilación y Despliegue:** Generación de APK, pruebas en emulador AVD.
4. **4.0 Documentación IEEE 830:** Manuales y entregable final.

### 3.2 Matriz de Roles (RACI)
* **Lead Architect / DevOps:** Configuración del Dockerfile y entorno de virtualización.
* **Mobile Developer:** Desarrollo del código nativo Kotlin / Compose.
* **QA & Doc Specialist:** Pruebas en el emulador AVD y validación del documento IEEE 830.
