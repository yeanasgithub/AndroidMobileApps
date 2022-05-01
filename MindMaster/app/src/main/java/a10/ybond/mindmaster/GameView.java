package a10.ybond.mindmaster;

// 3. GameView is created @05:37

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    // 6. we are going to draw on the screen at the speed of 30 frames per second
    // so we are going to make a variable for the value @07:33
    private final int FPS = 1000/30;

    // 8. we need to make a Thread object
    private Thread thread;

    // 12. In our run class, we need to see if a thread runs or not
    private boolean isPlaying;

    // 35. @32:00
    private Board board;

    // 24. To make the red circle fall due to the gravity effect
    // private int x=100, y=100;

    // 4. this is going to be a new thread, so it implements a runnable
    // in order to have all the drawing take place on the new thread

    // 83. @ 28:09 we are passing in the context of game activity

    public GameView(Context context, Point screenSize) {
        super(context);
        // 36. we are using the Board object here
        // Board requires a screenSize which can be obtained from GameActivity
        // board = new Board(screenSize, getResources());
        board = new Board(screenSize, getResources(), (GameActivity)context);

        // 37. Inside GameActivity.java we need a screenSize inside onCreate function as well
    }

    @Override
    public void run() {

        // 15. boolean isPlaying becomes the while loop's condition @11:47
        // we are calling resume() and pause()  in the GameActivity.java
        while(isPlaying)
        {
            // 7. call update, draw, and sleep methods @ 08:41
            // sort of like a render()
            // update a mouse movement, button pressed, etc
            update();
            // draws a new frame
            draw();
            // to keep FPS at a certain value
            sleep();
        }

    }

    // 5. making our own methods @ 06:38

    private void update()
    {
        // 25. inside draw (), we are using x and y value that is updated here
        // y += 4;


    }

    private void draw()
    {
        // 21. Let's draw on the screen
        // First there is a canvas @14:45
        if(getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas();
            // 26. To remove the weird string effect @ 19:47
            // Clearing the canvas each time of drawing
            canvas.drawColor(Color.BLACK);

            // 39. calling board to be drawn
            board.draw(canvas);

            // ----------- removing this test lines for the simple game loop
            // Paint paint = new Paint();
            // paint.setARGB(255,255,0,0);
            // canvas.drawCircle(100, 100, 20, paint);
            // canvas.drawCircle(x, y, 20, paint);
            // -----------End of removing lines @ 20:11

            // we have to unlock and post this to the screen
            getHolder().unlockCanvasAndPost(canvas);
            // 22. We have to make this shown inside GameActivity.java @ 17:36

        }

    }

    public void pause()
    {
        // 14. setting the boolean isPlayingnow's value according to the method
        isPlaying = false;

        // 10. pausing the thread
        // joining this thread that is running in a loop to a main thread
        // which will pause eventually
        try {
            thread.join();
        }
        catch (InterruptedException e) {    e.printStackTrace();        }

    }

    public void resume()
    {
        // 13. @11:25
        isPlaying = true;

        // 9. instantiating Thread @ 09:58
        // a new thread on this class
        thread = new Thread(this);
        thread.start();


    }

    public void sleep()
    {
        // 11. to keep the velocity of frame @ 10:49
        try {
            Thread.sleep(FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 55. @1:09:57
    // We need to override onTouchEvent, GameView is what is shown on the screen
    // and then call our Board onClick() and pass the coordinance of that touch to it
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            int x = Math.round(event.getX());
            int y = Math.round(event.getY());
            board.onClick(x, y);
        }

        return true;
    }


}
















