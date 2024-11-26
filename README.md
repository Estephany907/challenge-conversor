# Aplicación de Conversión de Tipos de Cambio en Java

Esta es una aplicación en Java que permite convertir cantidades entre diferentes monedas utilizando la API de ExchangeRate-API. La aplicación permite al usuario seleccionar la moneda de origen, la moneda de destino y la cantidad que desea convertir. También incluye una opción para salir del programa.

## Funcionalidades

- **Conversión de Monedas**: La aplicación permite convertir entre las siguientes monedas:
  - **ARS**: Peso argentino
  - **BOB**: Boliviano boliviano
  - **BRL**: Real brasileño
  - **CLP**: Peso chileno
  - **COP**: Peso colombiano
  - **USD**: Dólar estadounidense

- **Opción de salir**: El programa ofrece una opción para salir y terminar la ejecución en cualquier momento.

## Requisitos

- JDK 11 o superior.
- Conexión a internet para realizar consultas a la API externa de tipos de cambio.
- Dependencia de la biblioteca `org.json` para manejar respuestas en formato JSON.

## Dependencias

Si estás utilizando **Maven** para gestionar dependencias, agrega la siguiente dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20211205</version>
</dependency>
