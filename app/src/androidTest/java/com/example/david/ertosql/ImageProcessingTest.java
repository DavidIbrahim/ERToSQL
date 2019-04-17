package com.example.david.ertosql;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.david.ertosql.er.shapes.ERRectangle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    Context appContext;
    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();

    }



    @Test
    public void testGetRectangles() {
        for (int i = 0; i <ImageProcessing.mTestPictures.length ; i++) {
            int mTestPicture = ImageProcessing.mTestPictures[i];
            Mat mat = ImageProcessing.loadTestPic(appContext, mTestPicture);
            ArrayList<ERRectangle> rectangles = ImageProcessing.getRectangles(mat);
            System.out.println(rectangles.toString());


        }
    }
}
