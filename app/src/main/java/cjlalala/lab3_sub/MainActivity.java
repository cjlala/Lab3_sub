package cjlalala.lab3_sub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    private List<Goods> GoodList = new ArrayList<>();
    private List<Goods> list = new ArrayList<>();
    final ListAdapter list_adapter = new ListAdapter(MainActivity.this, R.layout.shopping_car, list);
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGoods();//初始化商品信息

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        EventBus.getDefault().register(this);//当前的事件注册一个EventBus

        listView.setAdapter(list_adapter);
        listView.setVisibility(View.GONE);//起初购物车的界面设为不可见
        recyclerView.setVisibility(View.VISIBLE);

        //RecyclerView线性布局同时动态效果
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final GoodAdapter adapter = new GoodAdapter(GoodList);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(1000);
        recyclerView.setAdapter(animationAdapter);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());

        //RecycleView的长按事件操作
        adapter.setonItemLongClickListener(new GoodAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                GoodList.remove(position);//删除掉position位置上的商品
                adapter.notifyDataSetChanged();//适配器即使更新
                Toast.makeText(MainActivity.this, "移除第" + (position + 1) +
                        "个商品", Toast.LENGTH_SHORT).show();
            }
        });

        //点击悬浮按钮实现购物车、商品列表切换
        final ImageView floating_button = (ImageView) findViewById(R.id.float_button);
        floating_button.setImageResource(R.drawable.shoplist);
        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    listView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    floating_button.setImageResource(R.drawable.mainpage);
                    flag = 1;
                } else {
                    listView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    floating_button.setImageResource(R.drawable.shoplist);
                    flag = 0;
                }
            }
        });

        //在购物车列表也要实现点按切换到详细页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Intent intent = new Intent(MainActivity.this, SecondIntent.class);
                    intent.putExtra("goods_name", list.get(position).getName());
                    startActivity(intent);
                }
            }
        });
        //购物车列表长按也是删除并且弹出对话框
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position != 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("移除商品");
                    dialog.setMessage("从购物车中移除" + list.get(position).getName());
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            list.remove(position);
                            list_adapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "你选择了：取消",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }

    public static final String[] name = new String[]{
            "Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis",
            "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers",
            "Lindt", "Borggreve"
    };

    public static final String[] price = new String[]{
            "¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00",
            "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43",
            "¥ 139.43", "¥ 28.90"
    };

    public static final String[] type = new String[]{
            "作者", "产地", "产地", "版本",
            "重量", "产地", "重量", "重量",
            "重量", "重量"
    };

    public static final String[] information = new String[]{
            "Johanna Basford", "德国", "澳大利亚", "8GB",
            "2Kg", "英国", "300g", "118g",
            "249g", "640g"
    };

    public static final int[] image = new int[]{
            R.drawable.enchatedforest, R.drawable.arla, R.drawable.devondale, R.drawable.kindle,
            R.drawable.waitrose, R.drawable.mcvitie, R.drawable.ferrero, R.drawable.maltesers,
            R.drawable.lindt, R.drawable.borggreve
    };

    public static final String[] letter = new String[]{
            "E", "A", "D", "K", "W",
            "M", "F", "M", "L", "B"
    };

    private void initGoods() {
        for (int i = 0; i < 10; i++) {
            Goods tmp = new Goods(name[i], letter[i], price[i], type[i], information[i],image[i]);
            GoodList.add(tmp);
        }
        Goods title = new Goods("购物车","*","价格","","",0);
        list.add(title);
    }

    //EventBus中的主线程模式，拿到详情页面传过来的信息加进购物车里面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstIntent intent) {
        String msg = intent.getmMsg();
        for(int i=0;i<10;i++) {
            if(msg.equals(GoodList.get(i).getName())){
                list.add(GoodList.get(i));
                list_adapter.notifyDataSetChanged();
            }
        }
    }
    //删除这个活动的注册信息的函数
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
