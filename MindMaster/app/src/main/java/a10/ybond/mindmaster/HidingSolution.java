package a10.ybond.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class HidingSolution {

    // 88. @37:15
    private Point pos;
    private final Paint paint = new Paint();
    private int width, height;
    public boolean show = false;
    public boolean offScreen = false; // done showing the result peg

    // 94. making our cover completely dark
    // public int alpha = 155;
    public int alpha = 255;



    public HidingSolution(Point pos, int width, int height) {
        paint.setARGB(alpha, 50, 50, 50); // dark grey color
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas)
    {
        // drawing a rectangle to hide solution
        canvas.drawRect(new Rect(pos.x, pos.y, pos.x + width, pos.y + height), paint);

        if (show && !offScreen)
        {
            alpha = alpha - 5;  // Question: how does this subtraction work to un-hide the solution
                                // at any point that the user got all pegs right?
            paint.setARGB(alpha, 50, 50, 50);
            // offScreen is used to know when there is a transition on the board @ 42:19 in part 3
            if (alpha <= 0) {   offScreen = true;   }
        }
    }
}
