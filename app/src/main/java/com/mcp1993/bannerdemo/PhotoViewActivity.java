package com.mcp1993.bannerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcp1993 on 2017/10/26.
 */

public class PhotoViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewpager;
    private List<String> ImgsUrl = new ArrayList<>();
    private List<ImageView> mImageViews = new ArrayList<>();
    private LinearLayout mDotLl;
    private List<ImageView> dotList =  new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436453&di=2a13f324ef4294a4e6c2ae4449386542&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F1f178a82b9014a90e7eb9d17ac773912b21bee47.jpg");
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436452&di=1f5360572b0467e649e67a50491a74da&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F0d338744ebf81a4cebca559dd12a6059242da6ee.jpg");
        ImgsUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436451&di=6d449bea178ad518d8076ebedffb2317&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2Fanime%2F4446%2F4446-8866.jpg");
        for (int i = 0; i < ImgsUrl.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews.add(imageView);
            Glide.with(this).load(ImgsUrl.get(i)).into(imageView);
        }
        initDot();
        viewpager.setAdapter(new BigImgAdapter());
        viewpager.setCurrentItem(0);

        viewpager.setOnPageChangeListener(this);
    }
    private void initDot(){
        //清空数据
        dotList.clear();
        mDotLl.removeAllViews();
        ImageView dotIv;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < ImgsUrl.size(); i++) {
            dotIv = new ImageView(this);
            if (i == 0) {
                dotIv.setBackgroundResource(R.drawable.sprite_nav_3);
            } else {
                dotIv.setBackgroundResource(R.drawable.sprite_nav1_3);
            }
            //设置点的间距
            params.setMargins(0, 0, DisplayUtil.dip2px(this, 5), 0);

            params.height=40;
            params.width=40;
            dotIv.setLayoutParams(params);
            //添加点到view
            mDotLl.addView(dotIv);
            dotList.add(dotIv);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i =0;i<dotList.size();i++){
            dotList.get(i).setBackgroundResource(R.drawable.sprite_nav1_3);
        }
        dotList.get(position).setBackgroundResource(R.drawable.sprite_nav_3);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class BigImgAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return ImgsUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mImageViews.get(position), 0);
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager) container).removeView(mImageViews.get(position));
        }
    }
}
