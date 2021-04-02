package com.goldze.home.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.goldze.home.R;
import com.goldze.home.ui.model.SheQuModel;

import java.util.List;

public class SheQuAdapter extends RecyclerView.Adapter<SheQuAdapter.MyHolder> {

    private List<SheQuModel> mList;//数据源

    private ViewGroup viewGroup;

    public SheQuAdapter(List<SheQuModel> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        viewGroup = parent;
        //将我们自定义的item布局R.layout.tv_content转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shequ, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, @SuppressLint("RecyclerView") final int position) {
//        if (mList.get(position).getImage() != null)
//            holder.iv_pic.setImageURI(Uri.parse(mList.get(position).getImage()));
//        holder.tv_title.setText(mList.get(position).getUserId());
//        holder.tv_phone.setText(DateUtil.timeStamp2Date(mList.get(position).getCreateTime()));
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView iv_pic;
        TextView tv_title;
        TextView tv_phone;

        public MyHolder(View itemView) {
            super(itemView);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_phone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
