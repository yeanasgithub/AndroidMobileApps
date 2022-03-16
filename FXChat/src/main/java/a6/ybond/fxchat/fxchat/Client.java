package a6.ybond.fxchat.fxchat;

import java.io.*;
import java.net.Socket;

public class Client {
    // own copy of the socket
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String userName;

    // constructor
    public Client(Socket socket, String userName) {
        // constructor for the Client method

        try
        {   // using method for the socket, and to get the socket,
            // we need bufferedWriter and bufferedReader
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = userName;
        }
        catch (IOException e) {    closeEverything();  }
    }

    public void sendMessage(String message) {

        //sendMessage method
        try
        {
            if (socket.isConnected())
            {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e) {    closeEverything();  }

    }


    public void closeEverything() {

        try
        {
            if (bufferedReader != null) {   bufferedReader.close();     }
            if (bufferedWriter != null) {   bufferedWriter.close();     }
            if (socket != null) {   socket.close();     }

        }

        catch (IOException e) {    e.printStackTrace();    }

    }

    public String getUserName() {
        return userName;
    }

    public void listenForMessages(CanWriteMessage canWriteMessage) {
        ClientListener clientListener = new ClientListener(this.socket, canWriteMessage);
        clientListener.start();
    }
}
