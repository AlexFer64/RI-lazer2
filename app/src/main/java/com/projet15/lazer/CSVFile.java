package com.projet15.lazer;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class CSVFile extends File  {
    private File directory = new File("/test");
    private String _separator;
    private Context _ctx;
    public CSVFile(@NonNull String pathname,String separator) {
        super(pathname);
        _separator = separator;
    }

    public boolean makedirectory(){
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void save(ArrayList<CoordonneesEnFonctionDuTemps> data) throws FileNotFoundException {


        for (int i = 0; i < data.size(); i++) {
            CoordonneesEnFonctionDuTemps courrant = data.get(i);
            String line = courrant.get_seconde() + _separator + courrant.get_x() + _separator + courrant.get_y() + "\n";
            //Ecrire Line
        }


    }


}
