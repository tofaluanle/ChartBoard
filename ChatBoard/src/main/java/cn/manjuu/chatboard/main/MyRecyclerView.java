package cn.manjuu.chatboard.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @auther 宋疆疆
 * @date 2016/1/27.
 */
public class MyRecyclerView extends RecyclerView {

    private static int TOUCH_SLOP;
    private float mX;
    private float mY;

    public MyRecyclerView(Context context) {
        super(context);
        init();
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        TOUCH_SLOP = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mX = e.getX();
                mY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = e.getX();
                float y = e.getY();
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx > dy && dx > TOUCH_SLOP) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }
}
