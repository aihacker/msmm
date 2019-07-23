package com.d2956987215.mow.frament;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.ErJiDetailActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.HomeHotAdapter;
import com.d2956987215.mow.adapter.ShouYeErJiAdapter;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.rxjava.response.ShouYeErjiResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.MyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTwoFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rl_fenlei)
    RecyclerView rl_fenlei;
    @BindView(R.id.iv_iv_jiage)
    ImageView iv_iv_jiage;

    @BindView(R.id.id_appbarlayout)
    AppBarLayout idAppbarlayout;

    Unbinder unbinder;
    private StatusLayoutManager statusLayoutManager;
    private HomeHotAdapter hotAdapter;
    private String type = "1";

    private List<HomeListResponse.DataBeanX.DataBean> goodlist = new ArrayList<>();
    private int p = 1;
    private ShouTitleResponse.DataBean.CateBean dataBean;
    private AnimationDrawable animationDrawable;
    private String order = "";
    private String sort = "desc";

    public void setType(ShouTitleResponse.DataBean.CateBean dataBean) {
        this.dataBean = dataBean;
    }

    public HomeTwoFragment() {
        // Required empty public constructor
    }

    private boolean isVisible;
    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        //这里 初始化view的各控件 数据
        isPrepared = true;
        unbinder = ButterKnife.bind(this, view);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnLoadmoreListener(this);
        EventBus.getDefault().register(this);
//        refreshLayout.setHeaderTriggerRate(1 / 2);
//        refreshLayout.setFooterTriggerRate(1 / 2);
//        refreshLayout.setEnableFooterTranslationContent(false);
//        refreshLayout.setEnableHeaderTranslationContent(false);
//        refreshLayout.setEnableAutoLoadmore(true);
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerHotProduct).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();


        refreshLayout.setOnRefreshListener(this);

        //处理SwipeRefreshLayoutCoordinatorLayout
        idAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });

        return view;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        /**
         * 判断是否可见，或者 初始化view的各控件
         */
        if (!isVisible || !isPrepared) {
            return;
        }
        //可见 或者 控件初始化完成 就 加载数据
        huoqu();
        huoqubanner();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        /**
         * 判断是否可见
         */
        if (getUserVisibleHint()) {

            isVisible = true;
            //执行可见方法-初始化数据之类
            onVisible();

        } else {

            isVisible = false;
            //不可见
        }

    }

    private void onVisible() {
        lazyLoad();
    }


    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(getActivity());
        }
        return inflater.inflate(resource, null);
    }

    private ShouYeErJiAdapter shouyeerjiadapter;

    private void huoqubanner() {

        Map<String, String> map = new HashMap<>();
        map.put("cate_id", dataBean.getId() + "");
        new Request<ShouYeErjiResponse>().request(RxJavaUtil.xApi().geterjilist(map), "二级头列表", getActivity(), false, new Result<ShouYeErjiResponse>() {


            @Override
            public void get(ShouYeErjiResponse response) {
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    final List<ShouYeErjiResponse.DataBean> listold = response.getData();
                    final List<ShouYeErjiResponse.DataBean> listnew = new ArrayList<ShouYeErjiResponse.DataBean>();
                    if (listold.size() <= 8) {
                        listnew.addAll(listold);
                    } else {
                        for (int i = 0; i < 8; i++) {
                            listnew.add(listold.get(i));
                        }


                    }

                    shouyeerjiadapter = new ShouYeErJiAdapter(R.layout.adapter_feilei_right, listnew);
                    MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(getActivity(), 4);
                    gridLayoutManager.setScrollEnabled(false);
                    rl_fenlei.setLayoutManager(gridLayoutManager);
                    rl_fenlei.setHasFixedSize(true);
//                    rl_fenlei.setNestedScrollingEnabled(false);
                    rl_fenlei.setAdapter(shouyeerjiadapter);
                    shouyeerjiadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getActivity(), ErJiDetailActivity.class);
                            intent.putExtra("name", listnew.get(position).getName());
                            intent.putExtra("cate_id", listnew.get(position).getId() + "");
                            startActivity(intent);


                        }
                    });
                }


            }
        });


    }


    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("keyword", dataBean.getName());
        map.put("page", p + "");
        map.put("order", order);
        map.put("sort", sort);


        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }

        new Request<HomeListResponse>().request(RxJavaUtil.xApi().getsearchlist(map), "首页列表", getActivity(), false, new Result<HomeListResponse>() {


            @Override
            public void get(HomeListResponse response) {
                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<HomeListResponse.DataBeanX.DataBean> list = response.getData().getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    if (p == 1) {
                        if (hotAdapter != null) {
//                            goodlist.clear();
                            hotAdapter.getData().clear();
                        }

                        hotAdapter.notifyDataSetChanged();
                        statusLayoutManager.showEmptyLayout();
                    }
                }

            }

            @Override
            public void onBackErrorMessage(HomeListResponse response) {
                super.onBackErrorMessage(response);
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }
        });

    }


    private void initHotRecycler1(final List<HomeListResponse.DataBeanX.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new HomeHotAdapter(R.layout.adapter_hot_product, list);
            hotAdapter.setPreLoadNumber(3);
            int spanCount = 1; // 只显示一行
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerHotProduct.setLayoutManager(layoutManager);
//            recyclerHotProduct.setLayoutManager(layoutManager);
//            recyclerHotProduct.setHasFixedSize(true);
//            recyclerHotProduct.setItemViewCacheSize(10);
//            recyclerHotProduct.setItemAnimator(null);
//            hotAdapter.setHasStableIds(true);
//            recyclerHotProduct.setHasFixedSize(true);
//            recyclerHotProduct.setNestedScrollingEnabled(false);
            recyclerHotProduct.setAdapter(hotAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }
//        hotAdapter.notifyDataSetChanged();
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getItemid() + "");
                intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");

                intent.putExtra("type", type);
                startActivity(intent);


            }
        });

        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                huoqu();
            }
        }, recyclerHotProduct);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.rb_zong, R.id.rb_yongjin, R.id.rb_jiage, R.id.rb_xiaoshou})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        switch (view.getId()) {
            case R.id.rb_zong:
                iv_iv_jiage.setBackground(getActivity().getResources().getDrawable(R.mipmap.list_comp_default));
                order = "";
                p = 1;
                sort = "desc";
                huoqu();
                break;
            case R.id.rb_yongjin:
                iv_iv_jiage.setBackground(getActivity().getResources().getDrawable(R.mipmap.list_comp_default));
                order = "tkmoney";
                p = 1;
                sort = "desc";

                huoqu();
                break;
            case R.id.rb_jiage:
                order = "itemendprice";
                p = 1;

                if (sort.equals("desc")) {
                    iv_iv_jiage.setBackground(getActivity().getResources().getDrawable(R.mipmap.list_comp_top));
                    sort = "asc";

                    huoqu();
                } else {
                    iv_iv_jiage.setBackground(getActivity().getResources().getDrawable(R.mipmap.list_comp_down));
                    sort = "desc";

                    huoqu();
                }

                break;
            case R.id.rb_xiaoshou:
                p = 1;
                iv_iv_jiage.setBackground(getActivity().getResources().getDrawable(R.mipmap.list_comp_default));
                order = "itemsale";
                sort = "desc";
                huoqu();
                break;
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }


//    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        //presenter.banner();
//        if(animationDrawable ==null){
//            animationDrawable = (AnimationDrawable) animFresh.getDrawable();
//        }
//        animationDrawable.start();
//        p = 1;
//
//        huoqu();
//
//        // presenter.hotProduct(p);
//        refreshlayout.finishRefresh();
//    }


//    @Override
//    public void onLoadmore(RefreshLayout refreshlayout) {
//        p++;
//        huoqu();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshListData(RefreshListData refreshListData) {
        p = 1;
        huoqu();
    }

    @Override
    public void onRefresh() {
        p = 1;

        huoqu();
    }
}
