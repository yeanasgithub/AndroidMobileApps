package a7.ybond.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener extends Thread {

    // another thread to listen any message from the server
    // this class needs a socket and bufferedReader to read from our server
    private final Socket socket;
    private BufferedReader bufferedReader;
    private CanWriteMessage canWriteMessage;

    public ClientListener(Socket socket, CanWriteMessage canWriteMessage) {
        this.socket = socket;
        this.canWriteMessage = canWriteMessage;
        try  {    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    }
        catch (IOException e) {     e.printStackTrace();    }
    }


    @Override
    public void run() {
        String messageFromServer;
        while (socket.isConnected())
        {
            try
            {
                // blocking operation
                messageFromServer = bufferedReader.readLine();
                System.out.println(messageFromServer);
                // make our thread able to talk back
                canWriteMessage.writeMessage(messageFromServer);
            }

            catch (IOException e)
            {
                closeEverything();
                break;
            }

        }
    }

    private void closeEverything() {
        try
        {
            if(bufferedReader != null) {    bufferedReader.close();     }
            if(socket != null) {    socket.close();     }

        }

        catch (IOException e) {     e.printStackTrace();    }
    }

}