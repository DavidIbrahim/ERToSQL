package com.example.david.ertosql;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.david.ertosql.cameraAndImages.OpenCameraView;
import com.example.david.ertosql.cameraAndImages.utils.ImagePreprocessor;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class TakeDiagramPicActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = TakeDiagramPicActivity.class.getSimpleName();

    private OpenCameraView cameraBridgeViewBase;

    private static Mat colorRgba;

    private  ImageView imageViewPreview;

    private Mat des, forward;

    private ImagePreprocessor preprocessor;
    private ImageView imageViewSave;

    private ImageView imageViewReOpenCamera;

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
        setTitle("Capture Diagram");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_open_cvcamera);

        preprocessor = new ImagePreprocessor();
        cameraBridgeViewBase = (OpenCameraView) findViewById(R.id.camera_view);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        cameraBridgeViewBase.disableFpsMeter();

        imageViewReOpenCamera = findViewById(R.id.retake_image_view);
        imageViewSave = findViewById(R.id.right_imageView);
        imageViewPreview = findViewById(R.id.imagePreview);

        imageViewSave.setVisibility(View.INVISIBLE);
        imageViewReOpenCamera.setVisibility(View.INVISIBLE);
        imageViewPreview.setVisibility(View.INVISIBLE);
        cameraBridgeViewBase.setContext(this,imageViewReOpenCamera,imageViewSave);

        final ImageView takePictureBtn = (ImageView) findViewById(R.id.take_picture);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePictureBtn.setVisibility(View.INVISIBLE);
                cameraBridgeViewBase.takePicture(imageViewPreview);


                //insertNewDiagram();


            }
        });
        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraBridgeViewBase.saveImageAndOpenEditorActivity();
                imageViewSave.setVisibility(View.INVISIBLE);
                imageViewReOpenCamera.setVisibility(View.INVISIBLE);
                imageViewPreview.setVisibility(View.INVISIBLE);

                takePictureBtn.setVisibility(View.VISIBLE);

            }
        });

        imageViewReOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureBtn.setVisibility(View.VISIBLE);

                imageViewSave.setVisibility(View.INVISIBLE);
                imageViewPreview.setVisibility(View.INVISIBLE);

                imageViewReOpenCamera.setVisibility(View.INVISIBLE);
                cameraBridgeViewBase.resume();
            }
        });
    }


 /*   private void insertNewDiagram()   {
        // Create database helper


        // Gets the database in write mode
         String sqlCode = "  SELECT column1, column2 FROM table1, table2 WHERE column2='value';\n"+
                 "SELECT * FROM Customers WHERE Last_Name=\'Smith';";
      //String sqlCode = ImageProcessing.getSQLcode(colorRgba,this);
        ContentValues values = new ContentValues();
        try {
            values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE, DbBitMapUtility.getBytes(convertToBitmap(colorRgba)));
            values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_NAME,"Untitled");
            values.put(ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE,sqlCode);
        } catch (IOException e) {
            e.printStackT
            race();
        }

        // Insert a new row for diagram in the database, returning the ID of that new row.
        Uri newUri = getContentResolver().insert(ERDiagramEntry.CONTENT_URI, values);
        openEditorActivity(newUri);
    }
*/

    public static Mat getColorRgba() {
        return colorRgba;
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

        des = new Mat(height, width, CvType.CV_8UC4);
        forward = new Mat(height, width, CvType.CV_8UC4);
      cameraBridgeViewBase.resume();
    }


    @Override
    public void onCameraViewStopped() {
        Log.d(TAG, "onCameraViewStopped");

        //colorRgba.release();
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        colorRgba = inputFrame.rgba();
        System.out.println("height is : "+colorRgba.height()+ "- width is: "+colorRgba.width());
        //todo sara
        preprocessor.changeImagePreviewOrientation(colorRgba, des, forward);
         //getStringFromImage(colorRgba ,this);
       //highlightShapes(colorRgba);2
        return colorRgba;
    }

}
