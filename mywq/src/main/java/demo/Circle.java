package demo;

public class Circle {
    final double cPI = 3.14;
    int cPoint_x,cPoint_y;
    double cRadius,cArea;

    public Circle() {
        cPoint_x = 0;
        cPoint_y = 0;
        cRadius = 1;
    }
    public Circle(int x, int y, double r) {
        cRadius = r;
        cPoint_x = x;
        cPoint_y = y;
    }
    public double cAreaCount() {
        cArea = cPI * cRadius * cRadius;
        return cArea;
    }
    public String toString() {
        return "圆心为(" + cPoint_x + "," + cPoint_y + ")半径 为 " + cRadius + "的圆的面积为" + cArea;
    }
}
