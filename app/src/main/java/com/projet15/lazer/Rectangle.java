package com.projet15.lazer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.widget.ImageView;


public class Rectangle {

    private ImageView mImageView;
    private Camera.Parameters params;

    public Rectangle(Camera.Parameters _params, ImageView _im)
    {
        this.params = _params;
        this.mImageView = _im;

    }

    public void dessinRectangle() {
        Bitmap bitmap = Bitmap.createBitmap(
                params.getPreviewSize().width, // Width
                params.getPreviewSize().height, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.TRANSPARENT);

        // Initialize a new Paint instance to draw the Rectangle
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb( 255, 0, 0));

        // Set a pixels value to padding around the rectangle
        int ecart = 50;

        // Initialize a new Rect object
        Rect rectangle = new Rect(
                canvas.getWidth()/2 - ecart, // Left
                canvas.getHeight()/2 - ecart, // Top
                canvas.getWidth()/2 + ecart,// Right
                canvas.getHeight()/2 + ecart // Bottom
        );

        // Initialize a new Rect object
        Rect rectangleDroite = new Rect(
                canvas.getWidth()/2 - 200 - ecart, // Left
                canvas.getHeight()/2 - ecart, // Top
                canvas.getWidth()/2 - 200 + ecart,// Right
                canvas.getHeight()/2 + ecart // Bottom
        );

        // Initialize a new Rect object
        Rect rectangleGauche = new Rect(
                canvas.getWidth()/2 + 200 - ecart, // Left
                canvas.getHeight()/2 - ecart, // Top
                canvas.getWidth()/2 + 200 + ecart,// Right
                canvas.getHeight()/2 + ecart // Bottom
        );

        // Finally, draw the rectangle on the canvas
        canvas.drawRect(rectangleDroite,paint);

        // Finally, draw the rectangle on the canvas
        canvas.drawRect(rectangleGauche,paint);

        // Finally, draw the rectangle on the canvas
        canvas.drawRect(rectangle,paint);

        // Display the newly created bitmap on app interface
        mImageView.setImageBitmap(bitmap);
    }


}
