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
import android.widget.TextSwitcher;
import android.widget.TextView;

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
                

            }
        });
    }

    //getteur & setteur
    public void set_patientNameET(EditText t){
        _patientNameET = t;
    }
    public EditText get_patientNameET(){
        return _patientNameET;
    }
    public void set_operatorNameET(EditText t){
        _operatorNameET = t;
    }
    public EditText get_operatorNameET(){
        return _operatorNameET;
    }
    public void set_markDistanceET(EditText t){
        _markDistanceET = t;
    }
    public EditText get_markDistanceET(){
        return _markDistanceET;
    }
    public void set_timeET(EditText t){
        _timeET = t;
    }
    public EditText get_timeET(){
        return _timeET;
    }
    public void set_startButton(Button b){
        _startButton = b;
    }
    public Button get_startButton(){
        return _startButton;
    }

    public TextView get_errorText() {
        return _errorText;
    }
    public void set_errorText(TextView _errorText) {
        this._errorText = _errorText;
    }

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