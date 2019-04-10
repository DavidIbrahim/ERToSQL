package com.example.david.ertosql;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;

public class ImageProcessing {

    ImageView img_view;

    /**
     *
     */
    public static Mat exampleOnUsingOpenCV(Mat img) {
       // Mat img = null;

        // in general el resources leha 3laka be el activities bs ....7aga b3rdha le el user ... tmam ?
        //wa 3lshan kda lazm ast5dmha gwa activity .... afrd 3aez ast5dmha bra ?? eshta lazm tdelha ae activity wa 5las
        // a3mle load le el sora 3n tre2 el path
        //Loading the OpenCV core library
     /*   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Instantiating the Imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();
        //Reading the Image from the file
        String file = "C:\\Users\\saras\\AndroidStudioProjects\\ERToSQL\\app\\src\\main\\res\\raw\\pic1.jpg";
        img = imageCodecs.imread(file);
*/      adaptiveThreshold(img, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 10);
       // Imgproc.threshold(img, img, 100, 255, Imgproc.THRESH_BINARY);

         return img;
       // Bitmap bm = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
     //   Utils.matToBitmap(img, bm);

    }


    public static void main(String[] args) {

    }
}
