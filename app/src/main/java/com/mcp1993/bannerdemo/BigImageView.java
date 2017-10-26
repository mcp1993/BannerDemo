package com.mcp1993.bannerdemo;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class BigImageView extends FrameLayout implements OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;
    private List<String> mUrlList;

    private List<ImageView> dotList = null;
    private MyAdapter mAdapter = null;

    private int prePosition = 0;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initData();

    }

    //初始化view
    private void initView(){
        View.inflate(mContext, R.layout.view_header, this);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);
        //让banner的高度是屏幕
        ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
        vParams.height = (int) (DisplayUtil.getMobileHeight(mContext));
        mViewPager.setLayoutParams(vParams);
    }

    //初始化数�?
    private void initData() {
        dotList = new ArrayList<ImageView>();
        mAdapter = new MyAdapter();
    }




    /**
     * 设置数据
     *
     * @param urlList
     */
    public void setImgUrlData(List<String> urlList) {
        this.mUrlList = urlList;
        if (mUrlList != null && !mUrlList.isEmpty()) {
            //清空数据
            dotList.clear();
            mDotLl.removeAllViews();
            ImageView dotIv;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < mUrlList.size(); i++) {
                dotIv = new ImageView(mContext);
                if (i == 0) {
                    dotIv.setBackgroundResource(R.drawable.sprite_nav_3);
                } else {
                    dotIv.setBackgroundResource(R.drawable.sprite_nav1_3);
                }
                //设置点的间距
                params.setMargins(0, 0, DisplayUtil.dip2px(mContext, 5), 0);
               
                params.height=40; 
                params.width=40;
                dotIv.setLayoutParams(params);

                //添加点到view�?
                mDotLl.addView(dotIv);
                //添加到集合中, 以便控制其切�?
                dotList.add(dotIv);
            }
        }

        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);

        //每次打开都会显示Viewpager第一页
//        mViewPager.setCurrentItem(0);

    }


    private class MyAdapter extends PagerAdapter {

        //为了复用
        private List<ImageView> imgCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            return mUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView iv;

            //获取ImageView对象
            if (imgCache.size() > 0) {
                iv = imgCache.remove(0);
            } else {
                iv = new ImageView(mContext);
            }
//            iv.setScaleType(ScaleType.FIT_XY);

            Glide.with(mContext).load(mUrlList.get(position ))
            .error(R.mipmap.ic_launcher).into(iv);
            ((ViewPager) container).addView(iv);
              return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null && object instanceof ImageView) {
                ImageView iv = (ImageView) object;
                ((ViewPager) container).removeView(iv);
                imgCache.add(iv);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i =0;i<dotList.size();i++){
            dotList.get(i).setBackgroundResource(R.drawable.sprite_nav1_3);
        }
        dotList.get(position).setBackgroundResource(R.drawable.sprite_nav_3);
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }


    //停止轮播
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
