package a10.ybond.mindmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        // 86. @31:19 in part 3
        Intent intent = getIntent(); // to get intent which has been brought
        // obtaining the boolean from the intent above
        boolean win = intent.getBooleanExtra("win", false);
        TextView textView = findViewById(R.id.txtGameOver);
        textView.setText(win? "You are a Mind Master!" : "Game Over");

        // 87. @ 33:26 Handling play again button
        Button btn = findViewById(R.id.btnPlayAgain);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}