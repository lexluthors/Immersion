package com.mycp.immersion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
    private ImmersionBar immersionBar;
    private TextView toolbarTitle;

    private int navTitleColor = Color.parseColor("#ff0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        immersionBar = ImmersionBar.with(this)
                .barColor(R.color.transparent)
                .addViewSupportTransformColor(findViewById(R.id.toolbar))
                .fitsSystemWindows(false)
                .statusBarDarkFont(true);
        immersionBar.init();

        statusView = findViewById(R.id.statusView);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("测试容器");
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

    float alpha = 0f;
    /**
     * 是否是透明的
     */
    boolean isTrans = false;

    private void initColor() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("ces测试", "dx大小》》》" + dx + "  dy大小》》》" + dy);
                mDisplayY += dy;
                if (mDisplayY <= toolbarHeight) {
                    alpha = mDisplayY / toolbarHeight;
                    Log.e("alpha", "alpha》》》" + alpha);
                    setAlphaAllView(rootLayout,alpha);
                    immersionBar.statusBarDarkFont(true,alpha).init();
                    isTrans = true;
                } else {
                    if (isTrans){
                        setAlphaAllView(rootLayout,1);
                        immersionBar.statusBarDarkFont(false).init();
                        isTrans = false;
                    }
                }
            }
        });
    }

    /**
     * 设置view 透明度 包括子view
     *
     * @param view
     * @param alpha 10进制 0...1
     */
    public static   void setAlphaAllView(View view, float alpha) {
        if (view == null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().mutate().setAlpha((int) (alpha * 255));
        }
        Log.d( "asdfa","setAlphaAllView alpha:" + alpha + " alphaNum:" + alpha);
        view.setAlpha(alpha);
        //设置子view透明度
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewChild = vp.getChildAt(i);
                //（递归）
                setAlphaAllView(viewChild, alpha);
            }
        }
    }

    /**
     * 获取状态栏的高度
     * @param activity
     * @return
     */
    private int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return activity.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}