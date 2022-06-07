package com.mycp.immersion;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.util.List;

/**
 * Description:
 * Data：2022/6/7-下午4:31
 * Author: liujie
 */
public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public DemoAdapter(List<String> list ) {
        super(R.layout.item_layout, list);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, @NonNull String item) {
        helper.setText(R.id.toolbarTitle, "This is an Item, pos: " + (helper.getAdapterPosition() - getHeaderLayoutCount()));
    }
}
