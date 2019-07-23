package com.d2956987215.mow.activity.product;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.activity.home.ErJiDetailActivity;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.activity.mine.XiaoXiActivity;
import com.d2956987215.mow.adapter.ShopMallLeftAdapter;
import com.d2956987215.mow.rxjava.Request;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.ErJiDetailActivity;
import com.d2956987215.mow.adapter.RightAdapter;
import com.d2956987215.mow.adapter.ShopMallLeftAdapter;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.FenLeiListResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.widgets.SpinerPopWindow3;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static android.R.id.list;
import static com.d2956987215.mow.R.id.right_menu;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements IShowData {


    @BindView(right_menu)
    RecyclerView rightMenu;
    @BindView(R.id.left_menu)
    RecyclerView rl_fenlei;
    @BindView(R.id.tv_leibie)
    TextView tv_leibie;

    Unbinder unbinder;


    public ProductFragment() {
        // Required empty public constructor
    }

    private LayoutInflater inflater;
    private StatusLayoutManager statusLayoutManager1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, view);

        statusLayoutManager1 = new StatusLayoutManager.Builder(rightMenu).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        huoqutitile();
        ;
        return view;
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(getActivity());
        }
        return inflater.inflate(resource, null);
    }

    private void huoqutitile() {
        new Request<FenLeiListResponse>().request(RxJavaUtil.xApi().getfeileilist(), "分类数据", getActivity(), false, new Result<FenLeiListResponse>() {
            @Override
            public void get(FenLeiListResponse response) {
                initleft(response.getData());

            }
        });

    }

    private String typeId;
    private ShopMallLeftAdapter mallLeftAdapter;
    private RightAdapter rightAdapter;

    private void initleft(final List<FenLeiListResponse.DataBean> list) {

        list.get(0).setSelete(true);
        typeId = list.get(0).getId() + "";
        tv_leibie.setText(list.get(0).getName());
        mallLeftAdapter = new ShopMallLeftAdapter(R.layout.shop_mall_left_item, list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rl_fenlei.setLayoutManager(layoutManager);
        rl_fenlei.setHasFixedSize(true);
        rl_fenlei.setNestedScrollingEnabled(false);

        rl_fenlei.setAdapter(mallLeftAdapter);
        // mallLeftAdapter.notifyDataSetChanged();

        rightdata(list.get(0).getSon());


        mallLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setSelete(true);
                        tv_leibie.setText(list.get(i).getName());
                        rightdata(list.get(i).getSon());
                    } else {
                        list.get(i).setSelete(false);
                    }
                }
                if (mallLeftAdapter != null) {
                    mallLeftAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void rightdata(final List<FenLeiListResponse.DataBean.SonBean> sList) {
        rightAdapter = new RightAdapter(R.layout.adapter_feilei_right, sList);
        rightMenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rightMenu.setHasFixedSize(true);
        rightMenu.setNestedScrollingEnabled(false);
        rightMenu.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), ErJiDetailActivity.class);
                intent.putExtra("cate_id", sList.get(position).getId() + "");
                intent.putExtra("name", sList.get(position).getName());
                startActivity(intent);

            }
        });
    }

    @OnClick({R.id.rl_search,R.id.tv_search})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        startActivity(new Intent(getActivity(), SearchActivity.class));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }


}
