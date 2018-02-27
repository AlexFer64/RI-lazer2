package com.projet15.lazer;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class StaticExerciceParameter extends AppCompatActivity {

    //Variables
        //Objet graphique
    private EditText _patientNameET;
    private EditText _operatorNameET;
    private EditText _markDistanceET;
    private EditText _timeET;
    private Button _startButton;
    private TextView _errorText;
        //valeurs parametre
    private String _patientName;
    private String _operatorName;
    private  int _markDistance;
    private  int _time;
    //Constructeur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        setTitle(getResources().getText(R.string.static_exercice));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_exercice_parameter);

        //association des objet graphique avec les composant graphique
        set_patientNameET((EditText) findViewById(R.id.PATIENT_NAME_EDITTEXT_ID));
        set_operatorNameET((EditText) findViewById(R.id.OPERATOR_NAME_EDITTEXT_ID));
        set_markDistanceET((EditText) findViewById(R.id.MARKDISTANCE_NAME_EDITTEXT_ID));
        set_timeET((EditText) findViewById(R.id.TIME_EDITTEXT_ID));
        set_startButton((Button) findViewById(R.id.START_BUTTON_ID));
        set_errorText((TextView) findViewById(R.id.ERROR_TEXT_ID));
        //Evenement clique sur le bouton start
        get_startButton().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(get_patientNameET().getText().toString().length() != 0 ||
                        get_operatorNameET().getText().toString().length() != 0 ||
                        get_markDistanceET().getText().toString().length() != 0 ||
                        get_timeET().getText().toString().length() != 0){

                }
                else {

                    get_errorText().setText("You haven't fill everything out");

                    //RECCURCIVITE
                }
            }
        });
        */
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

    //Généré automatiquement
    public String get_patientName() {
        return _patientName;
    }
    public void set_patientName(String _patientName) {
        this._patientName = _patientName;
    }
    public String get_operatorName() {
        return _operatorName;
    }
    public void set_operatorName(String _operatorName) {
        this._operatorName = _operatorName;
    }
    public int get_markDistance() {
        return _markDistance;
    }
    public void set_markDistance(int _markDistance) {
        this._markDistance = _markDistance;
    }
    public int get_time() {
        return _time;
    }
    public void set_time(int _time) {
        this._time = _time;
    }
    public TextView get_errorText() {
        return _errorText;
    }
    public void set_errorText(TextView _errorText) {
        this._errorText = _errorText;
    }
    //Méthode
}