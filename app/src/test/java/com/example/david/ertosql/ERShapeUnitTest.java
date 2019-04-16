package com.example.david.ertosql;

import com.example.david.ertosql.er.shapes.ERLine;
import com.example.david.ertosql.er.shapes.ERRectangle;
import com.example.david.ertosql.er.shapes.ERShape;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ERShapeUnitTest {
    @Test
    public void calculateDistance() {

        ERShape rect = new ERRectangle(new ERShape.ERPoint(0,0),"d");
        ERLine line = new ERLine(new ERShape.ERPoint(3,4),new ERShape.ERPoint(0,10));
        double d1 = line.calculateDistance(rect);
        double d2 = rect.calculateDistance(line);


        assertEquals(d1,d2,0.1);
        assertEquals(5,d1,0.1);
    }

}