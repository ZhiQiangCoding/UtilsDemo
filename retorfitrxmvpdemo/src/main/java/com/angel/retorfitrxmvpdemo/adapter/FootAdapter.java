package com.angel.retorfitrxmvpdemo.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.angel.retorfitrxmvpdemo.R;
import com.angel.retorfitrxmvpdemo.bean.response.Foot;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public class FootAdapter extends CommonAdapter<Foot.TngouBean> {
    private List<Foot.TngouBean> datas;

    public FootAdapter(List<Foot.TngouBean> datas, int resource) {
        super(datas, resource);
        this.datas = datas;
    }

    @Override
    protected void fillData(int position, CommonViewHolder viewHolder) {
        Foot.TngouBean tngouBean = datas.get(position);
        TextView name = viewHolder.findView(R.id.name);
        TextView desc = viewHolder.findView(R.id.desc);
        ImageView iv = viewHolder.findView(R.id.iv);
        name.setText(tngouBean.getName());
        desc.setText(tngouBean.getDescription());
        //http://tnfs.tngou.net/image/food/150804/2ad85610af25a980b5fd6d1828bbade1.jpg
        Picasso.with(viewHolder.getConvertView().getContext())
                .load("http://tnfs.tngou.net/image" + tngouBean.getImg())
                .into(iv);
    }
}
