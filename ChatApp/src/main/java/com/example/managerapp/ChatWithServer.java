package com.example.managerapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatWithServer extends Thread {
    private int ClientNbr;
    private List<Communication> clientsConnectes = new ArrayList<Communication>();
    public static void main(String[] args) {
        new ChatWithServer().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Starting the server...");
            while (true) {
                Socket s = ss.accept();
                ++ClientNbr;
                Communication NewCommunication = new Communication(s, ClientNbr);
                clientsConnectes.add(NewCommunication);
                NewCommunication.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class Communication extends Thread {

        private Socket s;
        private int ClientNbr;

        Communication(Socket s, int ClientNbr) {
            this.s = s;
            this.ClientNbr = ClientNbr;
        }
        @Override
        public void run() {
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("The client number " + ClientNbr + "and his IP adress " + Ip);
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("You are the client number : " + ClientNbr);
                pw.println("Send your message ");
                while (true) {
                    String UserRequest = br.readLine();
                    if (UserRequest.contains("=>")){
                        String[] usermessage = UserRequest.split("=>");
                        if(usermessage.length ==2){
                            String msg = usermessage[1];
                            int numeroClient = Integer.parseInt(usermessage[0]);
                            Send(msg,s,numeroClient);
                        }
                    }
                    else {
                        Send(UserRequest,s,-1);
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        void Send(String UserRequest,Socket socket, int nbr) throws IOException {
            for(Communication client: clientsConnectes){
                if (client.s != socket) {
                    if (client.ClientNbr == nbr || nbr == -1) {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(UserRequest);
                    }
                }
            }
        }
    }
}
