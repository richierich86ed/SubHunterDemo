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
    boolean debugging = true;

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

        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();
        setContentView(gameView);

        // Get the current device's screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize our size based variables
        // based on the screen resolution
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;

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
        canvas.drawColor(Color.argb(255,255,255,255));

        Log.d("Debugging", "In draw");
        printDebuggingText();
    }

    /*
        This part of the code will
        handle detecting that the player
        has tapped the screen.
     */
    @Override public boolean onTouchEvent(MotionEvent motionEvent){
        Log.d("Debugging", "In onTouchEvent");

        takeShot();
        draw();

        return true;
    }

    /*
        The code here will execute when
        the player taps the screen. It will
        calculate the distance from teh sub'
        and decide a hit or miss.
     */
    void takeShot() {

    }

    /*
        This code says "Boom!".
     */
    void boom() {

    }

    /*
        This code prints the debugging text.
     */
    void printDebuggingText() {
        Log.d("numberHorizontalPixels", "" + numberHorizontalPixels);
        Log.d("numberVerticalPixels", "" + numberVerticalPixels);

        Log.d("blockSize", "" + blockSize);
        Log.d("gridWidth", "" + gridWidth);
        Log.d("gridHeight", "" + gridHeight);
        Log.d("horizontalTouched", "" + horizontalTouched);
        Log.d("verticalTouched", "" + verticalTouched);
        Log.d("subHorizontalPosition", "" + subHorizontalPosition);
        Log.d("subVerticalPosition", "" + subVerticalPosition);

        Log.d("hit", "" + hit);
        Log.d("shotsTaken", "" + shotsTaken);
        Log.d("debugging", "" + debugging);

        Log.d("distanceFromSub", "" + distanceFromSub);
    }
}