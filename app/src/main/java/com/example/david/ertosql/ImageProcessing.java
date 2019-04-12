package com.example.david.ertosql;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.example.david.ertosql.er.shapes.ERLine;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;

public class ImageProcessing extends Activity {


    // this pic is the one used in testing
    private final static  int  TEST_PIC_ID = R.raw.pic1;

    private static Mat matPicTest ;


    public static Bitmap exampleOnUsingOpenCV(Context context) {
        Mat mat=loadTestPic(context );



        //apply thresholding on image
        adaptiveThreshold(mat, mat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 10);


        return convertToBitmap(mat);

    }


    private static ArrayList<ERLine> getLines(Mat mat){
        //todo kero   implement the method
        ArrayList<ERLine> erLines = null;


        return erLines;
    }
    private String getStringFromImage(Mat mat){
        StringBuilder sb = new StringBuilder();
        Bitmap bitmap = convertToBitmap(mat);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational()){
            return "couldn't get Text!";
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> text = textRecognizer.detect(frame);
            for (int i = 0; i < text.size(); i++){
                TextBlock item = text.valueAt(i);
                sb.append(item.getValue());
            }
        }

        return sb.toString();
    }





    private static Mat loadTestPic(Context context) {
        Mat mat = null;
        try {
            // load the image in grey scale and save it in mat ..... change pic1 for different resource
            mat = Utils.loadResource(context,TEST_PIC_ID ,  Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mat;
    }

    private static Bitmap convertToBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }


}
