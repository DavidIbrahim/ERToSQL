package com.example.david.ertosql;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    private static final String TAG="Mytag";
    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "opencv not loaded");
        }else{
            Log.d(TAG, "opencv loaded");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.image_view);
        Mat img=null;

        try {
            img = Utils.loadResource(this,R.raw.pic1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        img=ImageProcessing.exampleOnUsingOpenCV(img);
        Bitmap bm = Bitmap.createBitmap(img.cols(), img.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, bm);

        imageView.setImageBitmap(bm);




    }

}
