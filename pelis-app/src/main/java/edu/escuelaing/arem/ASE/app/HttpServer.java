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
                outputLine = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Movie Search</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <style>\n" +
                "            body {\n" +
                "                background-color: #00eeff;\n" +
                "            }\n" +
                "\n" +
                "            h1,label {\n" +
                "                color: rgb(0, 0, 0);\n" +
                "                font-size: 40px;\n" +
                "            }\n" +
                "\n" +
                "            form {\n" +
                "                margin-top: 20px;\n" +
                "            }\n" +
                "\n" +
                "            #getrespmsg {\n" +
                "                margin-top: 20px;\n" +
                "            }\n" +
                "\n" +
                "            input[type=\"button\"] {\n" +
                "                background-color: purple;\n" +
                "                color: white;\n" +
                "                border: none;\n" +
                "                padding: 10px 20px;\n" +
                "                text-align: center;\n" +
                "                text-decoration: none;\n" +
                "                display: inline-block;\n" +
                "                font-size: 16px;\n" +
                "                cursor: pointer;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <center><h1>MOVIE NAME</h1></center>\n" +
                "        <center><form action=\"/hello\">\n" +
                "            <center><label for=\"name\">TITLE:</label><br></center>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"Guardians of the galaxy\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form></center>\n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/title?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "    </body>\n" +
                "</html>";
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
