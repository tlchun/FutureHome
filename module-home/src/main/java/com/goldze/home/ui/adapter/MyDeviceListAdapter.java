package com.goldze.home.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.goldze.home.R;
import com.goldze.home.ui.activity.KeyShareActivity;
import com.goldze.home.ui.model.DeviceModel;

import java.util.List;

public class MyDeviceListAdapter extends RecyclerView.Adapter<MyDeviceListAdapter.MyHolder> {

    private List<DeviceModel> mList;//数据源

    private ViewGroup viewGroup;

    public MyDeviceListAdapter(List<DeviceModel> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        viewGroup = parent;
        //将我们自定义的item布局R.layout.tv_content转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textView.setText(mList.get(position).getDeviceMac());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewGroup.getContext(), KeyShareActivity.class);
                intent.putExtra("deviceId", mList.get(position).getDeviceId());
                intent.putExtra("deviceMac", mList.get(position).getDeviceMac());
                viewGroup.getContext().startActivity(intent);
            }
        });
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

        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
