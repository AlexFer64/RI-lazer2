package com.projet15.lazer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import 	android.hardware.Camera;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private Intent _intent;
    private Parameter _parametreDeLexercice;
    private TextView _callBack;
    private Camera _camera;
    private CameraPreviewPixel _preview;
    //private Button _button;
    private FrameLayout displayColor;

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
        //_button = (Button) findViewById(R.id.TAKE_PICTURE_BUTTON_ID);
        displayColor = (FrameLayout) findViewById(R.id.FRAME_LAYOUT_COLOR_ID);

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

            _camera.setParameters(ParametreCamera); //réattribuer les nouveaux paramètres à la caméra
        }

        //Création de la prévisualisation et paramétrage de cette application comme contenu de du framelayout
        _preview = new CameraPreviewPixel(this, _camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.CAMERA_FRAME_LAYOUT_ID);
        preview.addView(_preview);


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
            //transformer les pixels du formats NV21 au format RGB
            //decodePixels(pixels, data, previewSize.width, previewSize.height);
            //decodePixels(pixels, data, parameters.getPreviewSize().width , parameters.getPreviewSize().height);

            //mettre à droite la couleur du pixel le plus en haut à droite

            //displayColor.setBackgroundColor(pixels[0]);
            _callBack.setText("format: " + parameters.getPreviewFormat());
            //test logcat
            String TAG = "aaaaaaaaaaaaaaaaaaaaaaa";
            Log.v(TAG, "visiblement c'est un peu capilotracté");
            //Log.i("Pixels", "The top right pixel has the following RGB (hexadecimal) values:" +Integer.toHexString(pixels[0]));
        }
    }
}
