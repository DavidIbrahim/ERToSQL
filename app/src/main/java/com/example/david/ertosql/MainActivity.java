package com.example.david.ertosql;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;

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
            final ImageView imageView = findViewById(R.id.test_image_view);
            Button buttonStartTesting = findViewById(R.id.button_start_testing);
            buttonStartTesting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageProcessing.testImageProcessing(view.getContext(),imageView);
                }
            });


        }
        else {
            setContentView(R.layout.activity_main);
        }



    }


}
