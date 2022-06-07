package com.mycp.immersion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    View statusView;
    /**
     * 滑动的距离
     */
    private float mDisplayY = 0;
    float toolbarHeight = 520;
    private Toolbar toolbar;
    private LinearLayout rootLayout;

    private int navTitleColor = Color.parseColor("#ff0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).
                barColor(R.color.transparent).addViewSupportTransformColor(findViewById(R.id.toolbar)).fitsSystemWindows(false).init();


        statusView = findViewById(R.id.statusView);
        Log.e("ces测试", getStatusBarHeight(this)+"高度");
        statusView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(this)));
        recyclerView = findViewById(R.id.recyclerView);
        rootLayout = findViewById(R.id.rootLayout);
        toolbar = findViewById(R.id.toolbar);
        rootLayout.setBackgroundColor(navTitleColor);
        rootLayout.setAlpha(0);
        rootLayout.getBackground().setAlpha(0);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("哈哈哈哈哈");
        }
        DemoAdapter adapter = new DemoAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        initColor();
    }


    private void initColor() {
        int red = (navTitleColor & 0xff0000) >> 16;

        int green = (navTitleColor & 0x00ff00) >> 8;

        int blue = (navTitleColor & 0x0000ff);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("ces测试", "dx大小》》》" + dx + "  dy大小》》》" + dy);
                mDisplayY += dy;

                if (mDisplayY <= toolbarHeight) {
                    float alpha = mDisplayY / toolbarHeight;
                    int alpha2 = (int) (alpha * 255);
                    Log.e("alpha", "alpha》》》" + alpha);
//                    toolbar.setAlpha(alpha);
//                    toolbar.getBackground().setAlpha(alpha2);
                    setAlphaAllView(rootLayout,alpha);
                } else {
                    rootLayout.setBackgroundColor(navTitleColor);
                    rootLayout.setAlpha(1f);
//                    toolbar.getBackground().setAlpha(255);
                }
            }
        });
    }

    /**
     * 设置view 透明度 包括子view
     *
     * @param view
     * @param alpha 10进制
     */
    public static   void setAlphaAllView(View view, float alpha) {
        if (view == null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().mutate().setAlpha((int) (alpha * 255));
        }
        float alphaNum = alpha;
        Log.d( "asdfa","setAlphaAllView alpha:" + alpha + " alphaNum:" + alphaNum);
        view.setAlpha(alphaNum);
        //设置子view透明度
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewChild = vp.getChildAt(i);
                //调用本身（递归）
                setAlphaAllView(viewChild, alpha);
            }
        }
    }

    //获取状态栏的高度
    private int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return activity.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}