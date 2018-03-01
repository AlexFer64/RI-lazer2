package com.projet15.lazer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;



public class StaticExerciceParameter extends AppCompatActivity {

    //Variables
    //Objet graphique

    private Toolbar toolbar;
    private EditText patientName, operatorName, markDistance, time;
    private TextInputLayout layoutPatientName, layoutOperatorName, layoutMarkDistance, layoutTime;
    private Button buttonStart;
    //Parameter
    public Parameter parametreDeLexercice;

    //Constructeur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_exercice_parameter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back)); //mettre comme icône de naviguation ic_action_back (flêche)
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //mettre un listner à l'icone qui reviens au menu principal quand on clique dessus
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MenuPrincipal.class);
                startActivity(i); //lancement de l'activité menu principal
            }
        });


        //textInputLayout
        layoutPatientName = (TextInputLayout) findViewById(R.id.PATIENT_NAME_LAYOUT_ID);
        layoutOperatorName = (TextInputLayout) findViewById(R.id.OPERATOR_NAME_LAYOUT_ID);
        layoutMarkDistance = (TextInputLayout) findViewById(R.id.MARK_DISTANCE_LAYOUT_ID);
        layoutTime = (TextInputLayout) findViewById(R.id.TIME_LAYOUT_ID);

        //editText
        patientName = (EditText) findViewById(R.id.PATIENT_NAME_EDITTEXT_ID);
        operatorName = (EditText) findViewById(R.id.OPERATOR_NAME_EDITTEXT_ID);
        markDistance = (EditText) findViewById(R.id.MARK_DISTANCE_EDITTEXT_ID);
        time = (EditText) findViewById(R.id.TIME_EDITTEXT_ID);

        buttonStart = (Button) findViewById(R.id.START_BUTTON_ID);


        //set les listener
        patientName.addTextChangedListener(new textChangedListener(patientName));
        operatorName.addTextChangedListener(new textChangedListener(operatorName));
        markDistance.addTextChangedListener(new textChangedListener(markDistance));
        time.addTextChangedListener(new textChangedListener(time));

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });
    }

    //Getteur Setteur

    //Méthode
    //Validation formulaire
    private void submitForm() {
        if (!validatePatientName()) { //si le nom du patient n'est pas valide
            return; //on valide pas le formulaire
        }

        if (!validateMarkDistance()) {
            return;
        }

        if (!validateTime()) {
            return;
        }

        //sinon on passe à la vue suivante (CODE A RAJOUTER)

        Toast.makeText(getApplicationContext(), "la vue suivante n'existe pas encore", Toast.LENGTH_SHORT).show();
    }

    //validation PatientName
    private boolean validatePatientName() {
        //trim() enlève les espaces
        if (patientName.getText().toString().trim().isEmpty()) { //si le nom du patient est vide
            layoutPatientName.setError(getString(R.string.Err_Patient_Name)); //on met en message d'erreur Err_Patient_Name
            requestFocus(patientName);
            return false; //est on return false (nom non-validé)
        } else {
            layoutPatientName.setErrorEnabled(false); //sinon le patientName est valide donc si le patientName a déjà été validé on enlève le message d'erreur
        }

        return true; //et on retourne true(nom validé)
    }

    private boolean validateOperatorName() {
        if (operatorName.getText().toString().trim().isEmpty()) {
            layoutOperatorName.setError(getString(R.string.Err_Operator_Name));
            requestFocus(operatorName);
            return false;
        } else {
            layoutOperatorName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMarkDistance() {
        if (markDistance.getText().toString().trim().isEmpty()) {
            layoutMarkDistance.setError(getString(R.string.Err_Mark_Distance));
            requestFocus(markDistance);
            return false;
        } else {
            layoutMarkDistance.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTime() {
        if (time.getText().toString().trim().isEmpty()) {
            layoutTime.setError(getString(R.string.Err_Time));
            requestFocus(time);
            return false;
        } else {
            layoutTime.setErrorEnabled(false);
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
                case R.id.PATIENT_NAME_EDITTEXT_ID:
                    validatePatientName();
                    break;
                case R.id.OPERATOR_NAME_EDITTEXT_ID:
                    validateOperatorName();
                    break;
                case R.id.MARK_DISTANCE_EDITTEXT_ID:
                    validateMarkDistance();
                    break;
                case R.id.TIME_EDITTEXT_ID:
                    validateTime();
                    break;
            }
        }


    }
}



