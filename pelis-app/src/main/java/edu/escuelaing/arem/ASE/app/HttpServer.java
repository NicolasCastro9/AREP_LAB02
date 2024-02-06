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
            String inputLine, outputLine;
            String title = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(inputLine.contains("title?name")){
                    // se extrae el valor del name del URL
                    String[] firstSplit = inputLine.split("=");
                    title = (firstSplit[1].split("HTTP"))[0];
                }
                if (!in.ready()) {
                    break;
                }
            }
            // Respuesta HTTP si la pelicula se encuentra en el cache
            if(!title.isEmpty()){
                String cachedInfo = Cache.inMemory(title);
                outputLine = cachedInfo;
            }else {
            // Respuesta HTTP por defecto cuando se hace nueva busqueda o no se encuentra la pelicula
                try {
                    
                    File file = new File("pelis-app/src/main/Resources/index.html");
                    String absolutePath = file.getAbsolutePath();
                    FileReader fileReader = new FileReader(absolutePath);

                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    fileReader.close();
                    String content = stringBuilder.toString();
                    outputLine = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: " + content.length() + "\r\n" +
                            "\r\n" +
                            content;
                } catch (FileNotFoundException e) {
                    outputLine = "HTTP/1.1 404 Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "\r\n" +
                            "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "    <head>\n" +
                            "        <title>404 Not Found</title>\n" +
                            "    </head>\n" +
                            "    <body>\n" +
                            "        <h1>404 Not Found</h1>\n" +
                            "        <p>The requested resource was not found on this server.</p>\n" +
                            "    </body>\n" +
                            "</html>";
            }
        }
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
