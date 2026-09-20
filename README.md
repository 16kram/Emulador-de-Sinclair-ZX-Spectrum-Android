# Emulador de Sinclair ZX Spectrum para Android

Emulador del **Sinclair ZX Spectrum 48K** desarrollado en **Java** para dispositivos Android.

El proyecto adapta la idea del emulador de ZX Spectrum para ejecutarse como una aplicación móvil, permitiendo utilizar el sistema desde un dispositivo Android.

## Objetivo

El objetivo del proyecto es desarrollar una aplicación Android capaz de emular el **Sinclair ZX Spectrum 48K**, trasladando el funcionamiento del ordenador clásico a un entorno móvil.

El proyecto combina conceptos de:

* Emulación de hardware.
* Arquitectura del ZX Spectrum 48K.
* Procesador Z80.
* Desarrollo de aplicaciones Android.
* Programación en Java.
* Gestión de interfaces para dispositivos móviles.

## Características

* Emulación del **Sinclair ZX Spectrum 48K**.
* Aplicación desarrollada en **Java**.
* Ejecución sobre dispositivos Android.
* Interfaz adaptada al entorno móvil.
* Proyecto Android basado en **Gradle**.
* APK incluido en el repositorio para facilitar las pruebas.

## Arquitectura del proyecto

La aplicación se estructura como un proyecto Android:

```text
┌─────────────────────────────────┐
│          Aplicación Android     │
│                                 │
│          ZX Spectrum 48K        │
│                                 │
│       ┌─────────────────┐       │
│       │     Emulador    │       │
│       │      Java       │       │
│       └────────┬────────┘       │
│                │                │
│       ┌────────▼────────┐       │
│       │ Interfaz Android│       │
│       └────────┬────────┘       │
│                │                │
│       ┌────────▼────────┐       │
│       │ Pantalla /      │       │
│       │ interacción     │       │
│       └─────────────────┘       │
└─────────────────────────────────┘
```

## Ejecución

El repositorio incluye el archivo:

```text
ZX-Spectrum.apk
```

Para probar la aplicación:

1. Descargar `ZX-Spectrum.apk`.
2. Transferirlo al dispositivo Android.
3. Instalar el APK.
4. Ejecutar la aplicación.

El proyecto también incluye los archivos necesarios para trabajar con el código fuente mediante **Gradle**.

## Tecnologías

| Tecnología          | Uso                                  |
| ------------------- | ------------------------------------ |
| **Java**            | Desarrollo del emulador              |
| **Android**         | Plataforma de ejecución              |
| **Gradle**          | Sistema de construcción del proyecto |
| **ZX Spectrum 48K** | Sistema emulado                      |
| **Z80**             | Procesador del sistema emulado       |

## Conceptos trabajados

* Desarrollo de aplicaciones Android.
* Java.
* Emulación de sistemas.
* Arquitectura de computadores.
* Microprocesadores.
* Z80.
* Representación de un sistema clásico dentro de una aplicación móvil.
* Gestión de proyectos Android con Gradle.

## Estado del proyecto

Proyecto funcional orientado a la ejecución del **ZX Spectrum 48K en dispositivos Android**.

El repositorio incluye tanto el código fuente del proyecto Android como un APK para facilitar la prueba de la aplicación.


[![Alt text](https://img.youtube.com/vi/5m0UYnAE_o0/0.jpg)](https://www.youtube.com/watch?v=5m0UYnAE_o0)
