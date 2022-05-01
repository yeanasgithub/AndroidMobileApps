package a10.ybond.mindmaster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

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

    // 60. We need to know which rwo we are currently on
    private int currentRow = 0;

    // 65. @ 34:51 in part 2
    private PlayButton playButton;

    // 74. Making computer solution lists @ 53:59
    private final List<Integer> solutionList = new ArrayList<>();

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

        // 57. replacing what we did in 40
        if (screenSize.y * 0.63f > screenSize.x)
        {
            background = Bitmap.createScaledBitmap(background, screenSize.x, (int)(screenSize.x * 1.63f), true);
        }

        else
        {
            background = Bitmap.createScaledBitmap(background, (int)(screenSize.y * 0.63f), screenSize.y, true);
        }

        // 40. We are going to scale the image size according to the device's screen size @ 36:00
        //background = Bitmap.createScaledBitmap(background, (int) (screenSize.y * 0.66), screenSize.y, true);

        // 42. Calculating board's X Position @39:32
        // boardXPos = screenSize.x / 2; // this is centered with the left top corner of the image
        boardXPos = (screenSize.x /2) - (background.getWidth() / 2);

        // 48. @52:53
        // pegRadius = background.getHeight() / 30;

        // 58. Modifying our pegs radius and rowYOffset @16:20 in part 2
        // Length of the peg radius is determined by the vertical length of the screen
        pegRadius = Math.round(background.getHeight() / 35f);
        //rowYOffset = background.getHeight() / 15;
        rowYOffset = Math.round(pegRadius + background.getHeight() / 30f);
        rowVerticalSpace = Math.round(pegRadius + (background.getHeight() / 14.7f));

        // 50. @55:24
        //rowVerticalSpace = background.getHeight() / 11;


        // 44.
        generatePegs();

        // 66. Per row, we have to draw our play button
        // we are going to a new point and set it based on the last peg of the current row position
        Point tmp = new Point();
        // 68. @38:45 in part 2
        // we need to get the tmp's x posiiton
        // which is the x position of the last peg plus some offset value
        // so that it is displayed a little away from the pegs nicely
        tmp.x = Math.round(pegList.get(currentRow * 4 + 3).getPos().x + pegRadius * 1.2f);
        tmp.y = pegList.get(currentRow * 4 + 3).getPos().y;

        // 69. Creating our point button
        playButton = new PlayButton(tmp, Math.round(pegRadius * 0.9f));


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
                //int pegY = row * rowVerticalSpace + rowYOffset + row * 2;
                // 59.  For some reason, run-time debugging can take several hours LOL
                // @ 25:00 in part 2
                int pegY = rowYOffset + row * rowVerticalSpace;

                // 51. Now we can make a peg @56:55
                Peg tmp = new Peg(0, pegRadius, new Point(pegX, pegY));
                pegList.add(tmp);
            }
        }

        // 52. @1:00:00
        // Computer generated color set solution here
        for(int i = pegList.size() - 1; i > pegList.size() - 5; i--)
        {
            // 75. @55:00 in part 2
            int tmp = (int) (Math.random() * 5) + 1;
            // adding each color at the zeroth position  @56:30 in part 2
            solutionList.add(0, tmp);
            pegList.get(i).setColor((int) tmp);
            // 0 to 5
            // pegList.get(i).setColor((int) (Math.random() * 5 + 1));
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

        // 69. We need to also draw our play button @ 39:30 in part 2
        playButton.draw(canvas);

    }

    // 54. @ 1:07:04
    // @ 1:09:23 But, onClick() is not called automatically,
    // Rather, it gets called inside GameView.java,
    // We need to override on Touch event, GameView is what is shown on the screen
    // and then call our Board onClick() and pass the coordinance of that touch to it
    // - > go to GameView.java
    public void onClick(int x, int y) {
        // iterate through all pegs to check if a peg is clicked or not
        Point point = new Point(x, y);
        //for(Peg p : pegList)
        //{
        //    p.isPegClicked(point);
        // } deleting this loop @ 25:54 in part 2

        // 61. To track which row we are on currently
        // each row has 4 pegs @ 28:45 in part 2
        for(int i = currentRow*4; i < currentRow*4 + 4; i++)
        {
            pegList.get(i).isPegClicked(point);
        }

        // 71. @46:33
        // Board.java handles all the clicking
        // isPlayButton is getting wired with Board

        if (playButton.isPlayButtonClicked(point))
        {
            // currentRow++;
            // To see if out log catches this or not
            // Log.i("NEXT_ROW", "Current Row: " + currentRow);
            // 71. @ 51:29 calling the function to evaluate current row
            evalRow();

        }

    }

    // 72. a new function to evaluate each row
    // check our answers against solution
    private void evalRow()
    {
        // 76. Through the solution list and using for loop
        List<Integer> rowList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            rowList.add(pegList.get(currentRow * 4 + i).selectedPaint);
        }

        // advance current row
        advanceCurrentRow();
    }

    private void advanceCurrentRow()
    {
        // To make the max row the user can use 9th row
        // currentRow = (currentRow + 1) % 8;

        // 73. From the last row, the play button should be moved to the first row
        // to keep advancing @ 52:33 in part 2
        currentRow++;
        // moving the play button as well
        playButton.moveDown(rowVerticalSpace);
    }







}






















