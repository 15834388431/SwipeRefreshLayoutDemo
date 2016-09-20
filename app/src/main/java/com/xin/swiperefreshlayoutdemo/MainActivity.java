package com.xin.swiperefreshlayoutdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mLayout;
    private List<String> data = new ArrayList<String>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        initDatas();
        initRecyclerView();
        initRefreshLayout();

    }

    private void initRecyclerView() {
        myAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(myAdapter);
        //ListView样式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

//        mRecyclerView.se
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, String city) {
                Toast.makeText(MainActivity.this, "city:" + city + ",position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
//        //GridView样式
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        //瀑布流样式
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initRefreshLayout() {
        mLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setDistanceToTriggerSync(100);
        mLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.item_press));
        mLayout.setSize(SwipeRefreshLayout.LARGE);
        mLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            myAdapter.addData(i,"new City:"+i);
                        }
                        myAdapter.notifyItemRangeChanged(0,10);
                        mRecyclerView.scrollToPosition(0);
                        mLayout.setRefreshing(false);
//                        mLayout.isRefreshing();
                    }
                }, 3000);
            }
        });
    }

    private void initDatas() {
        data.add("北京");
        data.add("上海");
        data.add("广州");
        data.add("天津");
        data.add("香港");
        data.add("澳门");
        data.add("台湾");
        data.add("重庆");
        data.add("昆明");
        data.add("成都");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                myAdapter.addData(0, "new City");
                break;
            case R.id.id_action_delete:
                myAdapter.removeData(1);
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.id_action_staview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return true;
    }
}
