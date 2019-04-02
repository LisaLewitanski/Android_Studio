package lewitanski.lisa.com.assignment3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * <b>Graph</b>
 * <p>
 * This class allows to create and display the graph.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class Graph extends View {
    /**
     * myPaint is the Paint variable.
     * @see Graph#myPaint
     *
     * This is the width of the graph.
     * @see Graph#width
     *
     * This is the height of the graph.
     * @see Graph#height
     *
     * This is the time (x-axis).
     * @see Graph#x
     *
     * This is the altitude (y-axis).
     * @see Graph#y
     *
     * It's use to draw the line.
     * @see Graph#path
     *
     **/
    private Paint   myPaint;
    private int     width;
    private int     height;
    private double[] y, x;
    Path            path;

    /**
     * This method initialize the paint.
     *
     * @param context : The context
     * @param attrs : The attribute
     * @see Graph#Graph(Context, AttributeSet)
     */
    public Graph(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(0xFF33B5E5);
        myPaint.setStrokeWidth(4);
        myPaint.setAntiAlias(true);
        myPaint.setShadowLayer(4, 2, 2, 0x80000000);
    }

    /**
     * This method draw the graph in the view.
     *
     * @param canvas: The canvas
     * @see Graph#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredWidth();
        if (x.length == 0 || y.length == 0)
            return;

        double maxXvalue = getMax(x);
        double maxYvalue = getMax(y);
        path.moveTo(getXPos(x[0], maxXvalue), getYPos(y[0], maxYvalue));
        for (int i = 1; i < x.length && i < y.length; i += 1) {
            path.lineTo(getXPos(x[i], maxXvalue), getYPos(y[i], maxYvalue));
        }
        canvas.drawPath(path, myPaint);
    }

    /**
     * This method get the maximum value.
     * It's used to know where the all points will be to avoid overflow.
     *
     * @param points: All points (x or y)
     * @see Graph#getMax(double[])
     * @return The maximum point
     */
    private double getMax(@NonNull double[] points) {
        if (points.length == 0)
            return 0.0f;

        double ret = points[0];
        for (double elem : points) {
            if (elem > ret)
                ret = elem;
        }
        return ret;
    }

    /**
     * This method allows to get the y position of a point.
     *
     * @param value: The value
     * @param maxValue: The max value
     * @see Graph#getYPos(double, double)
     * @return The y position
     */
    private float getYPos(double value, double maxValue) {
        double xheight = (height - getPaddingTop() - getPaddingBottom());
        double pad = 0.1 * xheight;

        xheight -= pad;
        // scale to view height
        value = (value / maxValue) * xheight;
        // invert
        value = xheight + pad - value;
        // offset for padding
        value += getPaddingTop();
        return (float)value;
    }

    /**
     * This method allows to get the x position of a point.
     *
     * @param value: The value
     * @param maxValue: The max value
     * @see Graph#getXPos(double, double)
     * @return The x position
     */
    private float getXPos(double value, double maxValue) {
        double xwidth = width - getPaddingRight() - getPaddingLeft();
        // scale to view height
        value = (value / maxValue) * xwidth;
        // offset for padding
        value += getPaddingLeft();
        return (float)value;
    }

    /**
     * This method allows to create the graph with a given values.
     *
     * @param x:
     * @param y:
     * @see Graph#setGraphData(double[], double[])
     */
    public void setGraphData(double[] x, double[] y) {
        this.x = x.clone();
        this.y = y.clone();
        this.path = new Path();
        this.invalidate();
    }

    /**
     * The overriden onMeasure method allows to adapt display when screen size changes.
     *
     * @param widthMeasureSpec : Width size
     * @param heightMeasureSpec : Height size
     * @see Graph#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xwidth = getMeasuredWidth();

        setMeasuredDimension(xwidth, xwidth * 2 / 3);
        invalidate();
    }
}
