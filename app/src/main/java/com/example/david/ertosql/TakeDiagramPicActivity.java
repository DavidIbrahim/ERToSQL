package com.example.david.ertosql;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.david.ertosql.cameraAndImages.OpenCameraView;
import com.example.david.ertosql.cameraAndImages.utils.Constants;
import com.example.david.ertosql.cameraAndImages.utils.FolderUtil;
import com.example.david.ertosql.cameraAndImages.utils.ImagePreprocessor;
import com.example.david.ertosql.cameraAndImages.utils.Utilities;
import com.example.david.ertosql.data.DbBitMapUtility;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;

import static com.example.david.ertosql.ImageProcessing.convertToBitmap;
import static com.example.david.ertosql.data.ERDiagramContract.*;


public class TakeDiagramPicActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = TakeDiagramPicActivity.class.getSimpleName();

    private OpenCameraView cameraBridgeViewBase;

    private Mat colorRgba;
    private Mat colorGray;

    private Mat des, forward;

    private ImagePreprocessor preprocessor;

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    cameraBridgeViewBase.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_open_cvcamera);

        preprocessor = new ImagePreprocessor();
        cameraBridgeViewBase = (OpenCameraView) findViewById(R.id.camera_view);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        cameraBridgeViewBase.disableFpsMeter();


        ImageView takePictureBtn = (ImageView) findViewById(R.id.take_picture);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertPic();


            }
        });
    }

    private void insertPic()   {
        // Create database helper

        // Gets the database in write mode
        ContentValues values = new ContentValues();
        try {
            values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE, DbBitMapUtility.getBytes(convertToBitmap(colorRgba)));
            values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_NAME,"Untitled");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insert a new row for diagram in the database, returning the ID of that new row.
        Uri newUri = getContentResolver().insert(ERDiagramEntry.CONTENT_URI, values);
        openEditorActivity(newUri);
    }

    private void openEditorActivity(Uri uri) {
        Intent intent = new Intent(TakeDiagramPicActivity.this, EditorActivity.class);
        intent.setData(uri);

        // Launch the {@link EditorActivity} to display the data for the current pet.
        startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null)
            cameraBridgeViewBase.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, baseLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null)
            cameraBridgeViewBase.disableView();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        colorRgba = new Mat(height, width, CvType.CV_8UC4);
        colorGray = new Mat(height, width, CvType.CV_8UC1);

        des = new Mat(height, width, CvType.CV_8UC4);
        forward = new Mat(height, width, CvType.CV_8UC4);
    }


    @Override
    public void onCameraViewStopped() {
        Log.d(TAG, "onCameraViewStopped");

        colorRgba.release();
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        colorRgba = inputFrame.rgba();
        //todo sara
        preprocessor.changeImagePreviewOrientation(colorRgba, des, forward);
        return colorRgba;
    }

}
