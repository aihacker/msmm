package com.d2956987215.mow.imageloader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.util.GlideRoundTransform;

/**
 * Created by lq on 2018/1/3.
 */

public class GlideCornerHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        Glide.with(context).load(data).transform(new GlideRoundTransform(context, 5)).into(imageView);
    }
}
