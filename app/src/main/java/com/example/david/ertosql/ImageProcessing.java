package com.example.david.ertosql;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.SparseArray;
import android.widget.ImageView;

import com.example.david.ertosql.er.shapes.ERLine;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;

public class ImageProcessing {



    /**
     * this variable holds all test pictures
     * u can add new test pictures in raw folder and add their ids in this array so you can test them
     */
    private static int[] mTestPictures = {R.raw.pic1, R.raw.pic2};

    /**
     * time of displaying the image of a single test
     */
    private final static int DURATION_OF_SINGLE_TEST_IN_MILLIS = 2000;

    /*
     *
     */
    public static void testImageProcessing(final Context context, final ImageView imageView) {

        new CountDownTimer(DURATION_OF_SINGLE_TEST_IN_MILLIS*(mTestPictures.length+1), DURATION_OF_SINGLE_TEST_IN_MILLIS) {
            int i = 0;

            public void onTick(long millisUntilFinished) {
                int mTestPicture = mTestPictures[i];
                Mat originalImage = loadTestPic(context, mTestPicture);
                System.out.println(i);
                //todo change exampleOnUsingOpenCV wz ur own method to test it
                Mat convertedImageMat = exampleOnUsingOpenCV(originalImage);

                imageView.setImageBitmap(convertToBitmap(convertedImageMat));
                i++;
            }

            public void onFinish() {
            }
        }.start();


    }

    private static Mat exampleOnUsingOpenCV(Mat mat) {
        //apply thresholding on image
        adaptiveThreshold(mat, mat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 10);

        return mat;

    }


    private static ArrayList<ERLine> getLines(Mat mat) {
        //todo kero   implement the method
        ArrayList<ERLine> erLines = null;


        return erLines;
    }

    private String getStringFromImage(Mat mat, Context c) {
        StringBuilder sb = new StringBuilder();
        Bitmap bitmap = convertToBitmap(mat);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(c).build();
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

    private static Mat loadTestPic(Context context, int testPicID) {
        Mat mat = null;
        try {
            // load the image in grey scale and save it in mat ..... change pic1 for different resource
            mat = Utils.loadResource(context, testPicID, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mat;
    }

    private static Bitmap convertToBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }


}
