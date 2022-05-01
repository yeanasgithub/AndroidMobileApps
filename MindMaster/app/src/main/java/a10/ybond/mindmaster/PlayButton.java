package a10.ybond.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class PlayButton {

    // 62. 29:00 in part 2
    // we need to know point, height to make the indicator button
    private Point pos = new Point();
    private int height;

    // On the screen, we need a Paint object
    private Paint paint = new Paint();

    // 63. we need a triable shape
    // path is like a polygon in Android Canvas
    private Path path;



    public PlayButton(Point pos, int height) {
        this.pos = pos;
        this.height = height;
        this.paint.setARGB(255,0,255,0);

    }

    public void draw(Canvas canvas)
    {
        // 64. @ 31:42 in part 2
        // path is going to change every time we draw based on the each position
        path = new Path();
        // we are moving the point where we are going to start drawing to our top of the
        // left of the triangle
        path.moveTo(pos.x, pos.y - height / 2f);
        path.lineTo(pos.x + height, pos.y);
        path.lineTo(pos.x, pos.y + height / 2f);
        path.close();
        canvas.drawPath(path, paint);

    }

    // 70. @46:15 We need to know if the play button is clicked or not - - - -boolean method
    // play button is 90 degree right rotated triagle, so we need to find the pos.x and pos.y
    // range accordingly to detect the triangle button (play button)  is clicked or not
    public boolean isPlayButtonClicked(Point point)
    {
        return (point.x >= pos.x && point.x <= pos.x + height &&
                point.y >= pos.y - height /2 && point.y <= pos.y + height / 2);
    }

    public void moveDown(int rowVerticalSpace) {
        pos.y += rowVerticalSpace;
    }
}
