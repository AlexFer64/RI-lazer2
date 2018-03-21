package com.projet15.lazer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import 	android.hardware.Camera;


import java.util.ArrayList;




import java.util.List;
import java.util.Optional;


//TODO:récuperer les marqueurs
//TODO:récupéré les coordonnées du laser en fonction du temps et les enregistrer dans un variables "list<>"par exemple ou un tableau
//TODO: gerer le temps de l'exercice
//TODO:tracker le laser sur l'écran
//TODO:Emetre des bip à intervalle régulier
//TODO:sauvegarder les coordonnées dans un fichier csv
//TODO:Géré les different scénario d'erreurs (voir maquettes et scénario)

public class CameraActivity extends AppCompatActivity {
    private Intent _intent;
    private Parameter _parametreDeLexercice;
    private TextView _callBack;
    private Camera _camera;

    private CameraPreviewPixel _preview;

    private FrameLayout displayColor;
    private ImageView mImageView;




    //Cycle de Vie de l'application
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        String TAG = "aaaaaaaaaaaaaaaaaaaaaaa";
        Log.v(TAG, "index");

        //Recupération des données du formulaire
        _intent = getIntent();
        _parametreDeLexercice = (Parameter) _intent.getSerializableExtra("parameters");

        //Récupération du bouton et du texte
        _callBack = (TextView) findViewById(R.id.CALLBACK_TEXT_VIEW_ID);
        displayColor = (FrameLayout) findViewById(R.id.FRAME_LAYOUT_COLOR_ID);
        mImageView = (ImageView) findViewById(R.id.iv);

        //displayColor.setBackgroundColor(Color.parseColor("#FFFF0000"));

        //Création d'une instance de la caméra
        _camera = getCameraInstance();

        //Paramétrage de l'autofocus
        Camera.Parameters params = _camera.getParameters(); //paramètres de la caméra

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            //Le mode autofocus est supporté
            Camera.Parameters ParametreCamera = _camera.getParameters(); //récuperer les paramètres de la caméra
            ParametreCamera.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO); //rajouter le mode autofocus
            //ParametreCamera.setPictureFormat(ImageFormat.FLEX_RGB_888);
            ParametreCamera.setPreviewSize(1920,1080);
            _camera.setParameters(ParametreCamera); //réattribuer les nouveaux paramètres à la caméra
        }

        dessinRectangle(params);

        //Création de la prévisualisation et paramétrage de cette application comme contenu de du framelayout
        _preview = new CameraPreviewPixel(this, _camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.CAMERA_FRAME_LAYOUT_ID);
        preview.addView(_preview);
    }

    public void dessinRectangle(Camera.Parameters params) {
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

        // Finally, draw the rectangle on the canvas
        canvas.drawRect(rectangle,paint);

        // Display the newly created bitmap on app interface
        mImageView.setImageBitmap(bitmap);

    }

    @Override
    public void onPause() {
        Log.e("onpause","L'activité est en pause");
        super.onPause();
        finish();
    }


    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    private void releaseCamera() {
        _preview.mCamera = null;
        if (_camera != null) {
            _camera.stopPreview();
            _camera.release();        // release the camera for other applications
            _camera = null;

        }
    }


    /**     ----------------------------------------------------------------------------
     *      |                                                                          |
     *      |               TEST CAMERA PREVIEW AMELIOREE                              |
     *      |                                                                          |
     *      ----------------------------------------------------------------------------*/


    public class CameraPreviewPixel extends CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback {
     private SurfaceHolder mHolder;
        private Camera mCamera;

        //Settings de la caméra
        private Camera.Parameters parameters;
        //taille de la "preview" de la caméra
        private android.hardware.Camera.Size previewSize;
        //tableau pour stocker les pixels en paires hexadécimales
        private int[] pixels;

        public CameraPreviewPixel(Context context, Camera camera) {
            super(context,camera);

        }

        public void onPreviewFrame(byte[] data, Camera camera) {
            parameters = camera.getParameters();
            _callBack.setText(String.valueOf((int) data[6]));

            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;
            YuvToRgb test = new YuvToRgb(data,width,height);
            int [] tab;

            tab = test.decode() ;

            displayColor.setBackgroundColor(tab[1]);

        }

        @Override
        public void  surfaceDestroyed(SurfaceHolder holder){

        }
    }






}
