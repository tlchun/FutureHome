package com.goldze.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldze.base.R;


public class ItemView extends RelativeLayout {

    private ImageView itemLeft;
    private TextView itemText;
    private ImageView itemRight;
    private TextView itemRightText;

    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        //获取左边图片资源
        int item_left_img_res = ta.getResourceId(R.styleable.ItemView_item_left_img_res, -1);
        if (item_left_img_res != -1) {
            setItemLeftRes(item_left_img_res);
        }
        //获取文本的信息
        String text = ta.getString(R.styleable.ItemView_android_text);
        setItemText(text);

        //设置右边文字
        String item_right_text = ta.getString(R.styleable.ItemView_item_right_text);
        if (!TextUtils.isEmpty(item_right_text)) {
            setItemRightText(item_right_text);
        }

        boolean item_right_visible = ta.getBoolean(R.styleable.ItemView_item_right_visible, false);
        if(item_right_visible) {
            itemRight.setVisibility(VISIBLE);
        }else{
            itemRight.setVisibility(GONE);
        }

        boolean item_left_visible = ta.getBoolean(R.styleable.ItemView_item_left_visible, false);
        if(item_left_visible) {
            itemLeft.setVisibility(VISIBLE);
        }else{
            itemLeft.setVisibility(GONE);
        }

        String item_left_text_color_string = ta.getString(R.styleable.ItemView_item_left_text_color_string);
        if (!TextUtils.isEmpty(item_left_text_color_string)) {
            setItemTextColor(Color.parseColor(item_left_text_color_string));
        }
        ta.recycle();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item, this);
        itemLeft = (ImageView) findViewById(R.id.item_left);
        itemText = (TextView) findViewById(R.id.item_text);
        itemRightText = (TextView) findViewById(R.id.item_right_text);
        itemRight = (ImageView) findViewById(R.id.item_right);
    }

    private  void setItemLeftRes(int res) {
        itemLeft.setBackgroundResource(res);
    }

    private  void setItemText(String text) {
        if (!TextUtils.isEmpty(text)) {
            itemText.setText(text);
        }
    }

    public void setItemRightText(String text) {
        itemRightText.setText(text);
    }

    public void setItemRight(int res) {
        itemRight.setBackgroundResource(res);
    }


    private  void setItemTextColor(int color) {
        itemText.setTextColor(ColorStateList.valueOf(color));
    }

    public void setItemTextColor(ColorStateList colors) {
        itemText.setTextColor(colors);
    }

}