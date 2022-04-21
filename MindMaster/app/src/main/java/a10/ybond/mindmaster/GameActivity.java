package a10.ybond.mindmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    // 16. GameView's Pause and Resume are called in this class
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 38. @ 33:43
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        // 17. GameView takes context of this
        gameView = new GameView(this, screenSize);
        // 23. To make GameView shown
        setContentView(gameView);
    }


    // 2. res > new > activity > no layout : created this GameActivity.java file
    // @ 04:02 We are going to have two methods
    // When we navigate to Game Activity, we are creating a new class from which
    // Game view calls then, we are going to draw on that surface view
    // 3. 05:24 let's make our Game view now: GameView.java class

    @Override
    protected void onPause()
    {
        super.onPause();
        // 19. we have to transfer from GameActivity to MainActivity (MainActivity.java)
        gameView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 18. this will make a separate thread @ 13:00
        gameView.resume();;
    }

}