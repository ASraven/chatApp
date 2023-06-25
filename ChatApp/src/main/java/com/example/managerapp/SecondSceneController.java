package com.example.managerapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class SecondSceneController {

    @FXML
    private TextField hostID;
    @FXML
    private TextField mymsgid;
    @FXML
    private TextField portID;
    @FXML
    private ListView testView;

    PrintWriter pw;
    @FXML
    protected void onconnect() throws Exception {
       String host = hostID.getText();
       int port = Integer.parseInt(portID.getText());
        Socket s = new Socket(host,port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = s.getOutputStream();
        String Ip = s.getRemoteSocketAddress().toString();
         pw = new PrintWriter(os,true);
        new Thread(()->{
            while (true){  try {
                String reponse = br.readLine();
                Platform.runLater(() -> {
                    testView.getItems().add(reponse);
                });
            }catch (IOException e) {
                e.printStackTrace();
            }}

        }).start();
            }

            @FXML
            public void onsubmit(){
                String message =mymsgid.getText();
                pw.println(message);
            }

}