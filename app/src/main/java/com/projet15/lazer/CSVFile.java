package com.projet15.lazer;

import android.support.annotation.NonNull;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



public class CSVFile extends File {
    private File directory = new File("/");
    private String _separator;

    public CSVFile(@NonNull String pathname,String separator) {
        super(pathname);
        _separator = separator;
    }

    private boolean makedirectory(){
        if(directory.exists()){
            return directory.mkdir();
        }
        return true;
    }
    private boolean newFile() throws IOException {
        if(this.exists()){
            return this.createNewFile();
        }
        return true;
    }
    public void save(ArrayList<CoordonneesEnFonctionDuTemps> data)  throws IOException {
        makedirectory(); //création du dossier si il n'existe pas
        newFile();//création du nouveau fichier

        this.setWritable(true); //autorise l'écriture sur le fichier
        FileOutputStream fileOutputStream = new FileOutputStream(this.getAbsolutePath()); //conexion pour ecrire dans le fichier

        fileOutputStream.write(Byte.parseByte("seconde"+_separator+"x"+_separator+"y\n")); //Ecris l'entete du fichier
        for (int i = 0; i < data.size(); i++) {
            CoordonneesEnFonctionDuTemps courrant = data.get(i);
            String line = courrant.get_seconde() + _separator + courrant.get_x() + _separator + courrant.get_y() + "\n";
            fileOutputStream.write(Byte.parseByte(line));
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        this.setWritable(false);


    }

}
