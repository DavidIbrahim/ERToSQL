package com.example.david.ertosql;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.david.ertosql.er.shapes.ERLine;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERShape;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.MORPH_OPEN;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;

public class ImageProcessing {

    private final static String IMAGE_PROCESSING_TAG = ImageProcessing.class.getName();
    /**
     * this variable holds all test pictures
     * u can add new test pictures in raw folder and add their ids in this array so you can test them
     */
    private static int[] mTestPictures = {R.raw.pic1, R.raw.pic2};

    /**
     * time of displaying the image of a single test
     */
    private final static int DURATION_OF_SINGLE_TEST_IN_MILLIS = 4000;

    /*
     *
     */
    private final static boolean TEST_A_METHOD_RETURNS_IMAGE = false;


    /**
     *  a function that loads test images and test them automatically
     * @param context
     * @param imageView
     */
    public static void testImageProcessing(final Context context, final ImageView imageView) {
            new CountDownTimer(DURATION_OF_SINGLE_TEST_IN_MILLIS * (mTestPictures.length + 1), DURATION_OF_SINGLE_TEST_IN_MILLIS) {
                int i = 0;

                public void onTick(long millisUntilFinished) {
                    int mTestPicture = mTestPictures[i];
                    Mat originalImage = loadTestPic(context, mTestPicture);
                    System.out.println(i);
                    if(TEST_A_METHOD_RETURNS_IMAGE) {
                        //todo change exampleOnUsingOpenCV wz ur own method if it returns a pic to test it

                        Mat convertedImageMat = exampleOnUsingOpenCV(originalImage);
                        imageView.setImageBitmap(convertToBitmap(convertedImageMat));
                    }
                    else {
                        //todo test ur own code here if it doesn't return an image
                        //this is an example
                        ArrayList<ERLine> lines = getLines(originalImage);
                        Log.d(IMAGE_PROCESSING_TAG,"Testing with Image : "+i+ '\n'+lines.toString());
                    }
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

    /**
     *
     * @param mat
     * @return array list contain ERReactangle
     */
    private static ArrayList<ERRectangle> getRectangles(Mat mat){
        //copy image
        Mat img=mat.clone();
        ArrayList<ERRectangle> erRectangles=new ArrayList<>();

        //make threshold
         adaptiveThreshold(img, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 15, 8);

        //copy the image
        Mat orig=img.clone();

        //dilation to get the shapes
        Mat Kernel = new Mat(new Size(7, 7), CvType.CV_8U, new Scalar(255));
        Imgproc.dilate(img,img,Kernel);

        //contour all shapes in image and fill it with white color
        List<MatOfPoint> contours = new ArrayList<>();
        Scalar white = new Scalar(255, 255, 255);
        Imgproc.findContours(img, contours, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        Imgproc.fillPoly(img,contours,white);


        //make morph open using rectangle kernel
        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(50, 20));
        Imgproc.morphologyEx(img, img, MORPH_OPEN, kernel);

        //contour after changes
        List<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(img, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        // Log.d("getttpoint", String.valueOf(contour.size()));
        Scalar black = new Scalar(0, 190, 0);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat cut=null;
        for (MatOfPoint cnt : contour) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurve, 0.01 * Imgproc.arcLength(curve, true), true);

            int numberVertices = (int) approxCurve.total();

            //Rectangle detected
            if (numberVertices == 4 ) {
                Rect rec= Imgproc.boundingRect(cnt);
                if(rec.x==0||rec.y==0)
                    continue;
                Point p=new Point(rec.x+(rec.width/2),rec.y+(rec.height/2));

                cut=orig.submat(rec);
                ERShape.ERPoint center=new ERShape.ERPoint(rec.x+(rec.width/2),rec.y+(rec.height/2));
               // Imgproc.putText(orig,"sara",p,2,2,green,2);
                //  String text=getStringFromImage(cut)
                String text="sara";
                ERRectangle e=new ERRectangle(center,text);
                erRectangles.add(e);
                //erRectangles.add(e);
                Log.d("getttpoint", String.valueOf(e.getCenter().x));

                Imgproc.fillPoly(img, Collections.singletonList(cnt),black);
            }

        }

        return erRectangles;
    }

    private static ArrayList<ERLine> getLines(Mat mat) {
        //todo kero   implement the method
        ArrayList<ERLine> erLines = new ArrayList<ERLine>();
        erLines.add(new ERLine(new ERShape.ERPoint(2,2),new ERShape.ERPoint(3,4)));


        return erLines;
    }

    private static String getStringFromImage(Mat mat) {
        //todo Rameez


        return "";
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
