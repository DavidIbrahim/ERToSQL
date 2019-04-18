package com.example.david.ertosql;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.david.ertosql.er.shapes.ERLine;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERShape;

import org.junit.Test;


import static com.example.david.ertosql.ImageProcessing.getRectangles;
import static com.example.david.ertosql.ImageProcessing.loadTestPic;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ERshapesTest {


    @Test
    public void testErShapes() {

        ERShape rect = new ERRectangle(new ERShape.ERPoint(0, 0), "d");
        ERLine line = new ERLine(new ERShape.ERPoint(3, 4), new ERShape.ERPoint(0, 10));
        double d1 = line.calculateDistance(rect);
        double d2 = rect.calculateDistance(line);


        assertEquals(d1, d2, 0.1);
        assertEquals(5, d1, 0.1);
    }


}
