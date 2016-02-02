package cn.manjuu.chatboard.main;

import android.content.Intent;
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

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
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
    }

    private void initTabAndPager() {
        tabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = LayoutInflater.from(this).inflate(R.layout.main_tab_item, null);
            tab.setCustomView(view);
            ViewGroup customView = (ViewGroup) tab.getCustomView();
            ImageView im = (ImageView) customView.getChildAt(0);
            TextView tv = (TextView) customView.getChildAt(1);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            mAdapter.addData("haha" + index++, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
