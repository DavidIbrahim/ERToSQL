package com.example.david.ertosql.cameraAndImages;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.david.ertosql.EditorActivity;
import com.example.david.ertosql.ImageProcessing;
import com.example.david.ertosql.TakeDiagramPicActivity;
import com.example.david.ertosql.data.DbBitMapUtility;
import com.example.david.ertosql.data.ERDiagramContract;

import org.opencv.android.JavaCameraView;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.List;

import static com.example.david.ertosql.ImageProcessing.convertToBitmap;
import static com.example.david.ertosql.ImageProcessing.highlightShapes;


public class OpenCameraView extends JavaCameraView implements Camera.PictureCallback {

    private static final String TAG = OpenCameraView.class.getSimpleName();

    private String mPictureFileName;
    public Mat mMat;
    public static int minWidthQuality = 400;
    public Bitmap bm;
    private Context context;
    private ImageView mImageView;
    private ImageView imageViewReOpen;
    private ImageView imageViewSave;

    public void setContext(Context context, ImageView imageViewReOpenCamera, ImageView imageViewSave) {
        this.context = context;
        imageViewReOpen = imageViewReOpenCamera;
        this.imageViewSave = imageViewSave;

    }

    public OpenCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    public List<String> getEffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }


    public boolean isEffectSupported() {
        return (mCamera.getParameters().getColorEffect() != null);
    }


    public String getEffect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void setEffect(String effect) {
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(effect);
        mCamera.setParameters(params);
    }


    public List<Camera.Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }


    public void setResolution(Camera.Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }


    public Camera.Size getResolution() {
        return mCamera.getParameters().getPreviewSize();
    }


    public void takePicture(final ImageView imageView) {
        mImageView = imageView;
        mCamera.setPreviewCallback(null);
        mCamera.takePicture(null, null, this);
    }


    @SuppressLint("WrongThread")
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {


        mMat = TakeDiagramPicActivity.getColorRgba();
        Mat mat = new Mat();
        mMat.copyTo(mat);
        highlightShapes(mat);
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(mat,bitmap);
        mImageView.setImageBitmap(convertToBitmap(mat));
        mImageView.setVisibility(VISIBLE);
        imageViewReOpen.setVisibility(VISIBLE);
        imageViewSave.setVisibility(VISIBLE);

      /*  Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bm = rotate(bitmap, 90);
      */  //insertNewDiagram(bm);
        // Write the image in a file (in jpeg format)
     /*   try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            //galleryAddPic();
            Log.d(TAG,"savedPicture successfully");
        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }*/
    }

    public void saveImageAndOpenEditorActivity() {
        insertNewDiagram(bm);
    }

    private static Bitmap rotate(Bitmap bm, int rotation) {
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap bmOut = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            return bmOut;
        }
        return bm;
    }


    private void insertNewDiagram(Bitmap data) {
        // Create database helper


        // Gets the database in write mode
       /* String sqlCode = "  SELECT column1, column2 FROM table1, table2 WHERE column2='value';\n"+
                "SELECT * FROM Customers WHERE Last_Name=\'Smith';";*/
       String sqlCode=ImageProcessing.getSQLcode(mMat,context);


        /*Mat mat = new Mat();
        Utils.bitmapToMat(data,mat);*/
        long startTime = System.currentTimeMillis();
        Log.d(TAG,"begin highlighting : ");

        long endTime   = System.currentTimeMillis();
        Log.d(TAG,"time of highlighting : " + (endTime-startTime));
        //Utils.matToBitmap(mat,data);
        Bitmap bitmap = Bitmap.createBitmap(mMat.cols(), mMat.rows(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(mMat, bitmap);
      /*  byte[] array = null;
        try {
           array = DbBitMapUtility.getBytes(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }*/




        /* String sqlCode = ImageProcessing.getSQLcode(mMat, context);*/
        ContentValues values = new ContentValues();
        try {
            values.put(ERDiagramContract.ERDiagramEntry.COLUMN_ERDIAGRAM_ORIGINAL_IMAGE, DbBitMapUtility.getBytes(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put(ERDiagramContract.ERDiagramEntry.COLUMN_ERDIAGRAM_NAME, "Untitled");
        values.put(ERDiagramContract.ERDiagramEntry.COLUMN_ERDIAGRAM_SQL_CODE, sqlCode);

        // Insert a new row for diagram in the database, returning the ID of that new row.
        Uri newUri = context.getContentResolver().insert(ERDiagramContract.ERDiagramEntry.CONTENT_URI, values);
        if (newUri != null)
            openEditorActivity(newUri);
    }

    public void openEditorActivity(Uri uri) {
        Intent intent = new Intent(context, EditorActivity.class);
        intent.setData(uri);
        intent.putExtra("ParentActivity", TakeDiagramPicActivity.class.getSimpleName());
        // Launch the {@link EditorActivity} to display the data for the current pet.
        context.startActivity(intent);

    }

    public void resume() {
        // The camera preview was automatically stopped. Start it again.
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);
        Camera.Parameters params = mCamera.getParameters();
//*EDIT*//params.setFocusMode("continuous-picture");
//It is better to use defined constraints as opposed to String, thanks to AbdelHady
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        params.setPreviewFpsRange(30000, 30000);

        mCamera.setParameters(params);

    }
}
