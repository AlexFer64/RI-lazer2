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
    //private Button _button;
    private FrameLayout displayColor;

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

    //quand l'activité passe en arrière plan
   /* @Override
    protected void onPause() {

        super.onPause();

        //TODO:Sauvegarder l'instance de l'appli
        //TODO:stoper la préview
        //TODO:Eteindre  la camera

        _camera.release();

    }
    //reprise de l'activité en cour
    @Override
    protected void onResume() {

        super.onResume();
        //TODO:Reprendre l'activité là où elle en était
        _camera.open();
    }
    //arret de l'activité
    @Override
    protected void onStop() {

        super.onStop();
        //TODO:Eteindre  la camera
        _camera.release();
        //TODO:stoper la préview
    }
*/

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
    //variable pour conversion:
    int rTmp=0;
    int gTmp=0;
    int bTmp=0;

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
            final int frameSize = width * height;
            YUVTOPIXELRGB(data[0],data[frameSize],data[frameSize+2]);

            displayColor.setBackgroundColor(Color.rgb(rTmp,gTmp,bTmp));


            //Log.e("pixel en haut à gauche:","");
            //Log.e("Rouge: ", Integer.toString(rTmp));
            //Log.e("Vert :", Integer.toString(gTmp));
            //Log.e("Bleu :", Integer.toString(bTmp));

        }
    }

    void YUVTOPIXELRGB(byte yValue, byte uValue, byte vValue)
    {
        rTmp = (int)((float)(yValue) + (1.370705 * ((float)(vValue) - 128)));
        gTmp = (int)((float)(yValue) - (0.698001 * ((float)(vValue) - 128)) - (0.337633 * ((float)(uValue) - 128)));
        bTmp = (int)((float)(yValue) + (1.732446 * ((float)(uValue) - 128)));
        rTmp = clamp(rTmp, 0, 255);
        gTmp = clamp(gTmp, 0, 255);
        bTmp = clamp(bTmp, 0, 255);
    }

    int clamp(int x, int min, int max)
    {
        if(x<min){
            x=min;
        }
        else if(x>max){
            x=max;
        }
        return x;
    }
}
