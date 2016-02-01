package cn.manjuu.chatboard.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
    private class TempFragment extends BaseFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.item, null);
        }

        @Override
        protected void findView() {
            ViewGroup view = (ViewGroup) getView();
            TextView tv = (TextView) view.getChildAt(0);
            tv.setText(this.toString());
        }

        @Override
        protected void registerListener() {

        }

        @Override
        protected void init() {

        }
    }
}
