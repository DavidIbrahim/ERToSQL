package com.example.david.ertosql;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.david.ertosql.er.shapes.ERElipse;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERRhombus;
import com.example.david.ertosql.er.shapes.ERShape;
import com.example.david.ertosql.er.shapes.ERShape.ERPoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ImageProcessingTest {
    private static final String TAG = ImageProcessingTest.class.getSimpleName();
    Context appContext;
    ArrayList<ArrayList<ERRectangle>> expectedRectangles = new ArrayList<>();
    ArrayList<ArrayList<ERElipse>> expectedEllipsess = new ArrayList<>();
    ArrayList<ArrayList<ERRhombus>> expectedRhombus = new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "opencv not loaded");
        } else {
            Log.d(TAG, "opencv loaded");
        }


        // building the expected output of rectangles
        ArrayList<ERRectangle> rectanglesFirstPic = new ArrayList<ERRectangle>() {
            {
                add(new ERRectangle(new ERPoint(1709, 623), "sara"));
                add(new ERRectangle(new ERPoint(386, 513), "sara"));
            }
        };
        expectedRectangles.add(rectanglesFirstPic);
        ArrayList<ERRectangle> rectangles2ndPic = new ArrayList<ERRectangle>() {
            {
                add(new ERRectangle(new ERPoint(711, 819), "sara"));
                add(new ERRectangle(new ERPoint(1400, 714), "sara"));
                add(new ERRectangle(new ERPoint(1410, 215), "sara"));
                add(new ERRectangle(new ERPoint(316, 199), "sara"));

            }
        };
        expectedRectangles.add(rectangles2ndPic);
        ArrayList<ERRectangle> rectangles3rdPic = new ArrayList<ERRectangle>() {
            {
                add(new ERRectangle(new ERPoint(1921, 1483), "sara"));
                add(new ERRectangle(new ERPoint(641, 725), "sara"));
                add(new ERRectangle(new ERPoint(1877, 709), "sara"));

            }
        };
        expectedRectangles.add(rectangles3rdPic);
        ArrayList<ERRectangle> rectangles4thPic = new ArrayList<ERRectangle>() {
            {

                add(new ERRectangle(new ERPoint(1686, 1224), "sara"));
                add(new ERRectangle(new ERPoint(578, 1208), "sara"));
                add(new ERRectangle(new ERPoint(616, 593), "sara"));
                add(new ERRectangle(new ERPoint(1649, 585), "sara"));


            }
        };
        expectedRectangles.add(rectangles4thPic);
        ArrayList<ERRectangle> rectangles5thPic = new ArrayList<ERRectangle>() {
            {

                add(new ERRectangle(new ERPoint(986, 2586), "sara"));
                add(new ERRectangle(new ERPoint(1001, 1517), "sara"));
                add(new ERRectangle(new ERPoint(2270, 1484), "sara"));
                add(new ERRectangle(new ERPoint(1504, 621), "sara"));


            }
        };
        expectedRectangles.add(rectangles5thPic);

        ArrayList<ERRectangle> rectangles7thPic = new ArrayList<ERRectangle>() {
            {

                add(new ERRectangle(new ERPoint(2345, 1507), "sara"));
                add(new ERRectangle(new ERPoint(924, 647), "sara"));
            }
        };
        expectedRectangles.add(rectangles7thPic);


    // building the expected output of ellipse
    ArrayList<ERElipse> ellipsesFirstPic = new ArrayList<ERElipse>() {
        {
            add(new ERElipse(new ERPoint(462,928), "attr", true));
            add(new ERElipse(new ERPoint(130,877), "attr", true));
            add(new ERElipse(new ERPoint(1553,291), "attr", true));
            add(new ERElipse(new ERPoint(1920,262), "attr", true));
            add(new ERElipse(new ERPoint(578,181), "attr", true));
            add(new ERElipse(new ERPoint(260,176), "attr", true));

        }
    };
        expectedEllipsess.add(ellipsesFirstPic);
        ArrayList<ERElipse> ellipses2ndPic = new ArrayList<ERElipse>() {
            {
                add(new ERElipse(new ERPoint(200,925), "attr", true));
                add(new ERElipse(new ERPoint(258,777), "attr", true));
                add(new ERElipse(new ERPoint(449,680), "attr", true));
                add(new ERElipse(new ERPoint(1056,682), "attr", true));
                add(new ERElipse(new ERPoint(1196,566), "attr", true));
                add(new ERElipse(new ERPoint(1608,566), "attr", true));
                add(new ERElipse(new ERPoint(479,448), "attr", true));
                add(new ERElipse(new ERPoint(88,435), "attr", true));
                add(new ERElipse(new ERPoint(297,377), "attr", true));
                add(new ERElipse(new ERPoint(1308,79), "attr", true));
                add(new ERElipse(new ERPoint(1543,71), "attr", true));


            }
        };
        expectedEllipsess.add(ellipses2ndPic);
        ArrayList<ERElipse> ellipses3rdPic = new ArrayList<ERElipse>() {
            {
                add(new ERElipse(new ERPoint(1858,2164), "attr", true));
                add(new ERElipse(new ERPoint(2618,2027), "attr", true));
                add(new ERElipse(new ERPoint(1473,1869), "attr", true));
                add(new ERElipse(new ERPoint(306,385), "attr", true));
                add(new ERElipse(new ERPoint(1761,346), "attr", true));
                add(new ERElipse(new ERPoint(2170,287), "attr", true));
                add(new ERElipse(new ERPoint(991,322), "attr", true));
                add(new ERElipse(new ERPoint(621,131), "attr", true));



            }
        };
        expectedEllipsess.add(ellipses3rdPic);
        ArrayList<ERElipse> ellipses4thPic = new ArrayList<ERElipse>() {
            {
                add(new ERElipse(new ERPoint(521,1825), "attr", true));
                add(new ERElipse(new ERPoint(1635,1787), "attr", true));
                add(new ERElipse(new ERPoint(2298,1693), "attr", true));
                add(new ERElipse(new ERPoint(242,1536), "attr", true));
                add(new ERElipse(new ERPoint(1305,1531), "attr", true));
                add(new ERElipse(new ERPoint(340,313), "attr", true));
                add(new ERElipse(new ERPoint(1550,284), "attr", true));
                add(new ERElipse(new ERPoint(1899,237), "attr", true));
                add(new ERElipse(new ERPoint(903,264), "attr", true));
                add(new ERElipse(new ERPoint(599,105), "attr", true));



            }
        };
        expectedEllipsess.add(ellipses4thPic);
        ArrayList<ERElipse> ellipses5thPic = new ArrayList<ERElipse>() {
            {
                add(new ERElipse(new ERPoint(883,3188), "attr", true));
                add(new ERElipse(new ERPoint(1630,3186), "attr", true));
                add(new ERElipse(new ERPoint(2048,2988), "attr", true));
                add(new ERElipse(new ERPoint(449,2947), "attr", true));
                add(new ERElipse(new ERPoint(2392,2051), "attr", true));
                add(new ERElipse(new ERPoint(1839,1882), "attr", true));
                add(new ERElipse(new ERPoint(2795,1862), "attr", true));
                add(new ERElipse(new ERPoint(263,1779), "attr", true));
                add(new ERElipse(new ERPoint(355,1468), "attr", true));
                add(new ERElipse(new ERPoint(387,1167), "attr", true));
                add(new ERElipse(new ERPoint(1025,256), "attr", true));
                add(new ERElipse(new ERPoint(1857,186), "attr", true));

            }
        };
        expectedEllipsess.add(ellipses5thPic);
        ArrayList<ERElipse> ellipses7thPic = new ArrayList<ERElipse>() {
            {
                add(new ERElipse(new ERPoint(1402,2248), "attr", true));
                add(new ERElipse(new ERPoint(2353,2201), "attr", true));
                add(new ERElipse(new ERPoint(1302,1588), "attr", true));
                add(new ERElipse(new ERPoint(3353,1541), "attr", true));
                add(new ERElipse(new ERPoint(421,1146), "attr", true));

            }
        };
        expectedEllipsess.add(ellipses7thPic);
        expectedEllipsess.add(ellipses5thPic);
        ArrayList<ERRhombus> RhombusFirstPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(1013,569), "relation"));


            }
        };
        expectedRhombus.add(RhombusFirstPic);
        ArrayList<ERRhombus> Rhombus2ndPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(711,523), "relation"));
                add(new ERRhombus(new ERPoint(1383,481), "relation"));
                add(new ERRhombus(new ERPoint(757,210), "relation"));

            }
        };
        expectedRhombus.add(Rhombus2ndPic);
        ArrayList<ERRhombus> Rhombus3rdPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(1895,1087), "relation"));
                add(new ERRhombus(new ERPoint(1289,708), "relation"));


            }
        };
        expectedRhombus.add(Rhombus3rdPic);
        ArrayList<ERRhombus> Rhombus4thPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(1662,895), "relation"));
                add(new ERRhombus(new ERPoint(627,879), "relation"));
                add(new ERRhombus(new ERPoint(1149,581), "relation"));

            }
        };
        expectedRhombus.add(Rhombus4thPic);
        ArrayList<ERRhombus> Rhombus5thPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(1025,2078), "relation"));
                add(new ERRhombus(new ERPoint(1709,1533), "relation"));
                add(new ERRhombus(new ERPoint(991,1103), "relation"));

            }
        };
        expectedRhombus.add(Rhombus5thPic);
        ArrayList<ERRhombus> Rhombus7thPic = new ArrayList<ERRhombus>() {
            {
                add(new ERRhombus(new ERPoint(2248,663), "relation"));



            }
        };
        expectedRhombus.add(Rhombus7thPic);
}

    @Test
    public void testSHAPES() {


        for (int i = 0; i <ImageProcessing.mTestPictures.length; i++) {
            int mTestPicture = ImageProcessing.mTestPictures[i];
            Mat mat = ImageProcessing.loadTestPic(appContext, mTestPicture);
            Mat copy=mat.clone();
            Mat tocamera=mat.clone();
            Mat img=new Mat();
            ArrayList<ERRectangle> rectangles = ImageProcessing.getRectangles(mat,tocamera);

            System.out.println(rectangles);
            assertArrayEquals(expectedRectangles.get(i).toArray(), rectangles.toArray());

            ArrayList<ERElipse> ellipses = ImageProcessing.getEllipse(copy,mat,img,tocamera);
            System.out.println(ellipses);
            assertArrayEquals(expectedEllipsess.get(i).toArray(), ellipses.toArray());
            ArrayList<ERRhombus> erRhombuses=ImageProcessing.getRhombus(copy,mat,img,tocamera);
            System.out.println(erRhombuses);
            assertArrayEquals(expectedRhombus.get(i).toArray(), erRhombuses.toArray());

        }
    }
}
