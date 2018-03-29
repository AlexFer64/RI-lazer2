package com.projet15.lazer;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;


public class CSVFile {

    //Path: /sdcard/LaZer
    static public boolean  sauvegarde(ArrayList<CoordonneesEnFonctionDuTemps> data,Parameter _parametreDeLexercice){
        //TODO:verifier assez de place
        if(!isExternalStorageWritable()){
            Environment.getExternalStoragePublicDirectory("").setWritable(true);
        }

        //Reccup de la date au format YYYY_MM_DD_HH_MM
        Calendar rightNow = Calendar.getInstance();
        String date = rightNow.get(Calendar.YEAR) + "_"
                +(rightNow.get(Calendar.MONTH)+1)+"_"
                +rightNow.get(Calendar.DAY_OF_MONTH)+"_"
                +rightNow.get(Calendar.HOUR)+"_"+
                rightNow.get(Calendar.MINUTE);

        //nom du fichier suvegarder au format csv
        String savefilename = _parametreDeLexercice.get_patientName().replaceAll("\\s","")
                +_parametreDeLexercice.get_operatorName().replaceAll("\\s","")
                +_parametreDeLexercice.get_typeExercice()
                +date+".csv";
        String dirfilename = "LaZer";

        //reccupération du dossier de sauvegarde
        File dir = getPublicStorageDir(dirfilename);

        //file represente le fichier de sauvegarde
        File file = new File(dir.getAbsolutePath()+File.separator+savefilename);

        //Recapitulation des parametre de l'exercice réalisé (',' juste pour la mise en forme
        String recapExercice = "Recap of the exercice \n"
                +"Exercice = " + _parametreDeLexercice.get_typeExercice()+"\n"
                +"#Patient Name = "+_parametreDeLexercice.get_patientName()+"\n"
                +"#Operator Name = "+_parametreDeLexercice.get_operatorName()+"\n"
                +"#Mark Distance= "+_parametreDeLexercice.get_markDistance()+"\n"
                +"#Time(s) = "+_parametreDeLexercice.get_time()+"\n";
        recapExercice += (_parametreDeLexercice.get_bipIntervale()>0)? "#Bip Interval = ,"
                +_parametreDeLexercice.get_bipIntervale()+"\n\n":"\n";

        //En-tête du tableau
        String entete = "time(s),x,y\n"; //Entete du fichier

        FileOutputStream outputStream;
        try {
            if(!file.exists()) { //retourne si le fichier existe
                if (!file.createNewFile()) { //cree le fichier si il n'existe pas
                    Log.e("ErrFile", "File was not Created");
                }
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(recapExercice.getBytes()); //ecris le recap de l'exo
            outputStream.write(entete.getBytes()); // affiche l'entete du tableau

            //enregistrement de la liste dans le fichier
            for (int i = 0; i < data.size(); i++) {
                CoordonneesEnFonctionDuTemps courrant = data.get(i);//Recuperation de l'élément i de la liste
                String line = courrant.get_seconde() + "," + courrant.get_x() + "," + courrant.get_y() + "\n";
                //Ecrire Line
                outputStream.write(line.getBytes());//ecris la ligne courrante
            }
            outputStream.close(); //fermture du fichier
            return true;
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public static File getPublicStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(""),fileName);
        if(!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("ErrDir", "Directory not created");
            }
        }
        return file;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
