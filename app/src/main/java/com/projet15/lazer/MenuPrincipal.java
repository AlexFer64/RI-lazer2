package com.projet15.lazer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MenuPrincipal extends  AppCompatActivity{

    //Variables
    private Button _staticButton; // représente le bouton "Static Exercice"
    private Button _rythmButton; // représente le bouton "Rythm Exercice"
    private Button _managerButton; // représente le bouton "Data Manager"

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    //Constructeur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        if (ContextCompat.checkSelfPermission(this, //Regarde si l'accès à la camera est autorisé
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
                ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

        }

        //transitions
        setupWindowAnimations();
        //http://lgvalle.xyz/2015/06/07/material-animations/
        //https://developer.android.com/training/transitions/index.html
        //https://developer.android.com/training/transitions/start-activity.html#custom-trans


        //association des objets Java aux objets graphique
        set_staticButton((Button) findViewById(R.id.STATIC_BUTTON_ID));
        set_rythmButton((Button) findViewById(R.id.RYTHM_BUTTON_ID));
        set_managerButton((Button) findViewById(R.id.DATAMANAGER_BUTTON_ID));

        //mise en attente de click des différents bouton
        //clique sur Static exercice affichage de l'écrans de parametrage de l'exercice
        get_staticButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    Intent i = new Intent(view.getContext(), StaticExerciceParameter.class); //association de l'activité principale et de l'activité exercice statique parameter
                    startActivity(i); //lancement de l'activitéexercice statique parameter
                } else {
                    // Start without transition
                    Intent i = new Intent(view.getContext(), StaticExerciceParameter.class); //association de l'activité principale et de l'activité exercice statique parameter
                    startActivity(i); //lancement de l'activitéexercice statique parameter
                }


            }
        });

        get_rythmButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RythmExerciceParameter.class);
                startActivity(i);
            }
        });

        get_managerButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO:lié l'activité DataMangerActivity
                Toast.makeText(getApplicationContext(), "la vue suivante n'existe pas encore", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    //Getteur & setteur
    public void set_staticButton (Button b){
        _staticButton = b;
    }
    public Button get_staticButton(){
        return _staticButton;
    }
    public void  set_rythmButton(Button b){
        _rythmButton = b;
    }
    public Button get_rythmButton(){
        return _rythmButton;
    }
    public void  set_managerButton(Button b){
        _managerButton = b;
    }
    public Button get_managerButton(){
        return _managerButton;
    }

    //Méthodes
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    // La permission est refusée
                }
                return;
            }
        }
    }

}
