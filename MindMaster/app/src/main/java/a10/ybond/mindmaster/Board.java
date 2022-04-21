package a10.ybond.mindmaster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Board {

    // 33. @ 31:22
    private Paint paint  = new Paint();

    // 34. But, what are we going to draw from? -> Go to GameView.java
    // Board object

    // 41. We need to know the board x position to center the image
    private final int boardXPos;

    // 45. we need a list to hold the pegs
    private final List<Peg> pegList = new ArrayList<>();
    // 48.
    private final int pegRadius;

    // 49. @ 54:39 integer is used for pixels
    // to determine vertical starting point and horizontal starting point of the first row
    private final int rowVerticalSpace;
    private final int rowYOffset;

    // 28. Bitmap to draw the board itself
    private Bitmap background;


    // 27. Constructor @ 22:29
    // we need to know the size of the screen to draw a board
    // and to place the pegs correctly
    // Point for screenSize and
    // Resources file to get our image drawn in the background
    public Board(Point screenSize, Resources resources)
    {
        // 29. Bitmap from our resource to bring in
        background = BitmapFactory.decodeResource(resources, R.drawable.board);
        // 30. To contain all the images from Download folder @ 25:45
        // res > new > Android Resource Directory > name and type : drawable
        // Available qualifier : Density
        // Make identical folders and copy each image and paste it to its corresponding folder
        // Now, we have res > drawable > board > board mdpi  ~ xxxhdpi  images

        // 40. We are going to scale the image size according to the device's screen size @ 36:00
        background = Bitmap.createScaledBitmap(background, (int) (screenSize.y * 0.66), screenSize.y, true);

        // 42. Calculating board's X Position @39:32
        // boardXPos = screenSize.x / 2; // this is centered with the left top corner of the image
        boardXPos = (screenSize.x /2) - (background.getWidth() / 2);

        // 48. @52:53
        pegRadius = background.getHeight() / 30;

        // 50. @55:24
        rowVerticalSpace = background.getHeight() / 11;
        rowYOffset = background.getHeight() / 15;

        // 44.
        generatePegs();

    }

    private void generatePegs()
    {
        // 46. 10 loops for the rows of pegs and loop for the columns @ 51:00
        for(int row = 0; row < 10; row++)
        {
            for(int col = 0; col < 4; col++)
            {
                // @53:00
                // this equation is found after lots of trials
                int pegX = pegRadius * col * 3 + boardXPos + pegRadius * 2;
                int pegY = row * rowVerticalSpace + rowYOffset + row * 2;

                // 51. Now we can make a peg @56:55
                Peg tmp = new Peg(0, pegRadius, new Point(pegX, pegY));
                pegList.add(tmp);
            }
        }

        // 52. @1:00:00
        // Computer generated color set solution here
        for(int i = pegList.size() - 1; i > pegList.size() - 5; i--)
        {
            // 0 to 5
            pegList.get(i).setColor((int) (Math.random() * 5 + 1));
        }

    }

    // 31. We are going to draw first @ 31:00
    public void draw(Canvas canvas)
    {
        // 32. Making Paint method above
        canvas.drawBitmap(background, boardXPos, 0, paint);

        // 52. After making pegs, we need to draw them
        for(Peg p : pegList)
        {
            p.draw(canvas);
        }

    }

    // 54. @ 1:07:04
    // @ 1:09:23 But, onClick() is not called automatically,
    // Rather, it gets called inside GameView.java,
    // We need to override on Touch event, GameView is what is shown on the screen
    // and then call our Board onClick() and pass the coordinance of that touch to it
    // - > go to GameView.java
    public void onClick(int x, int y)
    {
        // iterate through all pegs to check if a peg is clicked or not
        Point point = new Point(x, y);
        for(Peg p : pegList)
        {
            p.isPegClicked(point);
        }
    }



}






















