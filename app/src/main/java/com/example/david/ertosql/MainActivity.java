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
    /**
     *  True for testing ImageProcessing class only
     */
    private static final boolean  TESTING = true;
    private static final String TAG= MainActivity.class.getSimpleName();

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
        if(TESTING) {
            setContentView(R.layout.activity_main_test);
            ImageView imageView = (ImageView) findViewById(R.id.image_view);

            // image returned that needs to be shown ...
            Bitmap bitmap = ImageProcessing.exampleOnUsingOpenCV(this);

            imageView.setImageBitmap(bitmap);
        }
        else {
            setContentView(R.layout.activity_main);
        }



    }

}
