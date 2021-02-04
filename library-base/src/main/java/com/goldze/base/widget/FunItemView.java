package com.goldze.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldze.base.R;


public class FunItemView extends RelativeLayout {


    private ImageView item_image;
    private TextView item_text;

    public FunItemView(Context context) {
        super(context);
        init(context);
    }

    public FunItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FunItemView);
        //获取左边图片资源
        int item_left_img_res = ta.getResourceId(R.styleable.FunItemView_item_img_res, -1);
        if (item_left_img_res != -1) {
            setItemLeftRes(item_left_img_res);
        }
        //获取文本的信息
        String text = ta.getString(R.styleable.FunItemView_item_text);
        setItemText(text);

        ta.recycle();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fun_view_item, this);
        item_image = (ImageView) findViewById(R.id.item_image);
        item_text = (TextView) findViewById(R.id.item_text);
    }

    private  void setItemLeftRes(int res) {
        item_image.setBackgroundResource(res);
    }

    private  void setItemText(String text) {
        if (!TextUtils.isEmpty(text)) {
            item_text.setText(text);
        }
    }

    private  void setItemTextColor(int color) {
        item_text.setTextColor(ColorStateList.valueOf(color));
    }

    public void setItemTextColor(ColorStateList colors) {
        item_text.setTextColor(colors);
    }

}
