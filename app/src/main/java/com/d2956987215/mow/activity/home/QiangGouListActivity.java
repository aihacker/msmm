package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.MyPagerAdapter;
import com.d2956987215.mow.adapter.TimeListAdapter;
import com.d2956987215.mow.adapter.TimeListHotAdapter;
import com.d2956987215.mow.frament.QiangGoFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.QiagGouListResponse;
import com.d2956987215.mow.rxjava.response.TimeListResponse;
import com.d2956987215.mow.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

public class QiangGouListActivity extends BaseActivity {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;

    @BindView(R.id.rl_time)
    RecyclerView rl_time;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    public MyPagerAdapter myPagerAdapter;
    public List<Fragment> fragmentList = new ArrayList<>();


    private StatusLayoutManager statusLayoutManager;
    private String mStatus;


    @BindView(R.id.view)
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(recycler_hot_product).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        huoqutime();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mView.setVisibility(View.VISIBLE);
            setStatusBarUpperAPI21();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperAPI19();
            mView.setVisibility(View.GONE);
        }

    }

    // 5.0版本以上
    private void setStatusBarUpperAPI21() {
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }


    // 4.4 - 5.0版本
    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        //设置悬浮透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight();
        int statusColor = getResources().getColor(R.color.transparent);

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null &&
                mTopView.getLayoutParams().height == statusBarHeight) {
            mTopView.setBackgroundColor(statusColor);
            return;
        }

        //制造一个和状态栏等尺寸的 View
        mTopView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(statusColor);
        //将view添加到第一个位置
        mContentView.addView(mTopView, 0, lp);

    }


    private TimeListAdapter timeadapter;

    private void huoqutime() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<TimeListResponse>().request(RxJavaUtil.xApi().timelist(map), "时间列表", this, false, new Result<TimeListResponse>() {


            @Override
            public void get(final TimeListResponse response) {

                timeadapter = new TimeListAdapter(R.layout.adapter_time, response.getData());
                int spanCount = 1; // 只显示一行
                int seceltPosition = 0;
                for (int i = 0; i < response.getData().size(); i++) {                //处理第一个数组,list里面的值为1,2,3,4
                    TimeListResponse.DataBean str = response.getData().get(i);
                    if (str.getStatus().equals("疯抢中")) {
                        str.setSelete(true);
                        seceltPosition = i;
                    }
                    QiangGoFragment qiangGoFragment = new QiangGoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("time_id", str.getId());
                    bundle.putBoolean("isStart", str.isSelete());
                    qiangGoFragment.setArguments(bundle);
                    fragmentList.add(qiangGoFragment);
                }

                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
                rl_time.setLayoutManager(layoutManager);
                rl_time.setHasFixedSize(true);
                rl_time.setNestedScrollingEnabled(false);
                rl_time.setAdapter(timeadapter);

                timeadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        for (int i = 0; i < response.getData().size(); i++) {
//                            if (i == position) {
//                                response.getData().get(i).setSelete(true);
//                            } else {
//                                response.getData().get(i).setSelete(false);
//                            }
//                        }
                        viewPager.setCurrentItem(position);
//                        if (timeadapter != null) {
//                            timeadapter.notifyDataSetChanged();
//                        }
                        mStatus = timeadapter.getItem(position).getStatus();
                    }
                });
                MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
                viewPager.setAdapter(myPagerAdapter);
                viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
                if (fragmentList.size() > 0) {
                    viewPager.setCurrentItem(seceltPosition);
                }
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < response.getData().size(); i++) {
                            if (i == position) {
                                response.getData().get(i).setSelete(true);
                            } else {
                                response.getData().get(i).setSelete(false);
                            }
                            if (timeadapter != null) {
                                timeadapter.notifyDataSetChanged();
                            }
                            rl_time.scrollToPosition(position);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
        });
    }

    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }

    private void huoqu(String time_id) {
        Map<String, String> map = new HashMap<>();
        map.put("time_id", time_id);
        if (User.uid() == -1) {
            map.put("user_id", "0");
        } else {
            map.put("user_id", User.uid() + "");
        }

        new Request<QiagGouListResponse>().request(RxJavaUtil.xApi().qianggoulist(map), "抢购列表", this, false, new Result<QiagGouListResponse>() {
            @Override
            public void get(QiagGouListResponse response) {
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<QiagGouListResponse.DataBean> list = response.getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    statusLayoutManager.showEmptyLayout();

                }

            }
        });

    }


    private TimeListHotAdapter hotAdapter;

    private void initHotRecycler1(final List<QiagGouListResponse.DataBean> list) {
        hotAdapter = new TimeListHotAdapter(R.layout.adapter_qianggou, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setHasFixedSize(true);
        recycler_hot_product.setNestedScrollingEnabled(false);
        recycler_hot_product.setAdapter(hotAdapter);
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(QiangGouListActivity.this, TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getGoodsID() + "");
                intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");
                startActivity(intent);


            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_qianggoulist;
    }

    @Override
    public void show(Object obj) {

    }
}
