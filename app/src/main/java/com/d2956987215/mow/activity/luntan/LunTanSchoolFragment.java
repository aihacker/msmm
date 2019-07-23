package com.d2956987215.mow.activity.luntan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.adapter.SchoolListAdapter;
import com.d2956987215.mow.adapter.SchoolTitleAdpter;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.LunTanSchoolCateResponse;
import com.d2956987215.mow.rxjava.response.SchoolListResponse;
import com.d2956987215.mow.widgets.NoScrollGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LunTanSchoolFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.gv_title)
    NoScrollGridView mGvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private int p = 1;
    private SchoolListAdapter schoolListAdapter;
    private List<LunTanSchoolCateResponse.DataBean> titleList;

    public LunTanSchoolFragment() {
    }

    @Override
    public void onRefresh() {
        p = 1;
        listtitle();
    }

    @Override
    public void show(Object obj) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_luntanschool, container, false);
        unbinder = ButterKnife.bind(this, view);
        listtitle();
        mRefreshLayout.setOnRefreshListener(this);
        mGvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Act_SchoolList.class);
                intent.putExtra("cate_id", titleList.get(i).getId());
                intent.putExtra("titleName", titleList.get(i).getName());
                startActivity(intent);
            }
        });

        return view;
    }

    public void listtitle() {
        new Request<LunTanSchoolCateResponse>().request(RxJavaUtil.xApi1().SchoolCate(), "论坛头列表", getActivity(), false, new Result<LunTanSchoolCateResponse>() {
            @Override
            public void get(final LunTanSchoolCateResponse response) {
                if (response.getData() != null && response.getData().size() > 0) {
                    titleList = response.getData();
                    mGvTitle.setAdapter(new SchoolTitleAdpter(getActivity(), titleList));
                    articlelist();
                }
            }
        });

    }

    private void showRefresh() {
        try {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void articlelist() {
        Map<String, String> map = new HashMap<>();
        map.put("page", p + "");
        map.put("page_size", "20");
        new Request<SchoolListResponse>().request(RxJavaUtil.xApi1().SchoolList(map), "每日爆款列表", getActivity(), false, new Result<SchoolListResponse>() {
            @Override
            public void get(SchoolListResponse response) {
                if (p == 1) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    schoolListAdapter.loadMoreComplete();
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (p == 1) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        schoolListAdapter = new SchoolListAdapter(R.layout.item_schoollist, response.getData());
                        mRecyclerView.setAdapter(schoolListAdapter);
                    } else {
                        schoolListAdapter.addData(response.getData());
                    }

                    if (response.getData().size() == 20) {
                        schoolListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                p++;
                                articlelist();
                            }
                        }, mRecyclerView);
                    } else {
                        schoolListAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.footer_layout, null));
                    }
                } else {
                    if (p == 1) {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onBackErrorMessage(SchoolListResponse response) {
                if (p == 1) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    schoolListAdapter.loadMoreComplete();
                }
                //没有数据
                mRecyclerView.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
