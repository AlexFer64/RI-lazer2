package com.projet15.lazer;

import android.app.ActionBar;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import java.io.IOException;
import java.security.Policy;

import static android.content.ContentValues.TAG;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback
{
    private SurfaceHolder mHolder;
    private Camera mCamera;

    //Settings de la caméra
    private Camera.Parameters parameters;
    //taille de la "preview" de la caméra
    private android.hardware.Camera.Size previewSize;
    //tableau pour stocker les pixels en paires hexadécimales
    private int[] pixels;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;




        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

            //récupérer les param de la caméra
            parameters = mCamera.getParameters();
            previewSize = parameters.getPreviewSize();
            pixels = new int[previewSize.width * previewSize.height]; //width sont des attributs et n'ont pas de getters

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        try {
            mCamera.stopPreview();
            android.hardware.Camera.Size s = mCamera.getParameters().getPreviewSize();
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();

            //récupérer les param de la caméra
            parameters = mCamera.getParameters();
            previewSize = parameters.getPreviewSize();
            Log.e("TEST WIDTH", Integer.toString(previewSize.width));
            Log.e("TEST HEIGH", Integer.toString(previewSize.height));
            pixels = new int[previewSize.width * previewSize.height]; //width sont des attributs et n'ont pas de getters
        } catch (Exception ex) {
            return;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //transformer les pixels du formats NV21 au format RGB
        decodePixels(pixels, data, previewSize.width, previewSize.height);


        //Log.i("Pixels", "The top right pixel has the following RGB (hexadecimal) values:" +Integer.toHexString(pixels[0]));
    }

    //decodePIxels
    public static void decodePixels(int[] argb, byte[] yuv, int width, int height) { //YUV_NV21_TO_RGB
        final int frameSize = width * height;

        final int ii = 0;
        final int ij = 0;
        final int di = +1;
        final int dj = +1;

        int a = 0;
        for (int i = 0, ci = ii; i < height; ++i, ci += di) {
            for (int j = 0, cj = ij; j < width; ++j, cj += dj) {
                int y = (0xff & ((int) yuv[ci * width + cj]));
                int v = (0xff & ((int) yuv[frameSize + (ci >> 1) * width + (cj & ~1) + 0]));
                int u = (0xff & ((int) yuv[frameSize + (ci >> 1) * width + (cj & ~1) + 1]));
                y = y < 16 ? 16 : y;

                int r = (int) (1.164f * (y - 16) + 1.596f * (v - 128));
                int g = (int) (1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = (int) (1.164f * (y - 16) + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);

                argb[a++] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
    }
}