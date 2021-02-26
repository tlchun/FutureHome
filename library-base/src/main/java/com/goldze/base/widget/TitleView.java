package com.goldze.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldze.base.R;
import com.goldze.base.utils.DensityUtils;

import androidx.core.content.ContextCompat;


public class TitleView extends RelativeLayout {

    private Context mContext;

    private RelativeLayout rlLeft;
    private RelativeLayout rlSearch;
    private RelativeLayout rlRight;

    private ImageView titleLeft;
    private TextView titleText;
    private ImageView titleSearch;
    private ImageView titleRight;
    private TextView tvRight;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_title, this);

        rlLeft = (RelativeLayout) findViewById(R.id.rl_left);
        rlSearch = (RelativeLayout) findViewById(R.id.rl_search);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        rlLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) TitleView.this.getContext()).finish();
            }
        });

        titleLeft = (ImageView) findViewById(R.id.title_left);
        titleText = (TextView) findViewById(R.id.title_text);
        titleSearch = (ImageView) findViewById(R.id.title_search);
        titleRight = (ImageView) findViewById(R.id.title_right);
        tvRight = (TextView) findViewById(R.id.tv_right);

    }

    public void setBackgroundColor(int color) {
        rlLeft.setBackgroundColor(color);
        rlRight.setBackgroundColor(color);
        titleText.setBackgroundColor(color);
    }

    public void setBackgroundTrans() {
        rlLeft.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        rlRight.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        titleText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
    }

    public void setTitleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleText.setText(text);
        } else {
            titleText.setText("");
        }
    }

    public void setTitleTextColor(String color) {
        titleText.setTextColor(color.startsWith("#") ? Color.parseColor(color) : Color.parseColor("#" + color));
    }

    public void setLeftInVisible() {
        titleLeft.setVisibility(View.INVISIBLE);
        rlLeft.setClickable(false);
    }

    public void setLeftOnClickListener(OnClickListener l) {
        if (titleLeft.getVisibility() == View.INVISIBLE) {
            titleLeft.setVisibility(View.VISIBLE);
            rlLeft.setClickable(true);
        }
        rlLeft.setOnClickListener(l);
    }

    public void setLeftOnClickListener(OnClickListener l, int resId) {
        titleLeft.setBackgroundResource(resId);
        setLeftOnClickListener(l);
    }

    public void setSearchOnClickListener(OnClickListener l, int resId) {
        titleText.setPadding(DensityUtils.dip2px(mContext, 50), 0, 0, 0);
        rlSearch.setVisibility(View.VISIBLE);
        titleSearch.setBackgroundResource(resId);
        setSearchOnClickListener(l);
    }

    private void setSearchOnClickListener(OnClickListener l) {
        if (rlSearch.getVisibility() == View.GONE) {
            rlSearch.setVisibility(View.VISIBLE);
            rlSearch.setClickable(true);
        }
        rlSearch.setOnClickListener(l);
    }

    public void setRightInVisible() {
        titleRight.setVisibility(View.INVISIBLE);
        rlRight.setClickable(false);
    }

    public void setRightVisible(int visible) {
        titleRight.setVisibility(visible);
        rlRight.setClickable(View.VISIBLE == visible);
    }

    public void setLeftVisible(int visible) {
        titleLeft.setVisibility(visible);
        rlLeft.setClickable(View.VISIBLE == visible);
    }

    private void setRightOnClickListener(OnClickListener l) {
        if (titleRight.getVisibility() == View.INVISIBLE) {
            titleRight.setVisibility(View.VISIBLE);
            rlRight.setClickable(true);
        }
        rlRight.setOnClickListener(l);
    }

    public void setRightOnClickListener(OnClickListener l, int resId) {
        titleRight.setBackgroundResource(resId);
        setRightOnClickListener(l);
    }

    public void setRightText(String text, OnClickListener l) {
        titleRight.setVisibility(View.INVISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(text);
        rlRight.setClickable(true);
        rlRight.setOnClickListener(l);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public TextView getTitleTextView() {
        return titleText;
    }
}
