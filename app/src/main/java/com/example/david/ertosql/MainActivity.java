package com.example.david.ertosql;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.david.ertosql.cameraAndImages.OpenCVCamera;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.opencv.android.OpenCVLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     *  True for testing ImageProcessing class only
     */
    private static final boolean  TESTING = false;
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
            Dexter.withActivity(this)
                    .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if(!report.areAllPermissionsGranted()){
                        Toast.makeText(MainActivity.this, "You need to grant all permission to use this app features", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }).check();
            FloatingActionButton startButton = findViewById(R.id.start_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cvIntent = new Intent(MainActivity.this, OpenCVCamera.class);
                    startActivity(cvIntent);
                }
            });

        }



    }


}
