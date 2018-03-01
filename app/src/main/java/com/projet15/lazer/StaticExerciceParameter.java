package com.projet15.lazer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Toast;

import org.w3c.dom.Text;

<<<<<<< HEAD
public class StaticExerciceParameter extends AppCompatActivity implements TextWatcher {

    //Variables
        //Objet graphique
    private EditText _patientNameET;
    private EditText _operatorNameET;
    private EditText _markDistanceET;
    private EditText _timeET;
    private Button _startButton;
    private TextView _errorText;


    //Parameter
     public Parameter parametreDeLexercice;
    //Constructeur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getText(R.string.Static_Exercice));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_exercice_parameter);

        //association des objet graphique avec les composant graphique
        set_patientNameET((EditText) findViewById(R.id.PATIENT_NAME_EDITTEXT_ID));
        set_operatorNameET((EditText) findViewById(R.id.OPERATOR_NAME_EDITTEXT_ID));
        set_markDistanceET((EditText) findViewById(R.id.MARKDISTANCE_NAME_EDITTEXT_ID));
        set_timeET((EditText) findViewById(R.id.TIME_EDITTEXT_ID));
        set_startButton((Button) findViewById(R.id.START_BUTTON_ID));
        set_errorText((TextView) findViewById(R.id.ERROR_TEXT_ID));

        //Désactivation du bouton au lancement (peut etre temporaire)
        get_startButton().setEnabled(false);

        //mise a l'écoute du changement du texte dans les champs pour validation du formulaire
        get_patientNameET().addTextChangedListener(this);
        get_operatorNameET().addTextChangedListener(this);
        get_markDistanceET().addTextChangedListener(this);
        get_timeET().addTextChangedListener(this);

        //Evenement clique sur le bouton start
        get_startButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametreDeLexercice = new Parameter(1,get_patientNameET().toString(),
                                                    get_operatorNameET().toString(),
                                                    Float.parseFloat(get_markDistanceET().toString()),
                                                    Integer.parseInt(get_timeET().toString()
                                                    ));
                

=======
public class StaticExerciceParameter extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText patientName, operatorName, markDistance, time;
    private TextInputLayout layoutPatientName, layoutOperatorName, layoutMarkDistance, layoutTime;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_exercice_parameter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
>>>>>>> interfaceMAJ
            }
        });
    }

    //Validation formulaire
    private void submitForm() {
        if (!validatePatientName()) { //si le nom du patient n'est pas valide
            return; //on valide pas le formulaire
        }

<<<<<<< HEAD
    public TextView get_errorText() {
        return _errorText;
=======
        if (!validateOperatorName()) {
            return;
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
>>>>>>> interfaceMAJ
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

<<<<<<< HEAD
    //Premiere vérification : Formulaire compet
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /*Activation du bouton quand tous les champs sont remplis
        permet d'évité la transmition d'un formulaire vide ou incomplet*/
        get_startButton().setEnabled((get_patientNameET().toString().length() != 0 &&
                                      get_operatorNameET().toString().length() != 0&&
                                      get_markDistanceET().toString().length() != 0&&
                                      get_timeET().toString().length() != 0 ));

        if(!get_startButton().isEnabled()) {
            get_errorText().setText("You haven't fild everyting"); //Message d'avertissement quand
                                                                    //tous les champs du formulaire ne sont pas remplis
        }
    }
    //Obligation de les re-declarer.
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void afterTextChanged(Editable editable) {

    }




    //Méthode
}
=======

}
>>>>>>> interfaceMAJ
