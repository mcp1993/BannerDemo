package com.mcp1993.bannerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Banner banner;
    private RollHeaderView headerView;
    private List<String> ImgsUrl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headerView = (RollHeaderView) findViewById(R.id.headerView);
        banner = (Banner) findViewById(R.id.banner);
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436453&di=2a13f324ef4294a4e6c2ae4449386542&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F1f178a82b9014a90e7eb9d17ac773912b21bee47.jpg");
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436452&di=1f5360572b0467e649e67a50491a74da&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F0d338744ebf81a4cebca559dd12a6059242da6ee.jpg");
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436451&di=6d449bea178ad518d8076ebedffb2317&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2Fanime%2F4446%2F4446-8866.jpg");
        banner.setImgUrlData(ImgsUrl);
        headerView.setImgUrlData(ImgsUrl);
    }
}
