package com.projet15.lazer;

/**
 *
 * @author dalmau
 */
public class  YuvToRgb {
    private byte[] yuv420sp;
    private int largeur, hauteur;

    public YuvToRgb(byte[] imageYuv, int large, int haut) {
        yuv420sp = imageYuv;
        largeur = large;
        hauteur = haut;
    }

    // Conversion d'une image au format YUV vers une image au format RGB
    // java n'offre aucune classe pour faire ca !
    // Cet algorithme a ete recupere sur le blog Obg1's blog
    // URL : http://obg1.wordpress.com/2010/06/26/decodeyuv420sp-using-jni-nice-performance-boost/
    public int[] decode() {
    	int tailleImage = largeur * hauteur;
        int[] rgb = new int[largeur * hauteur];

    	for (int j = 0, yp = 0; j < hauteur; j++) {
            int uvp = tailleImage + (j >> 1) * largeur, u = 0, v = 0;
            for (int i = 0; i < largeur; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }
                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);
                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;
                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
            }
    	}
        return rgb;
    }

}
