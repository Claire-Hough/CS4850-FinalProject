/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Claire
 */
public class NetworksClient {


    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
 
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
 
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
    //Loop and accept user console input
    public static void main(String[] args) throws IOException {
    NetworksClient client = new NetworksClient();
    client.startConnection("127.0.0.1", 18741);
    
        System.out.println("Enter a command:");
        while(true){
        Scanner input = new Scanner(System.in);
            String message = input.nextLine();

            if(".".equals(message)){
                client.stopConnection();
                return;
            }

            String response = client.sendMessage(message);

            System.out.println("From server: " + response);
        }
    }
    
    //login      
    //logout
    //send        
    //newUser
}
