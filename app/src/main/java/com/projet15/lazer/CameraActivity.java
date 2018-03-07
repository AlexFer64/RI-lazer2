package com.projet15.lazer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import 	android.hardware.Camera;

import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private Intent _intent;
    private Parameter _parametreDeLexercice;
    private TextView _callBack;
    private Camera _camera;
    private CameraPreview _preview;
    private Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //Recupération des données du formulaire
        _intent = getIntent();
        _parametreDeLexercice = (Parameter) _intent.getSerializableExtra("parameters");

        //Récupération du bouton et du texte
        _callBack = (TextView) findViewById(R.id.CALLBACK_TEXT_VIEW_ID);
        _button = (Button) findViewById(R.id.TAKE_PICTURE_BUTTON_ID);

        //Création d'une instance de la caméra
        _camera = getCameraInstance();

        //Paramétrage de l'autofocus
        Camera.Parameters params = _camera.getParameters(); //paramètres de la caméra

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            //Le mode autofocus est supporté
            Camera.Parameters ParametreCamera = _camera.getParameters(); //récuperer les paramètres de la caméra
            ParametreCamera.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO); //rajouter le mode autofocus
            _camera.setParameters(ParametreCamera); //réattribuer les nouveaux paramètres à la caméra
        }

        //Création de la prévisualisation et paramétrage de cette application comme contenu de du framelayout
        _preview = new CameraPreview(this, _camera);
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

}
