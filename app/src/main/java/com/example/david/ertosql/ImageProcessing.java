package com.example.david.ertosql;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.util.SortedList;
import android.util.SparseArray;
import android.util.Log;
import android.widget.ImageView;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.EREntity;
import com.example.david.ertosql.ERObjects.EROneToOneRelationship;
import com.example.david.ertosql.er.shapes.ERElipse;
import com.example.david.ertosql.er.shapes.ERLine;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERRhombus;
import com.example.david.ertosql.er.shapes.ERShape;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.MORPH_OPEN;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.minAreaRect;
import static org.opencv.imgproc.Imgproc.putText;

public class ImageProcessing {

    private static Context context=null;
    private final static String IMAGE_PROCESSING_TAG = ImageProcessing.class.getSimpleName();
    /**
     * this variable holds all test pictures
     * u can add new test pictures in raw folder and add their ids in this array so you can test them
     */
    public static int[] mTestPictures = {R.raw.ocr2};

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

                    Mat convertedImageMat = get_lines_test(originalImage);
                    imageView.setImageBitmap(convertToBitmap(convertedImageMat));
                } else {
                    //todo test ur own code here if it doesn't return an image
                    //this is an example
                //    Mat to_Rhombus=new Mat();
                  //  Mat copy_original=originalImage.clone();
                   // Mat tocamera=originalImage.clone();
                    //
                    // Mat toline=new Mat();
                   merge(originalImage,context);
                   //Imgproc.cvtColor(tocamera,tocamera,Imgproc.COLOR_GRAY2RGB);
                   // ArrayList<ERRectangle> rec = getRectangles(originalImage,tocamera,context);
                   // ArrayList<ERElipse> elipses=getEllipse(copy_original,originalImage,to_Rhombus,tocamera,context);
                   // Mat test=there_is_line(to_Rhombus);
                   // ArrayList<ERRhombus> erRhombuses=getRhombus(copy_original,originalImage,to_Rhombus,tocamera,context);
                   //
                     //Mat kiro=originalImage.clone();

                   // ArrayList<ERLine> lines =getLines(originalImage);
                   // Log.d(IMAGE_PROCESSING_TAG, "Testing with Image : " + i + '\n' + lines.toString());
                    imageView.setImageBitmap(convertToBitmap(originalImage));
                   // Log.d("textt", elipses.toString());
                }
                i++;
            }



            public void onFinish() {
            }
        }.start();


    }

    public static void  hilightShapes(Mat originalImage)
    {
        draw(originalImage);
      /*  Mat originalImageGrayScale=originalImage.clone();
        Imgproc.cvtColor(originalImageGrayScale,originalImageGrayScale,Imgproc.COLOR_RGB2GRAY);
        Mat to_Rhombus=new Mat();
        Mat copy_original=originalImageGrayScale.clone();
        Mat tocamera=originalImage;
      // Imgproc.cvtColor(tocamera,tocamera,Imgproc.COLOR_GRAY2RGB);
        ArrayList<ERRectangle> lines = getRectangles(originalImageGrayScale,tocamera);
        ArrayList<ERElipse> elipses=getEllipse(copy_original,originalImageGrayScale,to_Rhombus,tocamera);
        ArrayList<ERRhombus> erRhombuses=getRhombus(copy_original,originalImageGrayScale,to_Rhombus,tocamera);*/

    }

    private static Mat exampleOnUsingOpenCV(Mat mat) {
        //apply thresholding on image
        adaptiveThreshold(mat, mat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 10);

        return mat;

    }
    public static void draw(Mat orig)
    {
        Mat threshold=new Mat();

        Mat gray=new Mat();
        Imgproc.cvtColor(orig,gray,Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(gray,gray,new Size(5,5),0);
        adaptiveThreshold(gray, threshold, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 41, 5);

        //shapes
        //todo put in a fn
        Mat img=new Mat();
        Mat Kernel = new Mat(new Size(13, 13), CvType.CV_8U, new Scalar(255));
        Imgproc.dilate(threshold, img, Kernel);
        List<MatOfPoint> contours = new ArrayList<>();
        Scalar white= new Scalar(255, 255, 255);
        Imgproc.findContours(img, contours, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        Imgproc.fillPoly(img, contours, white);
        Imgproc.medianBlur(img,img,7);
        //rectangles
        Mat img1=new Mat();
        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(70, 50));
        Imgproc.morphologyEx(img, img1, MORPH_OPEN, kernel);
        List<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(img1, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
        // Log.d("getttpoint", String.valueOf(contour.size()));
        Scalar black = new Scalar(0,0, 0);
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

                c.add(cnt);
                Imgproc.fillPoly(img, Collections.singletonList(cnt), black);

            }
        }
        for (int i=0;i<c.size();i++){
            Imgproc.drawContours(orig,c,i,new Scalar(0,0,255),5);
        }
        //
        Imgproc.erode(img, img, Kernel);

        //for ellipse
        Mat imge=new Mat();
        Mat kernele=Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(15,15));
        Imgproc.morphologyEx(img, imge, MORPH_OPEN, kernele);
        List<MatOfPoint> contoure = new ArrayList<>();
        Imgproc.findContours(imge, contoure, new Mat(), Imgproc.CV_SHAPE_ELLIPSE, Imgproc.CHAIN_APPROX_NONE);
        MatOfPoint2f approxCurvee = new MatOfPoint2f();
        Mat cute = null;
        ArrayList<MatOfPoint> ce=new ArrayList<>();
        MatOfPoint2f approxCurver = new MatOfPoint2f();
        ArrayList<MatOfPoint> cr=new ArrayList<>();
        for (MatOfPoint cnt : contoure) {

            MatOfPoint2f curvee = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curvee, approxCurvee, 0.01 * Imgproc.arcLength(curvee, true), true);


            int numberVertices = (int) approxCurvee.total();

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurver, 0.1 * Imgproc.arcLength(curve, true), true);

            int numberVerticess = (int) approxCurver.total();
            if(numberVertices>8&&contourArea(cnt)>2000) {
                Imgproc.fillPoly(img, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                //  Imgproc.fillPoly(imge, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                ce.add(cnt);
            }
            else if(numberVerticess>3&&numberVerticess<9&&Imgproc.contourArea(cnt)>1000) {

                Imgproc.fillPoly(img, Collections.singletonList(cnt), new Scalar(0, 0, 0));

                cr.add(cnt);


            }
        }
        for (int j=0;j<ce.size();j++){
            Imgproc.drawContours(orig,ce,j,new Scalar(255,0,0),5);
        }
        //rhombus
      /*   List<MatOfPoint> contourr = new ArrayList<>();
         Imgproc.findContours(imge, contourr, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
         MatOfPoint2f approxCurver = new MatOfPoint2f();
         ArrayList<MatOfPoint> cr=new ArrayList<>();

         for (MatOfPoint cnt : contourr) {

             MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

             Imgproc.approxPolyDP(curve, approxCurver, 0.1 * Imgproc.arcLength(curve, true), true);

             int numberVertices = (int) approxCurver.total();
             if(numberVertices>3&&numberVertices<9&&Imgproc.contourArea(cnt)>1000) {

               //  Imgproc.fillPoly(img, Collections.singletonList(cnt), new Scalar(0, 0, 0));

                 cr.add(cnt);


             }

         }*/
      Mat ma=new Mat();
        for (int j=0;j<cr.size();j++){
            Imgproc.drawContours(orig,cr,j,new Scalar(0,255,0),5);
        }
        ArrayList<ERLine> erLine=getLines(img,ma);
        for(int i=0;i<erLine.size();i++)
        {
            Imgproc.line(orig, new Point(erLine.get(i).get_start().x,erLine.get(i).get_start().y),new Point(erLine.get(i).get_end().x,erLine.get(i).get_end().y), new Scalar(100, 200, 200), 3, Imgproc.LINE_AA, 0);

        }


    }

             /**
              * @param mat
              * @return array list contain ERReactangle of the image
              */

             public static ArrayList<ERRectangle> getRectangles (Mat mat, Mat tocamera, Context
             context){
                 //copy image

                 // Mat img = mat.clone();
                 Mat img = mat;
                 ArrayList<ERRectangle> erRectangles = new ArrayList<>();

                 //make threshold
                 Imgproc.GaussianBlur(img, img, new Size(5, 5), 0);
                 adaptiveThreshold(img, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 41, 5);


                 //copy the image
                 Mat orig = img.clone();

                 //dilation to get the shapes
                 Mat Kernel = new Mat(new Size(13, 13), CvType.CV_8U, new Scalar(255));
                 Imgproc.dilate(img, img, Kernel);

                 //contour all shapes in image and fill it with white color
                 List<MatOfPoint> contours = new ArrayList<>();
                 Scalar white = new Scalar(255, 255, 255);
                 Imgproc.findContours(img, contours, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
                 Imgproc.fillPoly(img, contours, white);
                 // Imgproc.erode(img, img, Kernel);

                 Imgproc.medianBlur(mat, mat, 7);
                 //make morph open using rectangle kernel
                 Mat img1 = new Mat();
                 Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(70, 50));
                 Imgproc.morphologyEx(img, img1, MORPH_OPEN, kernel);

                 //contour after changes
                 List<MatOfPoint> contour = new ArrayList<>();
                 Imgproc.findContours(img1, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
                 // Log.d("getttpoint", String.valueOf(contour.size()));
                 Scalar black = new Scalar(0, 0, 0);
                 MatOfPoint2f approxCurve = new MatOfPoint2f();
                 Mat cut = null;
                 int count = 0;

                 ArrayList<MatOfPoint> c = new ArrayList<>();
                 for (MatOfPoint cnt : contour) {

                     MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

                     Imgproc.approxPolyDP(curve, approxCurve, 0.015 * Imgproc.arcLength(curve, true), true);

                     int numberVertices = (int) approxCurve.total();

                     //Rectangle detected
                     if (numberVertices == 4 && Imgproc.contourArea(cnt) > 1000) {
                         Rect rec = Imgproc.boundingRect(cnt);

                         Point p = new Point(rec.x + (rec.width / 2), rec.y + (rec.height / 2));

                         cut = orig.submat(rec);
                         Log.d("rectangle-size", String.valueOf(cut.size()));
                         ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));

                         String text = getStringFromImage(cut, context);
                         text=text.replaceAll(" ","");
                         text=text.replaceAll(".","");
                         //  String text = "sara";
                         ERRectangle e = new ERRectangle(center, text);
                         erRectangles.add(e);
                         c.add(cnt);


                         //
                         Imgproc.fillPoly(img, Collections.singletonList(cnt), black);
                         count++;
                         Log.d("rectangle-text", text);
                         // Imgproc.putText(img,"sara",p,2,2,white,2);
                     }


                 }
                 for (int i = 0; i < c.size(); i++) {
                     Imgproc.drawContours(tocamera, c, i, new Scalar(0, 0, 255), 5);
                 }

                 Log.d("rectangle-count", String.valueOf(count));
                 Imgproc.erode(img, img, Kernel);
                 return erRectangles;
             }

             public static ArrayList<ERElipse> getEllipse (Mat orig, Mat mat, Mat to_rhombus, Mat tocamera, Context context)
             {

                 Imgproc.GaussianBlur(orig, orig, new Size(5, 5), 0);
                 adaptiveThreshold(orig, orig, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 41, 5);
                 //  Imgproc.threshold(orig,orig,0,255,THRESH_BINARY|THRESH_OTSU);
                 Mat img = new Mat();
                 ArrayList<ERElipse> erElipses = new ArrayList<>();
                 Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(15, 15));
                 Imgproc.morphologyEx(mat, img, MORPH_OPEN, kernel);

                 List<MatOfPoint> contour = new ArrayList<>();
                 Imgproc.findContours(img, contour, new Mat(), Imgproc.CV_SHAPE_ELLIPSE, Imgproc.CHAIN_APPROX_NONE);
                 MatOfPoint2f approxCurve = new MatOfPoint2f();
                 Mat cut = null;
                 int count = 0;
                 ArrayList<MatOfPoint> c = new ArrayList<>();
                 for (MatOfPoint cnt : contour) {

                     MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

                     Imgproc.approxPolyDP(curve, approxCurve, 0.01 * Imgproc.arcLength(curve, true), true);


                     int numberVertices = (int) approxCurve.total();
                     if (numberVertices > 8 && contourArea(cnt) > 2000) {
                         Rect rec = Imgproc.boundingRect(cnt);

                         cut = orig.submat(rec);
                         //  Mat k=Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(2,2));
                         // Imgproc.morphologyEx(cut,cut,Imgproc.MORPH_OPEN,k);
                         Log.d("ellipse-size", String.valueOf(cut.size()));
                         // boolean check=there_is_line(cut);
                         ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));
                         String text = getStringFromImage(cut, context);
                         text=text.replaceAll(" ","");
                         text=text.replaceAll(".","");
                         Boolean flag=there_is_line(cut);
                         ERElipse e = new ERElipse(center, text,flag);
                         erElipses.add(e);
                         Imgproc.fillPoly(mat, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                         Imgproc.fillPoly(img, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                         count++;

                         c.add(cnt);
                         Log.d("ellipse-text", text);
                         Log.d("ellipse-primary",String.valueOf(flag));

                        //  if(flag)
                          // putText(mat,"c",new Point(rec.x +(rec.width / 2), rec.y + (rec.height / 2)),2,2,new Scalar(200,200,200),4);
                     }
                 }
                 for (int j = 0; j < c.size(); j++) {
                     Imgproc.drawContours(tocamera, c, j, new Scalar(255, 0, 0), 5);
                 }
                 Log.d("3dd-elipse", String.valueOf(count));

               img.assignTo(to_rhombus);
                 return erElipses;
             }

             public static ArrayList<ERRhombus> getRhombus (Mat orig, Mat mat, Mat from_ellipse, Mat tocamera, Context context){
                 ArrayList<ERRhombus> erRhombuses = new ArrayList<>();
                 Mat la = new Mat();


                 List<MatOfPoint> contour = new ArrayList<>();
                 Imgproc.findContours(from_ellipse, contour, new Mat(), Imgproc.CV_SHAPE_RECT, Imgproc.CHAIN_APPROX_NONE);
                 MatOfPoint2f approxCurve = new MatOfPoint2f();
                 Mat cut = null;
                 int count = 0;
                 ArrayList<MatOfPoint> c = new ArrayList<>();
                 for (MatOfPoint cnt : contour) {

                     MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

                     Imgproc.approxPolyDP(curve, approxCurve, 0.1 * Imgproc.arcLength(curve, true), true);


                     int numberVertices = (int) approxCurve.total();
                     if (numberVertices > 3 && numberVertices < 9 && Imgproc.contourArea(cnt) > 1000) {
                         Rect rec = Imgproc.boundingRect(cnt);
                         cut = orig.submat(rec);

                         Imgproc.fillPoly(mat, Collections.singletonList(cnt), new Scalar(0, 0, 0));
                         count++;
                         ERShape.ERPoint center = new ERShape.ERPoint(rec.x + (rec.width / 2), rec.y + (rec.height / 2));

                         String text = getStringFromImage(cut, context);
                         text=text.replaceAll(" ","");
                         text=text.replaceAll(".","");
                         ERRhombus e = new ERRhombus(center, text);
                         erRhombuses.add(e);
                         c.add(cnt);
                         Log.d("rhombus-text", text);

                     }

                 }
                 for (int j = 0; j < c.size(); j++) {
                     Imgproc.drawContours(tocamera, c, j, new Scalar(0, 255, 0), 5);
                 }
                 Log.d("3dd_r", String.valueOf(count));
//         Mat kernel=Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(2,2));
//                 Imgproc.dilate(mat,mat,kernel);
//       Mat k=Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(4,4));
//       Imgproc.morphologyEx(mat,mat,Imgproc.MORPH_OPEN,k);
//
//       Imgproc.erode(mat,mat,kernel);


                 return erRhombuses;

             }
             public static Boolean there_is_line (Mat src) //function to know whether there is any line or not
             {
               //  Mat kernel=Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(20,1));
                  // Imgproc.erode(src,src,kernel);
              //   List<MatOfPoint> contour = new ArrayList<>();
               //  Imgproc.findContours(src, contour, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
              //   Log.d("kam wa7d", String.valueOf(contour.size()));
                //     Imgproc.drawContours(src,contour,-1,new Scalar(0,0,0),40);
               //  Mat kernel=Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(5,1));
                 // Imgproc.erode(src,src,kernel);
                 List<MatOfPoint> contourr = new ArrayList<>();
                 List<MatOfPoint2f> newContours = new ArrayList<>();
                 Imgproc.findContours(src, contourr, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
                 for(MatOfPoint point : contourr) {
                     MatOfPoint2f newPoint = new MatOfPoint2f(point.toArray());
                     newContours.add(newPoint);
                 }
                   //  Imgproc.fillPoly(src, Collections.singletonList(contour.get(0)), new Scalar(0, 0, 0));
                    // contour.get(0);

                 Boolean rep=false;
                 for (MatOfPoint2f cnt : newContours) {
                     RotatedRect rec=minAreaRect(cnt);
                   //  Imgproc.fillPoly(src, Collections.singletonList(cnt), new Scalar(255, 255, 255));
                     Point[] vertices = new Point[4];
                     rec.points(vertices);
                     double distx=abs(vertices[0].x-vertices[2].x);
                     double disty=abs(vertices[0].y-vertices[2].y);
                     Log.d("kam wa7d_x", String.valueOf(distx));
                     Log.d("kam wa7d_y", String.valueOf(disty));
                    if(distx>3*disty&&disty!=0)
                         rep=true;

                 }

                return rep;
                 // return ! src.empty();
             }
    private static ArrayList<ERLine> getLines (Mat src,Mat tocamera) {
        ArrayList<ERLine> erLine = new ArrayList<>();
      //  Imgproc.dilate(src,src,Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(5,5)));
        List<MatOfPoint> contour = new ArrayList<>();
        List<RotatedRect> minrec=new ArrayList<>(contour.size());
        Imgproc.findContours(src, contour, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        List<MatOfPoint2f> newContours = new ArrayList<>();

        for(MatOfPoint point : contour) {
            MatOfPoint2f newPoint = new MatOfPoint2f(point.toArray());
            newContours.add(newPoint);
        }
        Log.d("countoor", String.valueOf(newContours.size()));
         int count=0;
        for (MatOfPoint2f cnt : newContours) {

            RotatedRect rec=minAreaRect(cnt);
        if(rec.size.area()>1000&&rec.angle!=0) {
            Point[] vertices = new Point[4];
            rec.points(vertices);



          //  double x2=rec.boundingRect().x;
           // double y2=rec.boundingRect().y;
           // double x1=2*rec.center.x-x2;
           // double y1=2*rec.center.y-y2;
           // double length=(rec.size.height>rec.size.width)? rec.size.height:rec.size.width;
          //  double x2=rec.center.x-((length/2)*cos(rec.angle+180));
            //double y2=rec.center.y+((length/2)*sin(rec.angle+180));
            //double x1=rec.center.x+((length/2)*cos(rec.angle+180));
            //double y1=rec.center.y+((length/2)*sin(rec.angle+180));
               Point start = vertices[0];
               Point end=vertices[2];
            ERShape.ERPoint s=new ERShape.ERPoint(start.x,start.y);
            ERShape.ERPoint e=new ERShape.ERPoint(end.x,end.y);
               ERLine l=new ERLine(s,e);
               erLine.add(l);

            //   putText(src, "s", start, 3, 3, new Scalar(255, 255, 255),3);
              //  putText(src, "E", end, 3, 3, new Scalar(255, 255, 255),3);
           // Log.d("angle", String.valueOf(rec.angle));

            count++;


           }
            Log.d("count_line", String.valueOf(count));
           // Rect rec = Imgproc.boundingRect(cnt);
        }
        for(int i=0;i<erLine.size();i++)
        {
            Imgproc.line(tocamera, new Point(erLine.get(i).get_start().x,erLine.get(i).get_start().y),new Point(erLine.get(i).get_end().x,erLine.get(i).get_end().y), new Scalar(100, 200, 200), 3, Imgproc.LINE_AA, 0);

        }

          return erLine;
    }
         /*   private static ArrayList<ERLine> getLines (Mat src){
                 //todo kero   implement the method

                 //erLines.add(new ERLine(new ERShape.ERPoint(2, 2), new ERShape.ERPoint(3, 4)));
                 double wid = src.size().height;
                 double hei = src.size().width;
                 Log.d("m2as el sora", String.valueOf(wid)+String.valueOf(hei));
                 set_tolerance(wid, hei);
                 set_slope_c_tolerance(wid, hei);
                 Mat dst = new Mat();
                 Mat cdstP = new Mat();
                 Imgproc.Canny(src, dst, 50, 200, 3, false);
                 Imgproc.cvtColor(dst, cdstP, Imgproc.COLOR_GRAY2BGR);
                 Mat linesP = new Mat(); // will hold the results of the detection
                 Imgproc.HoughLinesP(dst, linesP, 1, Math.PI / 180, 20, 10, 5); // runs the actual detection
                 ArrayList<ERLine> erLine = new ArrayList<ERLine>();
                 for (int x = 0; x < linesP.rows(); x++) {
                     double[] l = linesP.get(x, 0);
                     ERLine l1 = new ERLine(new ERShape.ERPoint(l[0], l[1]), new ERShape.ERPoint(l[2], l[3]));
                     erLine.add(l1);
                 }
                 boolean flag = false;
                 for (int j = 0; j < erLine.size(); j++) {
                     flag = false;
                     int ff = erLine.size();
                     for (int i = j + 1; i < erLine.size(); i++) {

                         if ((ExtendLine(erLine.get(i), erLine.get(j))) || (InsideLine(erLine.get(i), erLine.get(j)))) {
                             ERShape.ERPoint p1 = new ERShape.ERPoint(0, 0);
                             ERShape.ERPoint p2 = new ERShape.ERPoint(0, 0);
                             if (erLine.get(i).get_slope() == 500) {
                                 p2 = most_y(erLine.get(i).get_start(), erLine.get(i).get_end(), erLine.get(j).get_start(), erLine.get(j).get_end());
                                 p1 = least_y(erLine.get(i).get_start(), erLine.get(i).get_end(), erLine.get(j).get_start(), erLine.get(j).get_end());
                             } else {
                                 p2 = most_x(erLine.get(i).get_start(), erLine.get(i).get_end(), erLine.get(j).get_start(), erLine.get(j).get_end());
                                 p1 = least_x(erLine.get(i).get_start(), erLine.get(i).get_end(), erLine.get(j).get_start(), erLine.get(j).get_end());
                             }
                             ERLine ged = new ERLine(p1, p2);
                             if (ged.get_slope() == 500 && erLine.get(i).get_slope() != 500 && erLine.get(j).get_slope() != 500) {
                                 ged.set_slope_c(erLine.get(i).get_slope(), erLine.get(i).get_c());
                             }
                             if (erLine.get(i).get_slope() == 500) {
                                 ged.set_slope_c(500, erLine.get(i).get_c());
                             }
                             if (erLine.get(j).get_slope() == 500) {
                                 ged.set_slope_c(500, erLine.get(j).get_c());
                             }
                             erLine.remove(i);
                             erLine.remove(j);
                             erLine.add(0, ged);
                             flag = true;
                         }
                         if (flag)
                             break;
                     }
                     if (flag)
                         j = -1;

                 }
                 for(int i=0;i<erLine.size();i++) {
                     Log.d("liness", erLine.get(i).toString());
                     Imgproc.line(cdstP, new Point(erLine.get(i).get_start().x,erLine.get(i).get_start().y),new Point(erLine.get(i).get_end().x,erLine.get(i).get_end().y), new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);

                 }

                 return erLine;
                 ArrayList<ERLine> erLine = new ArrayList<>();
                 ERLine l=new ERLine(new ERShape.ERPoint(386,500),new ERShape.ERPoint(570,175));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(386,500),new ERShape.ERPoint(250,165));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(386,500),new ERShape.ERPoint(1000,560));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(386,500),new ERShape.ERPoint(125,870));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(386,500),new ERShape.ERPoint(455,920));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(1700,615),new ERShape.ERPoint(1545,285));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(1700,615),new ERShape.ERPoint(1915,255));
                 erLine.add(l);
                 l=new ERLine(new ERShape.ERPoint(1700,615),new ERShape.ERPoint(1000,560));
                 erLine.add(l);
                 return erLine;
             }*/
             //method to show the detected lines
             public static Mat get_lines_test (Mat src)
             {
                 Mat dst = new Mat();
                 Mat cdstP = new Mat();
                 Imgproc.Canny(src, dst, 50, 200, 3, false);
                 Imgproc.cvtColor(dst, cdstP, Imgproc.COLOR_GRAY2BGR);
                 Mat linesP = new Mat(); // will hold the results of the detection
                 Imgproc.HoughLinesP(dst, linesP, 1, Math.PI / 180, 20, 10, 5); // runs the actual detection
// ArrayList<Linek> Lineks = new ArrayList<Linek>(1);
                 for (int x = 0; x < linesP.rows(); x++) {
                     double[] l = linesP.get(x, 0);
                     Imgproc.line(cdstP, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);
                 }

                 return cdstP;
             }

             private static String getStringFromImage (Mat mat, Context c){
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

             public static Mat loadTestPic (Context context,int testPicID){
                 Mat mat = null;
                 try {
                     // load the image in grey scale and save it in mat ..... change pic1 for different resource
                     mat = Utils.loadResource(context, testPicID, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 return mat;
             }

             private static Bitmap convertToBitmap (Mat mat){
                 Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                 Utils.matToBitmap(mat, bitmap);
                 return bitmap;
             }

   /* private static ArrayList<ERShape> extractAllShapes(Mat originalImage) {
        ArrayList<ERShape> erShapes = new ArrayList<>();
        ArrayList<ERRectangle> rectangleArrayList = getRectangles(originalImage);



        return erShapes;
    }*/

             private static ERShape.ERShapeWZCenter min_disrance_rectangle (ERLine
             line, ArrayList < ERRectangle > rec)
             {
                 ERShape.ERShapeWZCenter min = rec.get(0);
                 double mind = line.calculateDistance(rec.get(0));
                 for (int i = 1; i < rec.size(); i++) {
                     double temp = line.calculateDistance(rec.get(i));
                     if (temp < mind) {
                         mind = temp;
                         min = rec.get(i);
                     }
                 }

                 return min;
             }
             private static ERShape.ERShapeWZCenter min_distance_shapes (ERLine
             line, ArrayList < ERElipse > elipses, ArrayList < ERRhombus > rhom)
             {
                 ERShape.ERShapeWZCenter min = elipses.get(0);
                 double mind = line.calculateDistance(elipses.get(0));
                 for (int i = 1; i < elipses.size(); i++) {
                     double temp = line.calculateDistance(elipses.get(i));
                     if (temp < mind) {
                         mind = temp;
                         min = elipses.get(i);
                     }
                 }
                 for (int i = 0; i < rhom.size(); i++) {
                     double temp = line.calculateDistance(rhom.get(i));
                     if (temp < mind) {
                         mind = temp;
                         min = rhom.get(i);
                     }
                 }
                 return min;
             }


             private static void merge (Mat pic, Context context)
             {
                 Mat to_Rhombus = new Mat();
                 Mat copy_original = pic.clone();
                 Mat tocamera = pic.clone();
                 Mat toline = new Mat();

                 Imgproc.cvtColor(tocamera, tocamera, Imgproc.COLOR_GRAY2RGB);
                 ArrayList<ERRectangle> rec = getRectangles(pic, tocamera, context);

                 ArrayList<ERShape.ERShapeWZCenter> REC = new ArrayList<>();
                 for (int i = 0; i < rec.size(); i++) {
                     REC.add(rec.get(i));
                 }
                 ArrayList<ERElipse> elipses = getEllipse(copy_original, pic, to_Rhombus, tocamera, context);
                 ArrayList<ERRhombus> erRhombuses = getRhombus(copy_original, pic, to_Rhombus, tocamera, context);
                 ArrayList<ERLine> lines = getLines(pic,tocamera);
                 Map<ERShape.ERShapeWZCenter, ArrayList<ERShape.ERShapeWZCenter>> atrr = new HashMap<>();
                 Map<ERShape.ERShapeWZCenter, ArrayList<ERShape.ERShapeWZCenter>> relation = new HashMap<>();
                 Map<ERShape.ERShapeWZCenter, ArrayList<ERShape.ERShapeWZCenter>> uni = new HashMap<>();
                 ArrayList<ERShape.ERShapeWZCenter> totale = new ArrayList<>();

                 for (int i = 0; i < lines.size(); i++) {
                     ERShape.ERShapeWZCenter minrec = min_disrance_rectangle(lines.get(i), rec);
                     ERShape.ERShapeWZCenter connect = min_distance_shapes(lines.get(i), elipses, erRhombuses);
                     if (connect instanceof ERElipse) {
                         if (((ERElipse) connect).isUnderLined()) {
                             ArrayList<ERShape.ERShapeWZCenter> tempe = new ArrayList<>();
                             tempe.add(connect);
                             uni.put(minrec, tempe);

                         } else if (!atrr.containsKey(minrec)) {
                             ArrayList<ERShape.ERShapeWZCenter> tempe = new ArrayList<>();
                             tempe.add(connect);
                             atrr.put(minrec, tempe);

                         } else {
                             atrr.get(minrec).add(connect);
                         }


                     } else if (connect instanceof ERRhombus) {

                         if (!relation.containsKey(connect)) {
                             ArrayList<ERShape.ERShapeWZCenter> tempe = new ArrayList<>();
                             tempe.add(minrec);
                             relation.put(connect, tempe);

                         } else if (relation.get(connect).contains(minrec)) {
                             totale.add(minrec);
                         } else {
                             relation.get(connect).add(minrec);
                         }
                     }

                 }
                 ArrayList<EROneToOneRelationship> erRelationships = new ArrayList<>();
                 ArrayList<EREntity> erEntities = new ArrayList<>();
                 // ArrayList<ERAttribute> erAttributes=new ArrayList<>();

                 for (int j = 0; j < REC.size(); j++) {
                     ArrayList<ERAttribute> erAttributes = new ArrayList<>();
                     ArrayList<ERAttribute> erAttributesuni = new ArrayList<>();

                     if(atrr.containsKey(REC.get(j))) {
                         ArrayList<ERShape.ERShapeWZCenter> T= atrr.get(REC.get(j));
                         Log.d("atrr" + String.valueOf(j), REC.get(j).toString());
                     for (int i = 0; i <T.size(); i++) {
                          erAttributes.add(new ERAttribute(T.get(i).getText()));
                           //  erAttributes.add(new ERAttribute(atrr.get(REC.get(j)).get(i).getText()));
                         }
                     }

                       erAttributesuni.add(new ERAttribute(uni.get(REC.get(j)).get(0).getText()));


                     EREntity E = new EREntity(REC.get(j).getText(), erAttributesuni, erAttributes);
                     erEntities.add(E);
                 }

                 Log.d("List of entity", erEntities.toString());
             EREntity e1=null;
             EREntity e2=null;
             Boolean toe1=false;
             Boolean toe2=false;
                 for(int j=0;j<erRhombuses.size();j++)
                 {
                     ArrayList<ERShape.ERShapeWZCenter> T= relation.get(erRhombuses.get(j));
                     for(int i=0;i<T.size();i++) {
                         for(int k=0;k<erEntities.size();k++) {
                             if (T.get(i).getText() == erEntities.get(k).title) {
                                 if(e1==null) {
                                     if(totale.contains(T.get(i)))
                                         toe1=true;
                                     e1 = erEntities.get(k);
                                 }
                                 else
                                 if(totale.contains(T.get(i)))
                                     toe2=true;
                                     e2=erEntities.get(k);
                             }
                         }
                     }
                     if(toe1&&toe2)
                         erRelationships.add(new EROneToOneRelationship(erRhombuses.get(j).getText(),e1,e2, EROneToOneRelationship.Participation.TOTAL_TOTAL));
                         else if(toe1&&!toe2)
                         erRelationships.add(new EROneToOneRelationship(erRhombuses.get(j).getText(),e1,e2, EROneToOneRelationship.Participation.TOTAL_PARTIAL));
                         else
                         erRelationships.add(new EROneToOneRelationship(erRhombuses.get(j).getText(),e1,e2, EROneToOneRelationship.Participation.PARTIAL_PARTIAL));



                 }

                 //  Log.d("keyyy", relation.keySet().toString());
                   Log.d("test", erRelationships.toString());
    /* ArrayList<ERShape> atr=new ArrayList<>();
      for(ERShape key:atrr.keySet())
      {
          atr.add((ERShape) atrr.values());
          Log.d("test", atr.toString());
          break;
      }*/
                 tocamera.assignTo(pic);

             }


         }

