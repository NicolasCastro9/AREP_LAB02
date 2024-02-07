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
   ![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/6f6a5d9d-2f8c-4b36-9664-4219e02d295f)

4. En el navegador de Mozilla Firefox escribir las siguientes direcciones para ver la funcionalidad de la aplicación
  ```
   http://localhost:35000/index.html
   http://localhost:35000/image.jpg
   http://localhost:35000/script.js
   http://localhost:35000/styles.css
   http://localhost:35000/tiburon.gif
  ```
### index.html

![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/d1701fc5-8f32-473f-be0d-5b990d2d323e)
![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/58c324a7-e981-4c1a-b945-a4fc95116895)

### image.jpg

![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/f2faaa29-f17f-4d30-93a9-2a994102f845)

### styles.css

![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/e87f755e-4d01-4ff4-a9ae-65797c1501bb)

### tiburon.gif

![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/ffe48ffd-4f1d-446f-b774-b4212c1d25d6)

### script.js

![image](https://github.com/NicolasCastro9/AREP_LAB02/assets/98556822/a6f9b690-e902-4916-a9cf-11eb7a62810f)


## CONSTRUIDO CON

MAVEN -  framework de gestión de proyectos de software

## Dependencias
JUnit: Framework de pruebas unitarias para Java.

Gson: Biblioteca de Google para trabajar con JSON en Java.

## Autor
### Nicolás Castro Jaramillo

## Licencia
Este proyecto está bajo la licencia MIT.
