package com.goldze.work;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.goldze.base.utils.DateUtil;
import com.goldze.work.ui.CardRecordModel;
import com.goldze.work.ui.FaceRecordModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyCardRecordAdapter extends RecyclerView.Adapter<MyCardRecordAdapter.MyHolder> {

    private List<CardRecordModel> mList;//数据源

    private ViewGroup viewGroup;

    public MyCardRecordAdapter(List<CardRecordModel> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        viewGroup = parent;
        //将我们自定义的item布局R.layout.tv_content转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_record, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_card_no.setText(mList.get(position).getCardNo() + "");
        holder.tv_userid.setText(mList.get(position).getUserId());
        holder.tv_time.setText(DateUtil.timeStamp2Date(mList.get(position).getCreateTime()));
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
        TextView tv_card_no;
        TextView tv_userid;
        TextView tv_time;

        public MyHolder(View itemView) {
            super(itemView);
            tv_card_no = itemView.findViewById(R.id.tv_card_no);
            tv_userid = itemView.findViewById(R.id.tv_userid);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
