package cn.manjuu.chatboard.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.manjuu.chatboard.R;
import cn.manjuu.chatboard.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyAdapter.OnItemClickListener {

    private DrawerLayout drawer;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    int index;
    Handler mHandler = new Handler();
    private float mTouchX;
    private float mTouchY;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addData("haha" + index++, 1);
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeData(1);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("onTouch");
                MotionEvent e = event;
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchX = e.getRawX();
                        mTouchY = e.getRawY();
                        System.out.println("mTouchX: " + mTouchX);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mTouchX == 0) {
                            mTouchX = e.getRawX();
                            break;
                        }
                        float x = e.getRawX();
                        float y = e.getRawY();
                        float dx = Math.abs(x - mTouchX);
                        float dy = Math.abs(y - mTouchY);
                        System.out.println("mTouchX: " + mTouchX + ", x: " + x + ", dx: " + dx);
                        if (dx > 100 && !drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.openDrawer(GravityCompat.START);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouchX = 0;
                        break;
                }
                return false;
            }
        });

        initSwip();
    }

    private void initSwip() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swiperefreshlayout);
//        swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
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
            mAdapter.addData("haha" + index++, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
