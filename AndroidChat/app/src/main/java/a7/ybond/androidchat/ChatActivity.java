package a7.ybond.androidchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements CanWriteMessage{
    // to get userName
    private String userName;
    private Socket socket;
    private Client client;
    private ArrayList<String> messages, members;
    private RecyclerView messagesList, membersList;
    private EditText txtMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // getIntent() is a built-in function
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");


        startClient(userName);
        txtMessage = findViewById(R.id.txtMessage);
        messagesList = findViewById(R.id.messagesList);
        membersList = findViewById(R.id.membersList);
        messages = new ArrayList<>();
        members = new ArrayList<>();

        // Setting recycler adapter and layout manager
        messagesList.setAdapter(new RecyclerAdapter(this, messages));
        messagesList.setLayoutManager(new LinearLayoutManager(this));

        membersList.setAdapter(new RecyclerAdapter(this, members));
        membersList.setLayoutManager(new LinearLayoutManager(this));

        // on screen keyboard arrow clicked, we want the message to be sent
        txtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE) {
                    onSendClicked(textView);
                    return true;
                }
                return false;
            }
        });

        // when the keyboard pops up, our layout can be adjusted
        // in the chat window, it will show the message at the very bottom
        messagesList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                messagesList.scrollToPosition(messages.size() - 1);
            }

        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if our socket is open and it is not null, we want to close it
        if(socket != null && !socket.isClosed()) {
            try {    socket.close();    }
            catch (IOException e) {     e.printStackTrace();    }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // if onPause() is called then it will finish the app (logging out the user)
        this.finish();
    }

    private void startClient(String userName) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try
                {
                    //initialize the socket here
                    socket = new Socket("odin.cs.csub.edu", 3390);
                    client = new Client(socket, userName);
                    client.listenForMessages(ChatActivity.this);
                    client.sendMessage(userName);
                }

                catch (IOException e) {     e.printStackTrace();    }

            }
        }; // why semi colon here

        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void onSendClicked(View v) {

        // we want to hide the on-screen keyboard after the arrow is clicked
        hideOnscreenKeyboard(this);
        String messageToSend = txtMessage.getText().toString();
        client.sendMessage(userName + ": " + messageToSend);
        txtMessage.setText(null);
    }

    public static void hideOnscreenKeyboard(Activity activity) {

        // imm stands for Input Method Manager
        // we need to cast it as an InputMethodManager
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // we need reference to the view that we focus
        View view = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    public void writeMessage(String message) {

        // the very first message received should be our name
        // messages.add(message);
        // instead of writing messages, we need to determine what kind of message it is
        switch(message.charAt(0)) {
            case '+':
                members.add(message.substring(1));
                break;
            case '-':
                members.remove(message.substring(1));
                break;
            default:
                messages.add(message);

        }


        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // To make it run on UI thread
                messagesList.getAdapter().notifyDataSetChanged();
                membersList.getAdapter().notifyDataSetChanged();

                // To make the chat window automatically shows the most recent,
                // the very bottom messages according to the size
                // e.g. when the on-screen keyboard is in use, the chat window size shrinks
                messagesList.scrollToPosition(messages.size() - 1);
            }
        });


    }

    // move down by Ctrl+Shift+down arrow key

    public static Intent createIntent(Context context, String userName) {

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userName", userName);
        return intent;
    }
}