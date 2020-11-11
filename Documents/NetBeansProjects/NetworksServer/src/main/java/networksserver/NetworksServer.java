/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author Claire
 */
public class NetworksServer {

    //private static final Logger log = LoggerFactory.getLogger(NetworksServer.class);
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MyService myService;

    public NetworksServer() {
        this(new MyService());
    }

    public NetworksServer(MyService myService) {
        this.myService = myService;
        this.myService.init();
    }

    public String parseInput(String message) {
        // check params are valid
        //log.debug( "parseInput: message = {}", message);
        if (message == null || message.length() == 0) {
            return null;
        }
        // split input by spaces
        String[] tokens = message.split(" ");
        //System.out.println("Length of tokens: " + tokens.length);

        
        if ("send".equalsIgnoreCase(tokens[0])) {

            // call send function to determine response
            String result = myService.send();

            // return error message if user isn't logged in
            if (result == null) {
                return "A user must be logged in before the send command can be used.";
            }

            // return user message with userId in front of it
            return message.replace("send", (result + ":"));

            
        } else if ("login".equalsIgnoreCase(tokens[0])) {

            // check if two params were passed in for login
            if (tokens.length != 3) {
                return "Invalid command parameters: login requires a userId and password.";
            }

            // call login function to determine response
            LoginStatus result = myService.login(tokens[1], tokens[2]);

            if (result == LoginStatus.ACCEPTED) {
                return (tokens[1] + " is now logged in.");
            } else if (result == LoginStatus.BLOCKED) {
                return "Cannot login while another user is logged in.";
            } else if (result == LoginStatus.ERROR) {
                return "Invalid command parameters: login requires a userId and password.";
            } else if (result == LoginStatus.REJECTED) {
                return "The submitted userId or password is incorrect.";
            }

            // return error if none of the expected responses were received
            return "There was an error with the login method.";

            
        } else if ("newuser".equalsIgnoreCase(tokens[0])) {
            // check if two params were passed in for login
            if (tokens.length != 3) {
                return "Invalid command parameters: newuser requires a userId and password.";
            }

            // call newUser function to determine response
            NewUserStatus response = myService.newUser(tokens[1], tokens[2]);

            if (response == NewUserStatus.ACCEPTED) {
                return (tokens[1] + " is now a user.");
            } else if (response == NewUserStatus.BLOCKED) {
                return "Cannot create a new user while someone is logged in.";
            } else if (response == NewUserStatus.ERROR) {
                return "The userId must be less than 32 characters, and the password must be between 4 and 8 characters.";
            } else if (response == NewUserStatus.NOT_UNIQUE) {
                return "The userId you submitted is already taken. Choose a different userId.";
            }

            // return error if none of the expected responses were received
            return "There was an error with the newuser method.";

            
        } else if ("logout".equalsIgnoreCase(tokens[0])) {
            // call logout function to determine response
            boolean response = myService.logout();

            if (response) {
                return "Logout successful.";
            }

            return "There is no one logged in.";
        }

        // return if submission doesn't match any commands
        return "Valid commands are: send, login <userId password>, newuser<userId password>, logout.";

    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        System.out.println("Server started on port " + port);

        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                out.println("good bye");
                break;
            }

            System.out.println("From client: " + inputLine);

            String newMessage = parseInput(inputLine);

            out.println(newMessage);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "OFF");

        NetworksServer server = new NetworksServer();
        server.start(18741);

    }
}
