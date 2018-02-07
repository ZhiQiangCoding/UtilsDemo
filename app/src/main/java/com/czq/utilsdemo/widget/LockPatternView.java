package com.czq.utilsdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.czq.utilsdemo.R;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/03/08
 *     version: 1.0
 *     desc   : 九宫格绘制图案锁
 * </pre>
 */
public class LockPatternView extends View {
    //九宫格点的数组
    private Point[][] points = new Point[3][3];
    private boolean isInit;//是否初始化点

    private int width, height, offsetsX, offsetsY;
    //画笔
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap pointNormal, pointPressed, pointError, linePressed, lineError;

    public LockPatternView(Context context) {
        super(context, null);
    }

    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写ondraw
     *
     * @param canvas 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initPoints();//初始化图案的点
        }
        canvasPoints(canvas);//绘制点到画布
    }

    /**
     * 初始化点
     */
    private void initPoints() {
        //获取布局的宽高
        width = getWidth();
        height = getHeight();

        //判断屏幕横竖屏状态
        if (width > height) {//横屏状态
            offsetsX = (width - height) / 2;
            width = height;
        } else {//竖屏
            offsetsY = (height - width) / 2;
            height = width;
        }

        //计算点的位置
        points[0][0] = new Point(offsetsX + width / 4, offsetsY + width / 4);
        points[0][1] = new Point(offsetsX + width / 2, offsetsY + width / 4);
        points[0][2] = new Point(offsetsX + width - width / 4, offsetsY + width / 4);
        points[1][0] = new Point(offsetsX + width / 4, offsetsY + width / 2);
        points[1][1] = new Point(offsetsX + width / 2, offsetsY + width / 2);
        points[1][2] = new Point(offsetsX + width - width / 4, offsetsY + width / 2);
        points[2][0] = new Point(offsetsX + width / 4, offsetsY + width - width / 4);
        points[2][1] = new Point(offsetsX + width / 2, offsetsY + width - width / 4);
        points[2][2] = new Point(offsetsX + width - width / 4, offsetsY + width - width / 4);
        int color = getResources().getColor(R.color.text_color);
        //添加图案资源
//        pointNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.point_normal);
//        pointPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.point_pressed);
//        pointError = BitmapFactory.decodeResource(getResources(), R.mipmap.point_error);
        pointNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        pointPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        pointError = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        //  连线图案
//        linePressed = BitmapFactory.decodeResource(getResources(), R.mipmap.point_pressed);
//        lineError = BitmapFactory.decodeResource(getResources(), R.mipmap.point_error);
        linePressed = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        lineError = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    /**
     * 绘制点到画布
     *
     * @param canvas 画布
     */
    private void canvasPoints(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if (point.state == Point.STATE_NORMAL) {//正常状态
                    paint.setColor(Color.GREEN);
                    //调整画布的位置，因为在点的右下边开始画的图案，图案的中心并没有和点对齐，将图案向上和左偏移图案的半径（pointNormal.getwidth()/2）
                    canvas.drawBitmap(pointNormal, point.x - pointNormal.getWidth() / 2, point.y - pointNormal.getWidth() / 2, paint);
                } else if (point.state == Point.STATE_PRESSED) {//选中状态
                    canvas.drawBitmap(pointPressed, point.x, point.y, paint);
                } else {//错误状态
                    canvas.drawBitmap(pointError, point.x, point.y, paint);
                }
            }
        }
    }

    /**
     * 自定义的点
     */
    public static class Point {
        //正常状态
        public static int STATE_NORMAL = 0;
        //        选中状态
        public static int STATE_PRESSED = 1;
        //        错误状态
        public static int STATE_ERROR = 2;
        public float x, y;
        public int index = 0, state = 0;

        public Point() {

        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
