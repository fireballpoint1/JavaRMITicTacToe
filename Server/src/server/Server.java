package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private int clientLimit;
    private List<ClientConnection> clients;
    
    public void start(int port, int clientLimit) {
        try {
            serverSocket = new ServerSocket(port);
            this.clientLimit = clientLimit;
            clients = new ArrayList();
            Console.print("Server started at " + InetAddress.getLocalHost().getHostAddress()
                    + ":" + port + " | client limit: " + clientLimit);
            new Thread(() -> acceptConnections()).start();
        } catch (IOException ex) {}
    }
    
    public void stop() {
        clients.forEach(client -> client.farewell());
        try {
            serverSocket.close();
        } catch (IOException ex) {}
    }
    
    private void acceptConnections() {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                Console.print("New connection from " + clientSocket.getRemoteSocketAddress().toString());
                new ClientConnection(clientSocket, this);
            } catch (IOException ex) {}
        }
    }
    
    public boolean addClient(ClientConnection client) {
        if (clients.size() < clientLimit) {
            clients.add(client);
            Console.print("New client added");
            return true;
        } else {
            Console.print("Server is full | client limit: " + clientLimit);
            return false;
        }
    }
    
    public boolean removeClient(ClientConnection client) {
        if (clients.remove(client)) {
            Console.print("Client removed");
            return true;
        } else {
            Console.print("No such client");
            return false;
        }
    }
    
    public boolean makeGame(ClientConnection clientB) {
        clientB.clearGame();
        ClientConnection clientA = findIdleClient(clientB);
        if (clientA != null) {
            Console.print("Found a client to pair with");
            ServerGame serverGame = new ServerGame(clientA, clientB);
            clientA.setGame(serverGame);
            clientB.setGame(serverGame);
            serverGame.start();
            return true;
        }
        return false;
    }
    
    private ClientConnection findIdleClient(ClientConnection clientB) {
        return clients.stream()
                .filter(clientA -> clientA != clientB && !clientA.isInGame())
                .findFirst().orElse(null);
    }
    
    public static void main(String[] args) {
        int port = 9000;
        int clientLimit = 100;
        
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {}
        }
        
        if (args.length >= 2) {
            try {
                clientLimit = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {}
        }
        
        Server server = new Server();
        server.start(port, clientLimit);
        try {
            Console.print("Enter 'W' to stop the server");
            InputStreamReader kbd = new InputStreamReader(System.in);
            int c = kbd.read();
            if (c == 'W' || c == 'w') {
                server.stop();
                Console.print("Server stopped");
                System.exit(0);
            }
        } catch (IOException ex) {}
    }
}