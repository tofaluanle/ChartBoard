package cn.manjuu.chatboard.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.manjuu.chatboard.R;
import cn.manjuu.chatboard.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    MyFragmentAdapter mFragmentAdapter;
    Toolbar toolbar;
    PopupWindow mPopupWindow;
    boolean isShow;
    View btn_menu;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.btn_return).setOnClickListener(this);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        initTabAndPager();
        initPopupWindow();
    }

    private void initTabAndPager() {
        tabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.main_tab_item, null);
            tab.setCustomView(view);
            ImageView im = (ImageView) view.getChildAt(0);
            TextView tv = (TextView) view.getChildAt(1);
            im.setImageResource(R.drawable.imageview_selector);
            tv.setText("tab" + i);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                drawer.setDragGlobalEnable(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        System.out.println(id);
        switch (id) {
            case R.id.nav_manage:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                drawer.openDrawer(GravityCompat.START);
                break;
            case KeyEvent.KEYCODE_MENU:
                if (isShow) {
                    isShow = false;
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                if (!isShow) {
                    showPopupWindow();
                } else {
                    isShow = false;
                }
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initPopupWindow() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View sub_view = mLayoutInflater.inflate(R.layout.main_menu_ctg, null);
        mPopupWindow = new PopupWindow(sub_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    /*设置触摸外面时消失*/
        mPopupWindow.setOutsideTouchable(true);
    /*设置系统动画*/
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
    /*设置点击menu以外其他地方以及返回键退出*/
        mPopupWindow.setFocusable(true);

        /** 1.解决再次点击MENU键无反应问题
         *  2.sub_view是PopupWindow的子View
         */
        sub_view.setFocusableInTouchMode(true);
        sub_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_MENU) && (mPopupWindow.isShowing())) {
                    mPopupWindow.dismiss();// 这里写明模拟menu的PopupWindow退出就行
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_menu:
                showPopupWindow();
                break;
        }
    }

    private void showPopupWindow() {
        if (!mPopupWindow.isShowing()) {
            isShow = true;
            View p = (View) mViewPager.getParent();
            int[] loc = new int[2];
            p.getLocationInWindow(loc);
            mPopupWindow.showAtLocation(p, Gravity.RIGHT | Gravity.TOP, 0, loc[1]);
        } else {
            isShow = false;
        }
    }

}
