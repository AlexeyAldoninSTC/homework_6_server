package app.server;

import app.service.CurrentDirectoryFilesListCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyServer {

    /**
     * Method to launch hand-made server
     */
    public void run(){
        try (ServerSocket serverSocket = new ServerSocket(8088)) {
            System.out.println("Server started!");

            while (!serverSocket.isClosed()) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    if (input.ready()) {
                        String s = input.readLine();
                        //Анализируем полученный запрос
                        if (s.startsWith("GET")) {
                            CurrentDirectoryFilesListCollector collector = new CurrentDirectoryFilesListCollector();
                            output.println("HTTP/1.1 200 OK");
                            output.println("Content-Type: text/html; charset=utf-8");
                            output.println();
                            output.println("<p>" + collector.fileList() + "</p>");
                        } else {
                            output.println("HTTP/1.1 404 Not Found");
                            output.println("Content-Type: text/html; charset=utf-8");
                            output.println();
                            output.println("<p>Bad Request</p>");
                        }
                        output.flush();
                    }
                    System.out.println("Client disconnected!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
