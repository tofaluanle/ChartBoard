package cn.manjuu.chatboard.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.manjuu.chatboard.Logger;
import cn.manjuu.chatboard.R;
import cn.manjuu.chatboard.base.BaseFragment;
import cn.manjuu.chatboard.main.widget.SwipeRefreshLayout;

/**
 * @auther 宋疆疆
 * @date 2016/2/1.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> mFrags;
    int mPageIndex;
    private FragmentManager mFm;

    public void setPageIndex(int pageIndex) {
        if (mPageIndex != pageIndex) {
//            TempFragment fragment = (TempFragment) mFrags.get(pageIndex);
//            fragment.onHide();
            this.mPageIndex = pageIndex;
        }
    }

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFm = fm;
        init();
    }

    private void init() {
        mFrags = new ArrayList<>();
//        mFrags.add(new TempFragment());
//        mFrags.add(new TempFragment());
//        mFrags.add(new TempFragment());
//        mFrags.add(new TempFragment());
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("getItem: " + position);
//        return mFrags.get(position);
        return new TempFragment();
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

    Fragment mDelFrg;

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        System.out.println("destroyItem: " + position);
        mDelFrg = (Fragment) object;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        System.out.println("finishUpdate");
        if (mDelFrg != null) {
            FragmentTransaction t = mFm.beginTransaction();
            t.remove(mDelFrg);
            t.commitAllowingStateLoss();
        }
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
        Handler mHandler = new MyHandler();

        class MyHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println(msg.getTarget());
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
//            return inflater.inflate(R.layout.item, null);
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
            if (mRecyclerView == null) {
                return;
            }
            mLayoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter();
            mAdapter.setListener(this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        private void initSwip() {
            View view = getView();
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swiperefreshlayout);
            if (swipeRefreshLayout == null) {
                return;
            }
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
                    }, 300 * 1000);
                    mHandler.sendEmptyMessageDelayed(0, 300 * 1000);
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

        public void onHide() {
            Logger.v(this + "   onHide ");
            mHandler.removeCallbacksAndMessages(null);
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();

            mHandler.removeCallbacksAndMessages(null);
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.clearAnimation();
            ViewGroup view = (ViewGroup) getView();
            view.removeAllViews();
        }


    }
}
