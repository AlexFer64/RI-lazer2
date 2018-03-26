package com.projet15.lazer;

public class CoordonneesEnFonctionDuTemps {
    private int _seconde;
    private  int _x;
    private  int _y;

    public CoordonneesEnFonctionDuTemps(int _seconde, int _x, int _y) {
        this._seconde = _seconde;
        this._x = _x;
        this._y = _y;
    }

    public int get_seconde() {
        return _seconde;
    }

    public void set_seconde(int _seconde) {
        this._seconde = _seconde;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }
}
