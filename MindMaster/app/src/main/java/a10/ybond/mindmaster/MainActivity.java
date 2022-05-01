package a10.ybond.mindmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 56. Getting rid of the top action bar @04:51 in part 2
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    // 1. onlick Play button tagged in activity_main.xml
    // @02:25 to navigate to our next activity
    // surface view is used when updating our screen at the velocity of 30 times per second
    public void onPlayClicked(View view)
    {

        // 20. We will make a new Intent here in line @ 13:19
        startActivity(new Intent(MainActivity.this,  GameActivity.class));
    }

}