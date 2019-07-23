package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.util.AnimatorEffect;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText etContent;

    private MinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected String title() {
        return getString(R.string.feedback);
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({ R.id.bt_submit})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_submit:
                presenter.feedBack(etContent.getText().toString());
                break;
        }
    }



}
