# =========================================================================
# DOCKERFILE - Entorno Virtualizado de Desarrollo Android Native
# Aplicación Móvil Hoja de Vida Interactiva ("BioApp Native")
# =========================================================================

FROM ubuntu:22.04

LABEL maintainer="Equipo de Desarrollo Hoja de Vida"
LABEL version="1.0"
LABEL description="Contenedor Docker para compilación y ejecución de Android Studio SDK & AVD"

ENV DEBIAN_FRONTEND=noninteractive
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH="${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools"

# 1. Instalación de dependencias del sistema, Java JDK 17 y servidor gráfico GUI (VNC/X11)
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    wget \
    unzip \
    git \
    curl \
    qemu-kvm \
    libvirt-daemon-system \
    xvfb \
    x11vnc \
    fluxbox \
    && rm -rf /var/lib/apt/lists/*

# 2. Descarga e instalación de Android Command Line Tools (SDK)
RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-9444967_latest.zip -O /tmp/tools.zip && \
    unzip -q /tmp/tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools && \
    mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest && \
    rm /tmp/tools.zip

# 3. Aceptar licencias de Android SDK automáticamente
RUN yes | sdkmanager --licenses

# 4. Instalación de componentes del SDK para Android 14 (API Level 34)
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

WORKDIR /workspace

EXPOSE 5900

CMD ["bash"]
