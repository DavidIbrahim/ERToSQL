package com.example.david.ertosql.er.shapes;
import static com.example.david.ertosql.er.shapes.ERShape.ERPoint.isclose;
public class ERLine implements ERShape {
    private ERPoint start, end;
   // private double slope , c ;
    //private static double slope_tolerance;
    //private static double c_tolerance;


    public ERLine(ERPoint start, ERPoint end) {
        this.start = start;
        this.end = end;
      /*  if (start.get_x()==end.get_x())
            slope=500;
        else slope=((start.get_y()-end.get_y())/(start.get_x()-end.get_x()));
        c = start.get_y()-(start.get_x()*slope) ;*/
    }
    public ERPoint get_start()
    {
        return start;
    }
    public ERPoint get_end()
    {
        return end;
    }
  /*  public double get_slope()
    {
        return slope;
    }
    public double get_c()
    {
        return c;
    }
    public void set_slope_c (double ss,double cc)
    {
        slope=ss;
        c=cc;
    }

    public static void set_slope_c_tolerance(double width,double height)
    {
        slope_tolerance=(height/width)+0.7;
        c_tolerance=height/10;
    }

    public static boolean ExtendLine (ERLine v,ERLine s)
    {
        if ( isclose(v.start,s.start)||isclose(v.start,s.end)||isclose(v.end,s.start)||isclose(v.end,s.end))
        {
            if (v.slope==s.slope&&v.slope==500)
            {
                return true ;
            }
            if(v.slope==500&&(s.start.get_x()-s.c_tolerance/7)<=s.end.get_x()&&s.end.get_x()<=(s.start.get_x()+s.c_tolerance/7))
            {
                return true ;
            }
            if(s.slope==500&&(v.start.get_x()-s.c_tolerance/7)<=v.end.get_x()&&v.end.get_x()<=(v.start.get_x()+s.c_tolerance/7))
            {
                return true ;
            }
            if ((v.slope - s.slope_tolerance) <= s.slope && s.slope <= (v.slope + s.slope_tolerance))
                return true;
        }

        return false;

    }

    public static boolean InsideLine (ERLine v , ERLine s )
    {
        if (( v.start.get_x() < s.start.get_x() && s.start.get_x() < v.end.get_x() ) || ( v.start.get_x() < s.end.get_x() && v.start.get_x() > s.start.get_x() ) ) {
            if ((v.slope == s.slope && v.slope == 500) && ((v.c - s.c_tolerance*450) <= s.c && s.c <= (v.c + s.c_tolerance*450))) {
                return true;
            } else if (((v.slope - s.slope_tolerance) <= s.slope && s.slope <= (v.slope + s.slope_tolerance)) && ((v.c - s.c_tolerance*1.3) <= s.c && s.c <= (v.c + s.c_tolerance*1.3))) {
                return true;
            }

        }

        if ((v.start.get_y() < s.start.get_y() && s.start.get_y() < v.end.get_y()) || (v.start.get_y() < s.end.get_y() && v.start.get_y() > s.start.get_y()))
        {
            if ((v.slope == s.slope && v.slope == 500) && ((v.c - s.c_tolerance*450) <= s.c && s.c <= (v.c + s.c_tolerance*450))) {
                return true;
            }
            if (s.slope==500&&(v.start.get_x()-s.c_tolerance/7)<=v.end.get_x()&&v.end.get_x()<=(v.start.get_x()+s.c_tolerance/7))
            {
                return true ;
            }
            if(v.slope==500&&(s.start.get_x()-s.c_tolerance/7)<=s.end.get_x()&&s.end.get_x()<=(s.start.get_x()+s.c_tolerance/7))
            {
                return true ;
            }
        }
        return false ;
    }
*/
    @Override
    public double calculateDistance(ERShape shape) {
        if(shape instanceof ERShapeWZCenter){
            ERShapeWZCenter shapeWZCenter = (ERShapeWZCenter) shape;
            double d1 = ERShapeUtilities.calculateDistanceBetweenTwoPoints(start,shapeWZCenter.getCenter());
            double d2 = ERShapeUtilities.calculateDistanceBetweenTwoPoints(end,shapeWZCenter.getCenter());
            return d1<d2 ? d1 : d2;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "ERLine{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
