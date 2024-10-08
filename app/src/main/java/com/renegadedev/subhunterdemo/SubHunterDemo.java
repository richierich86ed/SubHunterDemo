package com.renegadedev.subhunterdemo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

public class SubHunterDemo extends Activity {

    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = -100;
    int subHorizontalPosition;
    int subVerticalPosition;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;

    /*
        Here are all the objects(instances)
        of classes that we need to do some drawing.
    */
    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;

    /*
        Android runs this code just before the app is seen by the player.
        This makes it a good place to add the code that is needed for
        the one-time setup.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Get the current device's screen resolution
        */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        /*
            Initialize our size based variables
            based on the screen resolution
        */
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;

        /*
            Initialize all the objects ready for drawing.
        */
        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();

        /*
            Tell Android to set our drawing
            as the view for this app.
        */
        setContentView(gameView);

        Log.d("Debugging", "In onCreate");
        newGame();
        draw();
        }

    /*
        This code will execute when a new
        game needs to be started. It will
        happen when the app is first started
        and after the player wins a game.
    */
    void newGame() {
        Random random = new Random();
        subHorizontalPosition = random.nextInt(gridWidth);
        subVerticalPosition = random.nextInt(gridHeight);
        shotsTaken = 0;

        Log.d("Debugging", "In newGame");
    }

    /*
        Here we will do all the drawing.
        The grid lines, the HUD and
        the touch indicator.
    */
    void draw() {
        gameView.setImageBitmap(blankBitmap);

        /*
            Wipe the screen with a white color.
        */
        canvas.drawColor(Color.argb(255,255,255,255));

        /*
            Change the paint color to black
        */
        paint.setColor(Color.argb(255,0,0,0));

        /*
            Draw the vertical lines of the grid.
        */
        for(int i = 0; i < gridWidth; i++) {
            canvas.drawLine(blockSize * i, 0,
                    blockSize * i, numberVerticalPixels,
                    paint);
        }

        /*
            Draw the horizontal lines of the grid.
        */
        for(int i = 0; i < gridHeight; i++) {
            canvas.drawLine(0, blockSize * i,
                    numberHorizontalPixels, blockSize * i,
                    paint);
        }

        canvas.drawRect(horizontalTouched * blockSize,
                verticalTouched * blockSize,
                (horizontalTouched * blockSize) + blockSize,
                (verticalTouched * blockSize) + blockSize,
                paint);

        /*
            Re-size the text appropriate for the
            score and distance text.
        */
        paint.setTextSize(blockSize * 2);
        paint.setColor(Color.argb(255,0,0,255));
        canvas.drawText("Shots Taken: " + shotsTaken +
                "   Distance: " + distanceFromSub,
                blockSize, blockSize * 1.75f,
                paint);

        Log.d("Debugging", "In draw");
    }

    /*
        This part of the code will
        handle detecting that the player
        has tapped the screen.
    */
    @Override public boolean onTouchEvent(MotionEvent motionEvent){
        Log.d("Debugging", "In onTouchEvent");

        if((motionEvent.getAction() &
                MotionEvent.ACTION_MASK)
                == MotionEvent.ACTION_UP) {
            takeShot(motionEvent.getX(), motionEvent.getY());
        }

        return true;
    }

    /*
        The code here will execute when
        the player taps the screen. It will
        calculate the distance from teh sub'
        and decide a hit or miss.
    */
    void takeShot(float touchX, float touchY) {

        shotsTaken ++;

        horizontalTouched = (int)touchX/ blockSize;
        verticalTouched = (int)touchY/ blockSize;

        hit = horizontalTouched == subHorizontalPosition
                && verticalTouched == subVerticalPosition;

        int horizontalGap = (int)horizontalTouched -
                subHorizontalPosition;
        int verticalGap = (int)verticalTouched -
                subVerticalPosition;

        distanceFromSub = (int)Math.sqrt(
                ((horizontalGap * horizontalGap) +
                        (verticalGap * verticalGap)));

        if(hit)
            boom();
        else draw();

    }

    /*
        This code says "Boom!".
    */
    void boom() {
        gameView.setImageBitmap(blankBitmap);

        canvas.drawColor(Color.argb(255,255,0,0));

        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(blockSize * 10);

        canvas.drawText("BOOM!", blockSize * 4,
                blockSize * 14, paint);

        paint.setTextSize(blockSize * 2);
        canvas.drawText("Take a shot to start again",
                blockSize * 8,
                blockSize * 18, paint);

        newGame();
    }
}