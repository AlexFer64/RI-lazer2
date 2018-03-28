package com.projet15.lazer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import 	android.hardware.Camera;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;




import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.rgb;


//TODO:dessiner trois carré pour y placé les marqueur adaptatif aux ecrans
//TODO:récupérer les coordonnées du laser en fonction du temps et les enregistrer dans un variables "list<>"par exemple ou un tableau
//TODO:tracker le laser sur l'écran
//TODO:sauvegarder les coordonnées dans un fichier csv
//TODO:Gerer les Threads
//TODO:Gérer les different scénario d'erreurs (voir maquettes et scénario)

public class CameraActivity extends AppCompatActivity {
    private Intent _intent;
    private Parameter _parametreDeLexercice;
    private TextView _callBack;
    private Camera _camera;

    private CameraPreviewPixel _preview;

    private FrameLayout displayColor;

    private Rectangle _rectangle;
    private Timer myTimer;

    private FrameLayout _displayColor;
    private ImageView _imageView ;

    private ArrayList<CoordonneesEnFonctionDuTemps> donneesAenregistrer = new ArrayList<CoordonneesEnFonctionDuTemps>();
<<<<<<< HEAD
    private CSVFile fichierauvegarde = new CSVFile("/test.csv",",");
=======

    private Context context;
    private File directory = new File("");

>>>>>>> master

    //Cycle de Vie de l'application
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        MyTimerTask myTask = new MyTimerTask();
        myTimer = new Timer();


        //Recupération des données du formulaire
        _intent = getIntent();
        _parametreDeLexercice = (Parameter) _intent.getSerializableExtra("parameters");

        //Récupération du bouton et du texte
        _callBack = (TextView) findViewById(R.id.CALLBACK_TEXT_VIEW_ID);
        _displayColor = (FrameLayout) findViewById(R.id.FRAME_LAYOUT_COLOR_ID);
        _imageView = (ImageView) findViewById(R.id.iv);


        //Création d'une instance de la caméra
        _camera = getCameraInstance();

        //Paramétrage de l'autofocus
        Camera.Parameters params = _camera.getParameters(); //paramètres de la caméra

        _rectangle=new Rectangle(params, _imageView);
        _rectangle.dessinRectangle();

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            //Le mode autofocus est supporté
            Camera.Parameters ParametreCamera = _camera.getParameters(); //récuperer les paramètres de la caméra
            ParametreCamera.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO); //rajouter le mode autofocus

        }

        if(_parametreDeLexercice.get_bipIntervale() == -1)
        {
            myTimer.schedule(myTask, _parametreDeLexercice.get_time()*1000);
        }
        else
        {
            myTimer.schedule(myTask, _parametreDeLexercice.get_time()*1000,(int) _parametreDeLexercice.get_bipIntervale()*1000);
        }

        //Création de la prévisualisation et paramétrage de cette application comme contenu de du framelayout
        _preview = new CameraPreviewPixel(this, _camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.CAMERA_FRAME_LAYOUT_ID);
        preview.addView(_preview);


        /*****************************************************************
         *                                                               *
         *                  Test Sauvegarde Fichier csv                  *
         *                                                               *
         *****************************************************************/

        File fichierASauvegarder = new File(directory, "test.csv");
        Log.e("directory",fichierASauvegarder.getName());
        boolean estCree = fichierASauvegarder.exists();
        if(!estCree) {
            try {

                // To open you can choose the mode MODE_PRIVATE, MODE_APPEND,
                // MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE
                // This is the creation mode (Private, World Readable et World Writable),
                // Append is used to open the file and write at its end
               FileOutputStream test = new FileOutputStream(fichierASauvegarder);
               test.flush();
               FileOutputStream fos = openFileOutput(fichierASauvegarder.getAbsolutePath(), Context.MODE_PRIVATE);
                // Open the writer
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                // Write
                outputStreamWriter.write("test");
                // Close streams
                outputStreamWriter.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    class MyTimerTask extends TimerTask {
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    Log.e("TonGen","Biiip");
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
                }
            });
        }
    }


    @Override
    public void onPause() {
        Log.e("onpause","L'activité est en pause");
        super.onPause();

        myTimer.cancel();
        myTimer.purge();
        finish();
    }

    //Gestion Camera
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); //  essaie ouverture de la camera
        }
        catch (Exception e){
            // Camera non disponible
        }
        return c; // returns null if camera is unavailable
    }
    private void releaseCamera() {
        _preview.mCamera = null;
        if (_camera != null) {
            _camera.stopPreview();
            _camera.release();        // relacher la camera pour que d'autre app puisse y acceder
            _camera = null;

        }
    }

    //Dessin figure sur preview
    public void dessinRectangle(Camera.Parameters params) {
        Bitmap bitmap = Bitmap.createBitmap(
                params.getPreviewSize().width, // Width
                params.getPreviewSize().height, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.TRANSPARENT);

        // Initialize a new Paint instance to draw the Rectangle
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(rgb( 255, 0, 0));

        // Set a pixels value to padding around the rectangle
        int ecart = 50;

        // Initialize a new Rect object
        Rect rectangle = new Rect(
                canvas.getWidth()/2 - ecart, // Left
                canvas.getHeight()/2 - ecart, // Top
                canvas.getWidth()/2 + ecart,// Right
                canvas.getHeight()/2 + ecart // Bottom
        );

        // Finally, draw the rectangle on the canvas
        canvas.drawRect(rectangle,paint);

        // Display the newly created bitmap on app interface
        _imageView.setImageBitmap(bitmap);

    }


    /**     ----------------------------------------------------------------------------
     *      |                                                                          |
     *      |               TEST CAMERA PREVIEW AMELIOREE                              |
     *      |                                                                          |
     *      ----------------------------------------------------------------------------*/


    public class CameraPreviewPixel extends CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback {
     private SurfaceHolder mHolder;
        private Camera mCamera;

        //Settings de la caméra
        private Camera.Parameters parameters;
        //taille de la "preview" de la caméra
        private android.hardware.Camera.Size previewSize;
        //tableau pour stocker les pixels en paires hexadécimales
        private int[] pixels;

        public CameraPreviewPixel(Context context, Camera camera) {
            super(context,camera);

        }

        public void onPreviewFrame(byte[] data, Camera camera) {
            parameters = camera.getParameters();
            _callBack.setText(String.valueOf((int) data[6]));

            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;
            YuvToRgb test = new YuvToRgb(data,width,height);
            int [] tab;

            tab = test.decode() ;


            int r = (tab[(720*240)+360]&0x00FF0000)>>16;
            int g = (tab[(720*240)+360]&0x0000FF00)>>8;
            int b = (tab[(720*240)+360]&0x000000FF);

            _displayColor.setBackgroundColor(rgb(r,g,b));

        }

        @Override
        public void  surfaceDestroyed(SurfaceHolder holder){

        }
    }






}
