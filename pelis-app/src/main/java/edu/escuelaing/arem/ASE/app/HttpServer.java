package edu.escuelaing.arem.ASE.app;

import java.io.*;
import java.net.*;

/**
 * Clase que crea el servidor HTTP
 */
public class HttpServer {

    /**
     * Metodo principal de la clase que inicia el servidor y escucha conexiones en el puerto 35000.
     * @param args Argumentos de linea de comandos (no utilizados).
     * @throws IOException Si ocurre un error de entrada/salida al abrir el socket del servidor.
     */

    private static final String STATIC_FILES_PATH = "pelis-app/src/main/Resources/";

    /**
     * Método principal que inicia el servidor y escucha conexiones en el puerto 35000.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     * @throws IOException Si ocurre un error de entrada/salida al abrir el socket del servidor.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Server listening on port 35000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        serverSocket.close();
    }

    /**
     * Metodo que maneja la solicitud del cliente en un nuevo hilo separado.
     * @param clientSocket Socket del cliente.
     */
    private static void handleRequest(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine;
            StringBuilder request = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.isEmpty()) {
                    break;
                }
                request.append(inputLine).append("\r\n");
            }
            String requestString = request.toString();
            if (requestString.contains("GET /title?name=")) {
                String[] parts = requestString.split(" ");
                String title = parts[1].substring("/title?name=".length());
                try {
                    String movieInfo = Cache.inMemory(title);
                    out.println("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + movieInfo);
                } catch (IOException e) {
                    out.println("HTTP/1.1 500 Internal Server Error\r\n\r\nError processing request");
                }
            } 
            String[] parts = requestString.split(" ");
            String resource = parts[1].substring(1);
            try {
                serveFile(resource, clientSocket.getOutputStream());
            } catch (IOException e) {
                out.println("HTTP/1.1 404 Not Found\r\n");
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Sirve un archivo estático al cliente.
     * 
     * @param filename   Nombre del archivo a servir.
     * @param outStream  Flujo de salida del cliente.
     * @throws IOException Si ocurre un error de entrada/salida al leer o escribir el archivo.
     */

    private static void serveFile(String filename, OutputStream outStream) throws IOException {
        File file = new File(STATIC_FILES_PATH + filename);
        if (file.exists() && !file.isDirectory()) {
            String contentType = getContentType(filename);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
    
            // Escribe encabezados de respuesta HTTP
            PrintWriter out = new PrintWriter(outStream, true);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType);
            out.println(); 
    
            // Escribe contenido del archivo en el flujo de salida
            while ((bytesRead = fis.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            fis.close();
        } else {
            // Si el archivo no existe, devolver código de error 404
            PrintWriter out = new PrintWriter(outStream, true);
            out.println("HTTP/1.1 404 Not Found\r\n");
        }
    }


    /**
     * Obtiene el tipo de contenido MIME para un archivo dado.
     * 
     * @param filename Nombre del archivo.
     * @return Tipo de contenido MIME.
     */
    private static String getContentType(String filename) {
        if (filename.endsWith(".html")) {
            return "text/html";
        } else if (filename.endsWith(".css")) {
            return "text/css";
        } else if (filename.endsWith(".js")) {
            return "text/javascript";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

}
