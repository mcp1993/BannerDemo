package com.mcp1993.bannerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcp1993 on 2017/10/25.
 */

public class Banner  extends FrameLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;
    private List<String> mUrlList;

    private List<ImageView> dotList = null;
    private MyAdapter mAdapter = null;
    private Handler mHandler = null;
    private AutoRollRunnable mAutoRollRunnable = null;
    private List<View> imageViews = null;

    private int prePosition = 0;

    public Banner(@NonNull Context context) {
        super(context);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initData();
    }
    //初始化view
    private void initView() {
        View.inflate(mContext, R.layout.view_header, this);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);
        ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
        vParams.height = (int) (getMobileWidth(mContext) / 2);
        mViewPager.setLayoutParams(vParams);
    }

    private void initData() {
        dotList = new ArrayList<ImageView>();
        imageViews = new ArrayList<>();
        mAutoRollRunnable = new AutoRollRunnable();
        mHandler = new Handler();
        mAdapter = new MyAdapter();
    }


    /**
     * 设置数据
     *
     * @param urlList
     */
    public void setImgUrlData(List<String> urlList) {
        imageViews.clear();
        this.mUrlList = urlList;
        for (int i = 0;i<=urlList.size()+1;i++){
            ImageView imageView = null;
            if (imageView == null) {
                imageView = new ImageView(mContext);
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Object url = null;
            if (i == 0) {
                url = mUrlList.get(mUrlList.size() - 1);
            } else if (i == mUrlList.size() + 1) {
                url = mUrlList.get(0);
            } else {
                url = mUrlList.get(i - 1);
            }
            imageViews.add(imageView);
            Glide.with(mContext).load(url)
                    .error(R.mipmap.ic_launcher).into(imageView);
        }
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

                params.height = 40;
                params.width = 40;
                dotIv.setLayoutParams(params);

                //添加点到view
                mDotLl.addView(dotIv);
                //添加到集合中
                dotList.add(dotIv);
            }
        }

        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);

        //设置viewpager初始位置, +10000就够�?
//        mViewPager.setCurrentItem(urlList.size() + 10000);
        startRoll();
    }

    public void startRoll() {
        mAutoRollRunnable.start();
    }
    private class MyAdapter extends PagerAdapter {

        //为了复用
        private List<ImageView> imgCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            //无限滑动
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
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
    private class AutoRollRunnable implements Runnable {

        //是否在轮播的标志
        boolean isRunning = false;

        public void start() {
            if (!isRunning) {
                isRunning = true;
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, 3000);
            }
        }

        public void stop() {
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }

        @Override
        public void run() {
            if (isRunning) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, 3000);
            }
        }
    }
    //屏幕宽度
    public static int getMobileWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; // 得到宽度
        return width;

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) position = mUrlList.size();
        if (position > mUrlList.size()) position = 1;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
