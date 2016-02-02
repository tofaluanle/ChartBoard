package cn.manjuu.chatboard.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.manjuu.chatboard.R;
import cn.manjuu.chatboard.base.BaseFragment;

/**
 * @auther 宋疆疆
 * @date 2016/2/1.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> mFrags;

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        mFrags = new ArrayList<>();
        mFrags.add(new TempFragment());
        mFrags.add(new TempFragment());
        mFrags.add(new TempFragment());
        mFrags.add(new TempFragment());
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("getItem: " + position);
        return mFrags.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        System.out.println("instantiateItem: " + position);
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tab = "tab";
        return tab;
    }

    @SuppressLint("ValidFragment")
    private class TempFragment extends BaseFragment implements MyAdapter.OnItemClickListener {

        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;
        SwipeRefreshLayout swipeRefreshLayout;
        MyAdapter mAdapter;
        Handler mHandler = new Handler();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, null);
        }

        @Override
        protected void findView() {
        }

        @Override
        protected void registerListener() {

        }

        @Override
        protected void init() {
            initRecyclerView();
            initSwip();
        }

        private void initRecyclerView() {
            View view = getView();
            mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter();
            mAdapter.setListener(this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setVisibility(View.GONE);
//            mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("onTouch");
//                    MotionEvent e = event;
//                    int action = e.getAction();
//                    switch (action) {
//                        case MotionEvent.ACTION_DOWN:
//                            mTouchX = e.getRawX();
//                            mTouchY = e.getRawY();
//                            System.out.println("mTouchX: " + mTouchX);
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            if (mTouchX == 0) {
//                                mTouchX = e.getRawX();
//                                break;
//                            }
//                            float x = e.getRawX();
//                            float y = e.getRawY();
//                            float dx = Math.abs(x - mTouchX);
//                            float dy = Math.abs(y - mTouchY);
//                            System.out.println("mTouchX: " + mTouchX + ", x: " + x + ", dx: " + dx);
//                            if (dx > 100 && !drawer.isDrawerOpen(GravityCompat.START)) {
//                                drawer.openDrawer(GravityCompat.START);
//                            }
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            mTouchX = 0;
//                            break;
//                    }
//                    return false;
//                }
//            });
        }

        private void initSwip() {
            View view = getView();
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swiperefreshlayout);
//        swipeRefreshLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//            swipeRefreshLayout.setEnabled(false);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);
                }
            });
        }

        @Override
        public void onItemClick(View v, int position) {
            System.out.println("position: " + position);
//        mAdapter.removeData(position);
            ViewGroup vg = (ViewGroup) v;
            TextView tv = (TextView) vg.getChildAt(0);
            mAdapter.setData(position, tv.getText() + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
            mAdapter.notifyItemChanged(position);
        }
    }
}
