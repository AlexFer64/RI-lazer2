package com.projet15.lazer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import android.widget.Toast;



public class StaticExerciceParameter extends AppCompatActivity {

    //Variables
    //Objet graphique

    private Toolbar _toolbar;
    private EditText _patientName, _operatorName, _markDistance, _time;
    private TextInputLayout _layoutPatientName, _layoutOperatorName, _layoutMarkDistance, _layoutTime;
    private Button buttonStart;
    //Parameter


    //Constructeur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_exercice_parameter);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        _toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back)); //mettre comme icône de naviguation ic_action_back (flêche)
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() { //mettre un listner à l'icone qui reviens au menu principal quand on clique dessus
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MenuPrincipal.class);
                startActivity(i); //lancement de l'activité menu principal
            }
        });


        //textInputLayout
        _layoutPatientName = (TextInputLayout) findViewById(R.id.STATIC_PATIENT_NAME_LAYOUT_ID);
        _layoutOperatorName = (TextInputLayout) findViewById(R.id.STATIC_OPERATOR_NAME_LAYOUT_ID);
        _layoutMarkDistance = (TextInputLayout) findViewById(R.id.STATIC_MARK_DISTANCE_LAYOUT_ID);
        _layoutTime = (TextInputLayout) findViewById(R.id.STATIC_TIME_LAYOUT_ID);

        //editText
        _patientName = (EditText) findViewById(R.id.STATIC_PATIENT_NAME_EDITTEXT_ID);
        _operatorName = (EditText) findViewById(R.id.STATIC_OPERATOR_NAME_EDITTEXT_ID);
        _markDistance = (EditText) findViewById(R.id.STATIC_MARK_DISTANCE_EDITTEXT_ID);
        _time = (EditText) findViewById(R.id.STATIC_TIME_EDITTEXT_ID);

        buttonStart = (Button) findViewById(R.id.STATIC_START_BUTTON_ID);

        //animations
        setupWindowAnimations();

        //set les listener
        _patientName.addTextChangedListener(new textChangedListener(_patientName));
        _operatorName.addTextChangedListener(new textChangedListener(_operatorName));
        _markDistance.addTextChangedListener(new textChangedListener(_markDistance));
        _time.addTextChangedListener(new textChangedListener(_time));

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                submitForm(view);

            }
        });


    }

    //Getteur Setteur

    //Méthode
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }


    //Validation formulaire
    private void submitForm(View view) {
        if (!validatePatientName()) { //si le nom du patient n'est pas valide
            return; //on valide pas le formulaire
        }

        if(!validateOperatorName()){
            return;
        }

        if (!validateMarkDistance()) {
            return;
        }

        if (!validateTime()) {
            return;
        }

        //sinon on passe à la vue suivante (CODE A RAJOUTER)
        Parameter parametreDeLexercice = new Parameter(1,
                _patientName.getText().toString(),
                _operatorName.getText().toString(),
                Float.parseFloat(_markDistance.getText().toString()),
                Integer.parseInt(_time.getText().toString()));

        Intent i = new Intent(view.getContext(),Camera.class);
        i.putExtra("parameters",parametreDeLexercice);
        startActivity(i);
       // Toast.makeText(getApplicationContext(), "la vue suivante n'existe pas encore", Toast.LENGTH_SHORT).show();
    }

    //validation PatientName
    private boolean validatePatientName() {
        //trim() enlève les espaces
        if (_patientName.getText().toString().trim().isEmpty()) { //si le nom du patient est vide
            _layoutPatientName.setError(getString(R.string.Err_Patient_Name)); //on met en message d'erreur Err_Patient_Name
            requestFocus(_patientName);
            return false; //est on return false (nom non-validé)
        } else {
            _layoutPatientName.setErrorEnabled(false); //sinon le patientName est valide donc si le patientName a déjà été validé on enlève le message d'erreur
        }

        return true; //et on retourne true(nom validé)
    }

    private boolean validateOperatorName() {
        if (_operatorName.getText().toString().trim().isEmpty()) {
            _layoutOperatorName.setError(getString(R.string.Err_Operator_Name));
            requestFocus(_operatorName);
            return false;
        } else {
            _layoutOperatorName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMarkDistance() {
        if (_markDistance.getText().toString().trim().isEmpty()) {
            _layoutMarkDistance.setError(getString(R.string.Err_Mark_Distance));
            requestFocus(_markDistance);
            return false;
        } else {
            _layoutMarkDistance.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTime() {
        if (_time.getText().toString().trim().isEmpty()) {
            _layoutTime.setError(getString(R.string.Err_Time));
            requestFocus(_time);
            return false;
        } else {
            _layoutTime.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

    private class textChangedListener implements TextWatcher {

        private View view;

        private textChangedListener(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

        }

        public void afterTextChanged(Editable editable){ //après qu'on est changé le texte d'un champ, on attend pas la validation pour vérifer que c'est nom vide: pour pouvoir supprimer les messsages d'erreur dès qu'on entre quelque chose
            switch (view.getId()) {
                case R.id.STATIC_PATIENT_NAME_EDITTEXT_ID:
                    validatePatientName();
                    break;
                case R.id.STATIC_OPERATOR_NAME_EDITTEXT_ID:
                    validateOperatorName();
                    break;
                case R.id.STATIC_MARK_DISTANCE_EDITTEXT_ID:
                    validateMarkDistance();
                    break;
                case R.id.STATIC_TIME_EDITTEXT_ID:
                    validateTime();
                    break;
            }
        }


    }
}



