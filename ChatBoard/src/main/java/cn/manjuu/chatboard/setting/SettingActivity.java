package cn.manjuu.chatboard.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.manjuu.chatboard.R;

/**
 * @auther 宋疆疆
 * @date 2016/1/28.
 */
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        MyAdapter adapter = new MyAdapter();
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public static class MyAdapter extends PagerAdapter {

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
