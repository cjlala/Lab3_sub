package cjlalala.lab3_sub;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.List;
import static android.support.v4.content.ContextCompat.startActivity;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder>{

    private List<Goods> mGoodsList;
    private OnItemLongClickListener mOnItemLongClickListener;//长按监听变量

    //将得到的监听变量放进适配器里面
    public void setonItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    //添加一个长按接口
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    //定义一个RecyclerView的ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        View goods_View;
        TextView goods_Image;
        TextView goods_Name;

        public ViewHolder(View view) {
            super(view);
            goods_View = view;
            goods_Image = (TextView) view.findViewById(R.id.good_image);
            goods_Name = (TextView) view.findViewById(R.id.good_name);
        }
    }

    //适配器的构造函数
    public GoodAdapter(List<Goods> goodsList) {
        mGoodsList = goodsList;
    }

    //创建Item视图，并且返回相应的ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //在创建一个视图的时候顺便实现点按的操作
        holder.goods_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //单击是跳转，所以要拿到详细页面的Intent
                Intent intent = new Intent(v.getContext(), SecondIntent.class);
                intent.putExtra("goods_name", mGoodsList.get(position).getName());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    //绑定数据到View上
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Goods good = mGoodsList.get(position);
        holder.goods_Image.setText(good.getLetter());
        holder.goods_Name.setText(good.getName());
        //绑定长按事件
        if(mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    //记录长按的位置，将这个位置传出去
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }
}