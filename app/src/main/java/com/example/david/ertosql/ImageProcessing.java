package com.example.david.ertosql;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.SparseArray;
import android.util.Log;
import android.widget.ImageView;

import com.example.david.ertosql.er.shapes.ERElipse;
import com.example.david.ertosql.er.shapes.ERLine;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERRhombus;
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

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.MORPH_CLOSE;
import static org.opencv.imgproc.Imgproc.MORPH_OPEN;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.putText;

public class ImageProcessing {

    private final static String IMAGE_PROCESSING_TAG = ImageProcessing.class.getSimpleName();
    /**
     * this variable holds all test pictures
     * u can add new test pictures in raw folder and add their ids in this array so you can test them
     */
    public static int[] mTestPictures = {R.raw.pic5};

    /**
     * time of displaying the image of a single test
     */
    private final static int DURATION_OF_SINGLE_TEST_IN_MILLIS = 4000;

    /*
     *
     */
    private final static boolean TEST_A_METHOD_RETURNS_IMAGE = false;


    /**
     * a function that loads test images and test them automatically
     *
     * @param context
     * @param imageView
     */
    public static void testImageProcessing(final Context context, final ImageView imageView) {
        new CountDownTimer(DURATION_OF_SINGLE_TEST_IN_MILLIS * (mTestPictures.length + 1), DURATION_OF_SINGLE_TEST_IN_MILLIS) {
            int i = 0;

            public void onTick(long millisUntilFinished) {
                int mTestPicture = mTestPictures[i];
                Mat originalImage = loadTestPic(context, mTestPicture);
                if (TEST_A_METHOD_RETURNS_IMAGE) {
                    //todo change exampleOnUsingOpenCV wz ur own method if it returns a pic to test it

                    Mat convertedImageMat = exampleOnUsingOpenCV(originalImage);
                    imageView.setImageBitmap(convertToBitmap(convertedImageMat));
                } else {
                    //todo test ur own code here if it doesn't return an image
                    //this is an example
                    Mat to_Rhombus=new Mat();
                    Mat copy_original=originalImage.clone();
                    Mat tocamera=originalImage.clone();
                    Imgproc.cvtColor(tocamera,tocamera,Imgproc.COLOR_GRAY2RGB);
                    ArrayList<ERRectangle> lines = getRectangles(originalImage,tocamera);
                    ArrayList<ERElipse> elipses=getEllipse(copy_original,originalImage,to_Rhombus,tocamera);
                    ArrayList<ERRhombus> erRhombuses=getRhombus(copy_original,originalImage,to_Rhombus,tocamera);
                    Log.d(IMAGE_PROCESSING_TAG, "Testing with Image : " + i + '\n' + lines.toString());
                    imageView.setImageBitmap(convertToBitmap(tocamera));
                }
                i++;
            }



            public void onFinish() {
            }
        }.start();


    }

    public static void  hilightShapes(Mat originalImage)
    {
        Mat originalImageGrayScale=originalImage.clone();
        Imgproc.cvtColor(originalImageGrayScale,originalImageGrayScale,Imgproc.COLOR_RGB2GRAY);
        Mat to_Rhombus=new Mat();
        Mat copy_original=originalImageGrayScale.clone();
        Mat tocamera=originalImage;
      // Imgproc.cvtColor(tocamera,tocamera,Imgproc.COLOR_GRAY2RGB);
        ArrayList<ERRectangle> lines = getRectangles(originalImageGrayScale,tocamera);
        ArrayList<ERElipse> elipses=getEllipse(copy_original,originalImageGrayScale,to_Rhombus,tocamera);
        ArrayList<ERRhombus> erRhombuses=getRhombus(copy_original,originalImageGrayScale,to_Rhombus,tocamera);

    }

    private static Mat exampleOnUsingOpenCV(Mat mat) {
        //apply thresholding on image
        adaptiveThreshold(mat, mat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 10);

        return mat;

    }

    /**
     * @param mat
     * @return array list contain ERReactangle of the image
     */

    public static ArrayList<ERRectangle> getRectangles(Mat mat,Mat tocamera) {
        //copy image

       // Mat img = mat.clone();
       Mat img = mat;
        ArrayList<ERRectangle> erRectangles = new ArrayList<>();

        //make threshold
           Imgproc.GaussianBlur(img,img,new Size(5,5),0);
        adaptiveThreshold(img, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 41, 5);



        //copy the image
        Mat orig = img.clone();

        //dilation to get the shapes
        Mat Kernel = new Mat(new Size(13, 13), CvType.CV_8U, new Scalar(255));
        Imgproc.dilate(img, img, Kernel);

        //contour all shapes in image and fill it with white color
        List<MatOfPoint> contours = new ArrayList<>();
        Scalar white= new Scalar(255, 255, 255);
        Imgproc.findContours(img, contours, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        Imgproc.fillPoly(img, contours, white);
       // Imgproc.erode(img, img, Kernel);

        //make morph open using rectangle kernel
        Mat img1=new Mat();
        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(70, 50));
        Imgproc.morphologyEx(img, img1, MORPH_OPEN, kernel);

        //contour after changes
        List<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(img1, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        // Log.d("getttpoint", String.valueOf(contour.size()));
        Scalar black = new Scalar(0, 190, 0);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat cut = null;
        int count =0;

       ArrayList<MatOfPoint> c=new ArrayList<>();
        for (MatOfPoint cnt : contour) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurve, 0.015 * Imgproc.arcLength(curve, true), true);

            int numberVertices = (int) approxCurve.total();

            //Rectangle detected
            if (numberVertices==4&&Imgproc.contourArea(cnt)>1000) {
                Rect rec = Imgproc.boundingRect(cnt);

                Point p = new Point(rec.x + (rec.width / 2), rec.y + (rec.height / 2));

                cut = orig.submat(rec);
                ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));

                //  String text=getStringFromImage(cut)
                String text = "sara";
                ERRectangle e = new ERRectangle(center, text);
                erRectangles.add(e);
                c.add(cnt);


                //
                Imgproc.fillPoly(img, Collections.singletonList(cnt), black);
                count++;
               // Imgproc.putText(img,"sara",p,2,2,white,2);
            }


        }
        for (int i=0;i<c.size();i++){
            Imgproc.drawContours(tocamera,c,i,new Scalar(0,0,255),5);
        }

        Log.d("rectangle-count", String.valueOf(count));
        Imgproc.erode(img, img, Kernel);
        return erRectangles;
    }

    public static ArrayList<ERElipse> getEllipse(Mat orig,Mat mat,Mat to_rhombus,Mat tocamera)
    {

        Imgproc.GaussianBlur(orig,orig,new Size(5,5),0);
        adaptiveThreshold(orig, orig, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 41, 5);


        Mat img=new Mat();
        ArrayList<ERElipse> erElipses=new ArrayList<>();
        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(15,15));
        Imgproc.morphologyEx(mat, img, MORPH_OPEN, kernel);

        Mat la=new Mat();
        int i= Imgproc.connectedComponents(img,la);
        List<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(img, contour, new Mat(), Imgproc.CV_SHAPE_ELLIPSE, Imgproc.CHAIN_APPROX_NONE);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat cut = null;
        int count=0;
        ArrayList<MatOfPoint> c=new ArrayList<>();
        for (MatOfPoint cnt : contour) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurve, 0.01 * Imgproc.arcLength(curve, true), true);


            int numberVertices = (int) approxCurve.total();
            if(numberVertices>8&&contourArea(cnt)>2000) {
                Rect rec = Imgproc.boundingRect(cnt);
                cut = orig.submat(rec);
                boolean check=there_is_line(cut);
                ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));
               String s="attr";
                ERElipse e=new ERElipse(center,s,check);
                erElipses.add(e);
                Imgproc.fillPoly(mat, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                Imgproc.fillPoly(img, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                count++;
                c.add(cnt);
                if(check)
                  putText(mat,"c",new Point(rec.x +(rec.width / 2), rec.y + (rec.height / 2)),2,2,new Scalar(200,200,200),4);
            }
        }
        for (int j=0;j<c.size();j++){
            Imgproc.drawContours(tocamera,c,j,new Scalar(255,0,0),5);
        }
        Log.d("3dd-elipse", String.valueOf(count));

       img.assignTo(to_rhombus);
        return erElipses;
    }

   public static ArrayList<ERRhombus> getRhombus(Mat orig,Mat mat,Mat from_ellipse,Mat tocamera){
        ArrayList<ERRhombus> erRhombuses=new ArrayList<>();
        Mat la=new Mat();


        List<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(from_ellipse, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat cut = null;
        int count=0;
       ArrayList<MatOfPoint> c=new ArrayList<>();
        for (MatOfPoint cnt : contour) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurve, 0.1 * Imgproc.arcLength(curve, true), true);


            int numberVertices = (int) approxCurve.total();
            if(numberVertices>3&&numberVertices<9&&Imgproc.contourArea(cnt)>2000) {
                Rect rec = Imgproc.boundingRect(cnt);
                cut = orig.submat(rec);

                Imgproc.fillPoly(mat, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                count++;
                ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));
               String s="relation";
                ERRhombus e=new ERRhombus(center,s);
                erRhombuses.add(e);
                c.add(cnt);

            }
        }
       for (int j=0;j<c.size();j++){
           Imgproc.drawContours(tocamera,c,j,new Scalar(0,255,0),5);
       }
        Log.d("3dd_r", String.valueOf(count));



        return erRhombuses;

    }
    public static boolean there_is_line (Mat src) //function to know whether there is any line or not
    {
        Imgproc.HoughLinesP(src, src, 1, Math.PI / 180, 0, 70, 30); // runs the actual detection
        return ! src.empty();
    }
    private static ArrayList<ERLine> getLines(Mat mat) {
        //todo kero   implement the method
        ArrayList<ERLine> erLines = new ArrayList<ERLine>();
        erLines.add(new ERLine(new ERShape.ERPoint(2, 2), new ERShape.ERPoint(3, 4)));


        return erLines;
    }

    private String getStringFromImage(Mat mat, Context c) {
        StringBuilder sb = new StringBuilder();
        Bitmap bitmap = convertToBitmap(mat);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(c).build();
        if (!textRecognizer.isOperational()) {
            return "couldn't get Text!";
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> text = textRecognizer.detect(frame);
            for (int i = 0; i < text.size(); i++) {
                TextBlock item = text.valueAt(i);
                sb.append(item.getValue());
            }
        }
        return sb.toString();
    }

    public static Mat loadTestPic(Context context, int testPicID) {
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

   /* private static ArrayList<ERShape> extractAllShapes(Mat originalImage) {
        ArrayList<ERShape> erShapes = new ArrayList<>();
        ArrayList<ERRectangle> rectangleArrayList = getRectangles(originalImage);



        return erShapes;
    }*/




}
