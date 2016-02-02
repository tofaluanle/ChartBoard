package cn.manjuu.chatboard.main;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther 宋疆疆
 * @date 2016/2/1.
 */
public class MyBehavior extends AppBarLayout.ScrollingViewBehavior {

    int mBottom;
    boolean init;

    public MyBehavior() {
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        System.out.println("onLayoutChild    mBottom");
        boolean b = super.onLayoutChild(parent, child, layoutDirection);

        ViewGroup vg = (ViewGroup) child;
        View v = vg.getChildAt(1);

        v.offsetTopAndBottom(-mBottom);

        return b;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewChanged(parent, child, dependency);

        int bottom = dependency.getBottom();
        if (!init) {
            mBottom = bottom;
            init = true;
        }
        int dx = bottom - mBottom;
        System.out.println("mBottom: " + mBottom + ", bottom: " + bottom + ", dx: " + dx);
        mBottom = bottom;

        ViewGroup vg = (ViewGroup) child;
        View v = vg.getChildAt(1);

//        if (v.getBottom() - dx >= vg.getHeight() - dependency.getBottom()) {//越界判断
            v.offsetTopAndBottom(-dx);
//        }

        return false;
    }

}
