package cn.manjuu.chatboard.setting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.manjuu.chatboard.R;

/**
 * @auther 宋疆疆
 * @date 2016/1/28.
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout);
//        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab3---------------"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab4---------"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab5---------"));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        MyAdapter adapter = new MyAdapter();
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item, null);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tab = "tab";
            for (int i = 0; i < position; i++) {
                tab += "---";
            }
            return tab;
        }
    }
}
