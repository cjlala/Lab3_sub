package cjlalala.lab3_sub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2017/10/23.
 */

public class SecondIntent extends AppCompatActivity {
    private String[] list = {"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
    private List<Goods> GoodsList = new ArrayList<>();
    public static final String[] Name = new String[]{
            "Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis",
            "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers",
            "Lindt", "Borggreve"
    };

    public static final String[] Price = new String[]{
            "¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00",
            "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43",
            "¥ 139.43", "¥ 28.90"
    };

    public static final String[] Type = new String[]{
            "作者", "产地", "产地", "版本",
            "重量", "产地", "重量", "重量",
            "重量", "重量"
    };

    public static final String[] Information = new String[]{
            "Johanna Basford", "德国", "澳大利亚", "8GB",
            "2Kg", "英国", "300g", "118g",
            "249g", "640g"
    };

    public static final int[] Image = new int[]{
            R.drawable.enchatedforest, R.drawable.arla, R.drawable.devondale, R.drawable.kindle,
            R.drawable.waitrose, R.drawable.mcvitie, R.drawable.ferrero, R.drawable.maltesers,
            R.drawable.lindt, R.drawable.borggreve
    };

    public static final String[] Letter = new String[]{
            "E", "A", "D", "K", "W",
            "M", "F", "M", "L", "B"
    };

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.detail_page);

        //回退按钮的功能实现,只要一按这个按钮就终止这个事件
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //星星的点按实现
        final ImageView star = (ImageView) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            int flag = 0;
            //使用一个flag记录当前的星星状态
            public void onClick(View view) {
                if (flag == 0) {
                    star.setImageResource(R.drawable.full_star);
                    flag = 1;}
                else{
                    star.setImageResource(R.drawable.empty_star);
                    flag = 0;}
            }
        });

        //获得一个ListView的适配器，将上面list表中的内容放进listView里面
        //也就是实现"更多产品信息"下面的四行文本
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondIntent.this,
                android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.list_view1);
        listView.setAdapter(adapter);

        //作为一个Intent接收到其它Intent通过"goods_name"传过来的信息
        Intent intent = getIntent();
        final String goods_name = intent.getStringExtra("goods_name");

        //使用EventBus将购物车的点按的信息发送到FirstIntent中
        ImageView shop = (ImageView) findViewById(R.id.shop_car);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new FirstIntent(goods_name));
                Toast.makeText(SecondIntent.this, "商品已添加到购物车",Toast.LENGTH_SHORT).show();
            }
        });

        ImageView forground = (ImageView) findViewById(R.id.forground);
        TextView name = (TextView) findViewById((R.id.name));
        TextView price = (TextView) findViewById(R.id.price_view);
        TextView information = (TextView) findViewById(R.id.information_view);

        for(int i = 0; i<10 ;i++){
            Goods tmp = new Goods(Name[i], Letter[i], Price[i], Type[i], Information[i], Image[i]);
            GoodsList.add(tmp);
        }

       for(int i=0; i<10; i++) {
           if(goods_name.equals(GoodsList.get(i).getName())){
               name.setText(GoodsList.get(i).getName());
               price.setText(GoodsList.get(i).getPrice());
               forground.setImageResource(GoodsList.get(i).getImage());
               String tmp = GoodsList.get(i).getType() + " " + GoodsList.get(i).getInformation();
               information.setText(tmp);
               break;
           }
       }
}}
