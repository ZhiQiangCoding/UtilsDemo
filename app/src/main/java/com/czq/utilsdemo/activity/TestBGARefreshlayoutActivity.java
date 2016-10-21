package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.adapter.CommonAdapter;
import com.czq.utilsdemo.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class TestBGARefreshlayoutActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout refreshlayout;
    private ListView lv;
    private List<String> dataList = new ArrayList<>();//数据源
    //每一页显示多少条
    private int datasize = 10;
    //一共请求多少次数据
    private int allsum = 0;

    private CommonAdapter<String> adapter = null;
    private BGANormalRefreshViewHolder normalRefreshViewHolder;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_bgarefreshlayout);
        refreshlayout = getViewById(R.id.refreshlayout);
        lv = getViewById(R.id.lv);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        //注册刷新和加载更多的监听
        refreshlayout.setDelegate(this);
        //设置刷新和加载更多
        normalRefreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
//        normalRefreshViewHolder.setPullDownRefreshText("下拉刷新");
//        normalRefreshViewHolder.setReleaseRefreshText("释放刷新");
//        normalRefreshViewHolder.setPullDistanceScale(2f);
//        normalRefreshViewHolder.setRefreshingText("加载中...");
//        normalRefreshViewHolder.setLoadingMoreText("加载更多");
        normalRefreshViewHolder.setTopAnimDuration(3000);
        normalRefreshViewHolder.setSpringDistanceScale(0.5f);
        //设置刷新的样式
        refreshlayout.setRefreshViewHolder(normalRefreshViewHolder);

    }

    /**
     * 模拟请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    dataList.clear();
                    setData();
                    refreshlayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    refreshlayout.endLoadingMore();
                    break;
                case 2:
                    refreshlayout.endLoadingMore();
                    break;
            }
        }
    };

    /**
     * 获取数据
     */
    private void setData() {
        for (int i = 0; i < datasize; i++) {
            dataList.add("第" + (allsum * 10 + i) + "条数据");
        }
        if (allsum == 0) {
            adapter = new CommonAdapter<String>(mContext, dataList, R.layout.item_test_lv) {
                @Override
                protected void convertView(View item, String s) {
                    TextView textView = CommonViewHolder.get(item, R.id.tv_show);
                    textView.setText(s);
                }
            };
            lv.setAdapter(adapter);
        } else {
            adapter.setRefresh(true);
        }
    }

    /**
     * 进入页面首次加载
     */
    @Override
    protected void onStart() {
        super.onStart();
        refreshlayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(refreshlayout);

    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        normalRefreshViewHolder.setLoadingMoreText("加载更多");
        allsum = 0;
        handler.sendEmptyMessageAtTime(0,2000);

    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (allsum==2){
            normalRefreshViewHolder.setLoadingMoreText("没有更多数据");
            handler.sendEmptyMessageAtTime(2,2000);
            return true;
        }
        allsum++;
        handler.sendEmptyMessageAtTime(1,2000);
        return true;
    }
}
