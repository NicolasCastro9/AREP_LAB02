# SERVIDOR WEB DE CONSULTAS
Este proyecto consiste en un servidor web que soporta múlltiples solicitudes seguidas (no concurrentes). El servidor lee los archivos del disco local y retorna todos los archivos solicitados, incluyendo páginas html, archivos java script, css e imágenes. El proyecto en cuestion contiene archivos javascript, css, e imágenes para poder probar el servidor. 

## Clases
HttpServer: Esta clase implementa un servidor HTTP básico que escucha en el puerto 35000. El servidor atiende las solicitudes de los clientes y responde según la información proporcionada en esas solicitudes.

Cache: Esta clase implementa un cache que actúa como una memoria para almacenar información de películas.

HttpConnection: clase que se utiliza para realizar conexiones HTTP con la API de OMDB y obtener información sobre películas.

MovieInfo: clase que representa la información de una película obtenida de la API de OMDB.

## Pre-Requisitos

Asegúrate de tener instalado Java y Maven en tu máquina antes de ejecutar el proyecto.

## Instrucciones de uso

1. Clona el repositorio a tu máquina local:
   ```
   git clone https://github.com/NicolasCastro9/AREP_LAB02.git
   ```
2. Abrir en un IDE el proyecto descargado y ejecutar el archivo java HttpServer.java
   ```
   cd miprimera-app
   ```

3. En el navegador de Mozilla Firefox escribir las siguientes direcciones para ver la funcionalidad de la aplicación
  ```
   http://localhost:35000/index.html
   http://localhost:35000/image.jpg
   http://localhost:35000/script.js
   http://localhost:35000/styles.css
   http://localhost:35000/tiburon.gif
  ```

## CONSTRUIDO CON

MAVEN -  framework de gestión de proyectos de software

## Dependencias
JUnit: Framework de pruebas unitarias para Java.

Gson: Biblioteca de Google para trabajar con JSON en Java.

## Autor
### Nicolás Castro Jaramillo

## Licencia
Este proyecto está bajo la licencia MIT.
