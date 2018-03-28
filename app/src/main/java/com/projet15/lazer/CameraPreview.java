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
    //camera
    private Camera mCamera;
    //Settings de la caméra
    private Camera.Parameters parameters;
    //taille de la "preview" de la caméra

    //définition  la taille de la préview
    int HAUTEURPREVIEW=480;
    int LARGEURPREVIEW=720;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        // Install a SurfaceHolder.Callback so we get notified when the underlying surface is created and destroyed.


        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);

            //paramétrer la taille de la préview
            parameters = mCamera.getParameters();
<<<<<<< HEAD
            parameters.setPreviewSize(LARGEURPREVIEW,HAUTEURPREVIEW);
            mCamera.setParameters(parameters);
=======
            parameters.setPreviewSize(720,480);
            mCamera.setParameters(parameters);
            previewSize = parameters.getPreviewSize();
            pixels = new int[previewSize.width * previewSize.height]; //width sont des attributs et n'ont pas de getters
>>>>>>> master

            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        try {
            mCamera.stopPreview();

            //paramétrer la taille de la préview
            parameters = mCamera.getParameters();
            parameters.setPreviewSize(LARGEURPREVIEW,HAUTEURPREVIEW);

            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();

<<<<<<< HEAD

            Log.e("TEST WIDTH", Integer.toString(parameters.getPreviewSize().width));
            Log.e("TEST HEIGH", Integer.toString(parameters.getPreviewSize().height));
=======
            //récupérer les param de la caméra
            parameters = mCamera.getParameters();
            parameters.setPreviewSize(720,480);
            previewSize = parameters.getPreviewSize();
            Log.e("TEST WIDTH", Integer.toString(previewSize.width));
            Log.e("TEST HEIGH", Integer.toString(previewSize.height));
            pixels = new int[previewSize.width * previewSize.height]; //width sont des attributs et n'ont pas de getters
>>>>>>> master
        } catch (Exception ex) {
            return;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //fonction exécuté à chaque frame capturé
    }


}