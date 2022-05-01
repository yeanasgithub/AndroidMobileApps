package a10.ybond.mindmaster;

import android.content.Intent;
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

    // 60. We need to know which rwo we are currently on
    private int currentRow = 0;

    // 65. @ 34:51 in part 2
    private PlayButton playButton;

    // 74. Making computer solution lists @ 53:59
    private final List<Integer> solutionList = new ArrayList<>();

    // 77. @ 02:04 in part 3
    // To store the feedback pegs, we need a list container
    private final List<Peg> resultPegs = new ArrayList<>();

    // 83. To go ending game, we will reference this boolean variable
    private boolean win = false;

    // 84. @ 29:10 in part 3, after modifying the constructor below,
    private final GameActivity gameActivity;

    // 89. To instantiate hidingSolution class @ 42:54 in part 3
    private HidingSolution hidingSolution;


    // 28. Bitmap to draw the board itself
    private Bitmap background;


    // 27. Constructor @ 22:29
    // we need to know the size of the screen to draw a board
    // and to place the pegs correctly
    // Point for screenSize and
    // Resources file to get our image drawn in the background
    public Board(Point screenSize, Resources resources, GameActivity gameActivity)
    {
        this.gameActivity = gameActivity; // 84. @29:40 in part 3
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

        // 90. @ 44: 57, after this we need to also draw this in draw()
        Point tmpCoverPos = new Point();
        tmpCoverPos.x = pegList.get(pegList.size() - 4).getPos().x - pegRadius;
        tmpCoverPos.y = pegList.get(pegList.size() - 4).getPos().y - pegRadius;
        hidingSolution = new HidingSolution(tmpCoverPos, pegRadius * 11,  pegRadius * 2);



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

                // 78. @02:22 every fourth peg is generated, the feedback pegs are created?
                if(col == 3)
                {
                    int resultPegRadius = Math.round(pegRadius * 0.4f);
                    resultPegs.add(new Peg(0,
                            resultPegRadius, new Point(tmp.getPos().x + pegRadius *3, Math.round(tmp.getPos().y - resultPegRadius * 1.2f))));
                    resultPegs.add(new Peg(0,
                            resultPegRadius, new Point(tmp.getPos().x + pegRadius *4, Math.round(tmp.getPos().y - resultPegRadius * 1.2f))));
                    resultPegs.add(new Peg(0,
                            resultPegRadius, new Point(tmp.getPos().x + pegRadius *3, Math.round(tmp.getPos().y + resultPegRadius * 1.2f))));
                    resultPegs.add(new Peg(0,
                            resultPegRadius, new Point(tmp.getPos().x + pegRadius *4, Math.round(tmp.getPos().y + resultPegRadius * 1.2f))));

                }
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
        for(Peg p : pegList)  {  p.draw(canvas);  }
        // 79. We need to draw the result pegs to have them shown @ 05:32 in part 3
        for(Peg p: resultPegs)  {  p.draw(canvas);  }

        // 69. We need to also draw our play button @ 39:30 in part 2
        playButton.draw(canvas);

        // 91. drawing hidden solution @ 45:43 in part 3
        hidingSolution.draw(canvas);

        // 93. @ 49:44 hidingSolution is drawn every frame, and eventually offScreen becomes true
        // then the game is ended
        if (hidingSolution.off_Screen) {  goEndingGame();  }


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
        // This is where we control result pegs to show only after the play button is clicked!!!
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

        // copy the solution list to temp list
        List<Integer> tmpSolution = new ArrayList<>();
        tmpSolution.addAll(solutionList);

        // 80. We are looking for the exact matches first, then, for color matches second
        // @ 09:01 in part 3
        // we need a increasing reference to count how many exactly matched pegs in a row list
        int emptyPeg = 0;

        int exactMatches = 0;
        // we are decrementing i to prevent index out of bound case from happening
        // since we are removing a matched peg from rowList
        for (int i = 3; i >= 0; i--)
        {
            if(rowList.get(i) == tmpSolution.get(i))
            {
                // a green result peg indicates an exact match
                resultPegs.get(currentRow * 4 + emptyPeg).setColor(3);
                emptyPeg++;
                exactMatches++;
                rowList.remove(i);
                tmpSolution.remove(i);
            }
        }

        // 81. @15:43 in part 3, We are looking for color matches second
        for (int i = rowList.size() - 1; i >= 0; i--)
        {
            for (int k = tmpSolution.size() - 1; k >= 0; k--)
            {
                if (rowList.get(i) == tmpSolution.get(k))
                {
                    // a yellow result peg indicates just a color match
                    resultPegs.get(currentRow * 4 + emptyPeg).setColor(4);
                    emptyPeg++; // yellow result pegs show followed by the green result pegs
                                // since the variable emptyPeg might have been increased above already
                    rowList.remove(i);
                    tmpSolution.remove(k);
                    break;

                    // 82. @ 21:50 in part 3, we are adding a new empty activity to show
                    // if the game is over  - - - > activity_end_game.xml

                }
            }
        }


        // 82. Check win condition
        if(exactMatches == 4)
        {
            win = true;
            // 92. @ 48:05
            hidingSolution.show = true;
            // goEndingGame(); @ 48:21 in part 3 so that we can see the solution
        }

        else if (currentRow == 8) // check lose condition
        {

            hidingSolution.show = true;

            //commenting this out @ 48:21 in part 3 so that we can see the solution
            //goEndingGame();

        }

        else {  advanceCurrentRow();  }  // advance current row

    }

    // 83. Ending game @ 25:47 in part 3
    private void goEndingGame()
    {
        // this is not going to work, we need to go to GameView that is passed to GameActivity.java
        // @ 27:52 in part 3
        // Intent intent = new Intent(this, EndGameActivity.class
        Intent intent = new Intent(gameActivity, EndGameActivity.class);

        // 85. @31:00  showing the screen of ending activity
        intent .putExtra("win", win);
        gameActivity.startActivity(intent);

        // 88. @ 35:11 in part 3 the current activity should be finished as well
        // so it can bring back to the very first page
        gameActivity.finish();




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






















