package com.projet15.lazer;

import java.io.Serializable;


/*
* Sauvegarde les données du formulaire
* */
public class Parameter implements Serializable{
    //Attributs
    private int _typeExercice; //a discuter
    private String _patientName;
    private String _operatorName;
    private float _markDistance;
    private  int _time;
    private float _bipIntervale; //peux etre renomée
                                //Comment représenté le deuxieme exo


    //Constructeur

    public Parameter(int _typeExercice, String _patientName, String _operatorName, float _markDistance, int _time, float _bipIntervale) {
        this._typeExercice = _typeExercice;
        this._patientName = _patientName;
        this._operatorName = _operatorName;
        this._markDistance = _markDistance;
        this._time = _time;
        this._bipIntervale = _bipIntervale;
    }

    public Parameter(int _typeExercice, String _patientName, String _operatorName, float _markDistance, int _time) {
        this._typeExercice = _typeExercice;
        this._patientName = _patientName;
        this._operatorName = _operatorName;
        this._markDistance = _markDistance;
        this._time = _time;
        this._bipIntervale = -1; // A voir
    }
    public Parameter(Parameter p){
        set_patientName(p.get_patientName());
        set_operatorName(p.get_operatorName());
        set_markDistance(p.get_markDistance());
        set_time(p.get_time());
        set_bipIntervale(p.get_bipIntervale());
    }

    //Getteur & Setteur

    public int get_typeExercice() {
        return _typeExercice;
    }

    public void set_typeExercice(int _typeExercice) {
        this._typeExercice = _typeExercice;
    }

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

    public float get_markDistance() {
        return _markDistance;
    }

    public void set_markDistance(float _markDistance) {
        this._markDistance = _markDistance;
    }

    public int get_time() {
        return _time;
    }

    public void set_time(int _time) {
        this._time = _time;
    }

    public float get_bipIntervale() {
        return _bipIntervale;
    }

    public void set_bipIntervale(float _bipIntervale) {
        this._bipIntervale = _bipIntervale;
    }
}
