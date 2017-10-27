package cjlalala.lab3_sub;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by cj on 2017/10/23.
 */

public class ListAdapter extends BaseAdapter{
    private Context context;
    private List<Goods> list;
    private int resource;
    /*适配器的构造函数*/
    public ListAdapter(Context context, int resource, List<Goods> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
    }
    /*获得对应位置子项的函数*/
    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
    }
    /*获得子项的ID*/
    @Override
    public long getItemId(int position) {
        return position;
    }
    /*获得整个列表的长度*/
    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }
        return  list.size();
    }
    /*获得适配器生成的view*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if(convertView ==null){
            view = LayoutInflater.from(context).inflate(resource,parent,false);
        }else{
            view = convertView;
        }
        TextView name = (TextView) view.findViewById(R.id.name);//商品的名称
        TextView imageid = (TextView) view.findViewById(R.id.circle);//圆形字母框
        TextView price = (TextView) view.findViewById(R.id.price);//商品的价格
        name.setText(list.get(position).getName());//使用之前Goods类里面的get函数
        imageid.setText(list.get(position).getLetter());
        price.setText(list.get(position).getPrice());
        return view;
    }
}
