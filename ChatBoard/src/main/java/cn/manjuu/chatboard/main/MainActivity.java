package cn.manjuu.chatboard.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.manjuu.chatboard.R;
import cn.manjuu.chatboard.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    int index;
    Handler mHandler = new Handler();
    private float mTouchX;
    private float mTouchY;
    NavigationView navigationView;
    TabLayout tabLayout;
    MyFragmentAdapter mFragmentAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAdapter.addData("haha" + index++, 1);
                mFragmentAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.btn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAdapter.removeData(1);
            }
        });

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
        ViewPager mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
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
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("menu");// 必须创建一项
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        if (mPopupWindow != null) {
//            if (!mPopupWindow.isShowing()) {
//            /*最重要的一步：弹出显示   在指定的位置(parent)  最后两个参数 是相对于 x / y 轴的坐标*/
//                mPopupWindow.showAtLocation(toolbar, Gravity.BOTTOM, 0, 0);
//            }
//        }
//        return false;// 返回为true 则显示系统menu
//    }
    PopupWindow mPopupWindow;

    private void initPopupWindow() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    /*设置显示menu布局   view子VIEW*/
        View sub_view = mLayoutInflater.inflate(R.layout.main_menu_ctg, null);
    /*第一个参数弹出显示view  后两个是窗口大小*/
        mPopupWindow = new PopupWindow(sub_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    /*设置背景显示*/
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
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
}
