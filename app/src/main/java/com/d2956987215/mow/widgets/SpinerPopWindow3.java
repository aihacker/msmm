package com.d2956987215.mow.widgets;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.adapter.ShouYeAdapter;
import com.d2956987215.mow.eventbus.AnimBottomBar;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.androidannotations.annotations.App;
import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class SpinerPopWindow3 extends PopupWindow {
    private LayoutInflater inflater;


    CallBack callback;
    private Activity context;
    private RecyclerView rl_sheng;
    private View hideView;
    private List<ShouTitleResponse.DataBean.CateBean> cateBean;
    private AlphaAnimation showanimation;
    private AlphaAnimation hideanimation;
    private View view;
    private double viewHeight = -1;
    private TextView tv_more;
    private NestedScrollView scroll_view;


    public SpinerPopWindow3(Activity context, List<ShouTitleResponse.DataBean.CateBean> cateBean, CallBack callback) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.callback = callback;
        this.context = context;
        this.cateBean = cateBean;
        initAnimation();
        init();
        shuju();

    }

    public SpinerPopWindow3(Activity context, View hideView, List<ShouTitleResponse.DataBean.CateBean> cateBean, CallBack callback) {
        super(context);
        if (context == null)
            inflater = LayoutInflater.from(APP.getApplication());
        else
            inflater = LayoutInflater.from(context);
        this.callback = callback;
        this.context = context;
        this.cateBean = cateBean;
        this.hideView = hideView;
        initAnimation();
        init();
        shuju();
    }

    private ShouYeAdapter shouYeAdapter;

    protected void shuju() {

        shouYeAdapter = new ShouYeAdapter(R.layout.adapter_feilei_right, cateBean);
        rl_sheng.setLayoutManager(new GridLayoutManager(context, 4));
        rl_sheng.setHasFixedSize(true);
        rl_sheng.setNestedScrollingEnabled(false);
        rl_sheng.setAdapter(shouYeAdapter);
        shouYeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                callback.OK(position);


            }
        });


    }


    private void init() {
        view = inflater.inflate(R.layout.window_more, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        rl_sheng = (RecyclerView) view.findViewById(R.id.rl_sheng);
        tv_more = (TextView) view.findViewById(R.id.tv_more);
        scroll_view = (NestedScrollView) view.findViewById(R.id.scroll_view);


    }

    public interface CallBack {
        public void OK(int position);

        public void NO();
    }


    //解决安卓8.0和7.0显示问题：
    public void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
//        if (Build.VERSION.SDK_INT >= 24) {
//            Rect visibleFrame = new Rect();
//            anchor.getGlobalVisibleRect(visibleFrame);
//            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
//            pw.setHeight(height);
//            pw.showAsDropDown(anchor, xoff, yoff);
//        } else {
//            pw.showAsDropDown(anchor, xoff, yoff);
//        }
        super.showAsDropDown(anchor, xoff, yoff);
        view.measure(0, 0);
        if (viewHeight == -1) {
            viewHeight = view.getMeasuredHeight();
        }
        try {
            int h = rl_sheng.getChildAt(0).getMeasuredHeight() * 3 + tv_more.getMeasuredHeight() + DensityUtil.dp2px(30);
            if (viewHeight > h) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = h;
                view.setLayoutParams(params);
            /*ViewGroup.LayoutParams paramsl = rl_sheng.getLayoutParams();
            paramsl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            rl_sheng.setLayoutParams(paramsl);*/
            }
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            if (hideView != null) {
                hideView.startAnimation(showanimation);
                EventBus.getDefault().post(new AnimBottomBar(true));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public interface onClickQuxiaoListener {
        void xuanqu();

    }

    private onClickQuxiaoListener quxiaoListener;

    public void setClickButtonListener(onClickQuxiaoListener quxiaoListener) {
        this.quxiaoListener = quxiaoListener;
    }

    private class ViewHolder {
        private TextView tvName;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (hideView != null) {
            EventBus.getDefault().post(new AnimBottomBar(false));
            hideView.startAnimation(hideanimation);
        }
    }

    void initAnimation() {
        showanimation = new AlphaAnimation(1f, 0.4f);
        showanimation.setDuration(300);
        showanimation.setFillAfter(true);
        hideanimation = new AlphaAnimation(0.4f, 1f);
        hideanimation.setDuration(300);
        hideanimation.setFillAfter(true);
    }
}