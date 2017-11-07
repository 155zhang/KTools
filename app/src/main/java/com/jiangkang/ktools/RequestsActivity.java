package com.jiangkang.ktools;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiangkang.annotations.log.DebugLog;
import com.jiangkang.ktools.requests.gank.GankFragment;
import com.jiangkang.ktools.requests.juejin.JuejinFragment;
import com.jiangkang.ktools.requests.wechat.WechatFragment;
import com.jiangkang.ktools.requests.zhihu.ZhihuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestsActivity extends AppCompatActivity {

    @BindView(R.id.layout_requests_content)
    FrameLayout layoutRequestsContent;
    @BindView(R.id.tab_layout_requests)
    TabLayout tabLayoutRequests;

    private static final String[] TABS = new String[]{
            "知乎", "掘金", "干货", "微信"
    };

    private static final Fragment[] FRAGMENTS = new Fragment[]{
            new ZhihuFragment(),
            new JuejinFragment(),
            new GankFragment(),
            new WechatFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        ButterKnife.bind(this);
        setTitle("Requests");

        initViews();

    }

    @DebugLog
    private void initViews() {

        tabLayoutRequests.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView)tab.getCustomView().findViewById(R.id.tv_tab_title)).setTextColor(Color.parseColor("#f44336"));
                loadFragment(tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView)tab.getCustomView().findViewById(R.id.tv_tab_title)).setTextColor(Color.BLACK);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addTabItem();

    }

    private void loadFragment(Object tag) {
        int tagIndex = (int) tag;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_requests_content,FRAGMENTS[tagIndex], String.valueOf(tagIndex))
                .commit();
    }

    private void addTabItem() {
        for (int i = 0; i < TABS.length; i++) {
            tabLayoutRequests.addTab(tabLayoutRequests.newTab().setCustomView(getTabItemView(i)).setTag(i));
        }
    }

    private View getTabItemView(int i) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.tab_item_text, tabLayoutRequests, false);
        TextView tvTabTitle = (TextView) itemView.findViewById(R.id.tv_tab_title);
        tvTabTitle.setAllCaps(false);
        tvTabTitle.setText(TABS[i]);
        itemView.setTag(i);
        return itemView;
    }

}
