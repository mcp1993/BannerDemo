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

public class RollHeaderView extends FrameLayout implements OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;
    private List<String> mUrlList;

    private List<ImageView> dotList = null;
    private MyAdapter mAdapter = null;
    private Handler mHandler = null;
    private AutoRollRunnable mAutoRollRunnable = null;

    private int prePosition = 0;

    private HeaderViewClickListener headerViewClickListener;

    public RollHeaderView(Context context) {
        this(context, null);
    }

    public RollHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initData();
        initListener();
    }

    //初始化view
    private void initView() {
        View.inflate(mContext, R.layout.view_header, this);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);


        ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
//        vParams.height = (int) (DisplayUtil.getMobileHeight(mContext) * 0.4);
        vParams.height = (int) (DisplayUtil.getMobileWidth(mContext) / 2);
        mViewPager.setLayoutParams(vParams);
    }

    //初始化数�?
    private void initData() {
        dotList = new ArrayList<ImageView>();
        mAutoRollRunnable = new AutoRollRunnable();
        mHandler = new Handler();
        mAdapter = new MyAdapter();
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(this);
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

                params.height = 40;
                params.width = 40;
                dotIv.setLayoutParams(params);

                //添加点到view�?
                mDotLl.addView(dotIv);
                //添加到集合中, 以便控制其切�?
                dotList.add(dotIv);
            }
        }

        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);

        //设置viewpager初始位置, +10000就够�?
        mViewPager.setCurrentItem(urlList.size() + 10000);
        startRoll();
    }


    /**
     * 设置点击事件
     *
     * @param headerViewClickListener
     */
    public void setOnHeaderViewClickListener(HeaderViewClickListener headerViewClickListener) {
        this.headerViewClickListener = headerViewClickListener;
    }


    //�?始轮�?
    public void startRoll() {
        mAutoRollRunnable.start();
    }

    // 停止轮播
    public void stopRoll() {
        mAutoRollRunnable.stop();
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

    public interface HeaderViewClickListener {
        void HeaderViewClick(int position);
    }

    private class MyAdapter extends PagerAdapter {

        //为了复用
        private List<ImageView> imgCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            //无限滑动
            return Integer.MAX_VALUE;
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
            iv.setScaleType(ScaleType.FIT_XY);

            iv.setOnTouchListener(new OnTouchListener() {
                private int downX = 0;
                private long downTime = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mAutoRollRunnable.stop();
                            //获取按下的x坐标
                            downX = (int) v.getX();
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            mAutoRollRunnable.start();
                            int moveX = (int) v.getX();
                            long moveTime = System.currentTimeMillis();
                            if (downX == moveX && (moveTime - downTime < 500)) {//点击的条�?
                                //轮播图回调点击事�?
                                if (headerViewClickListener != null) {
                                    headerViewClickListener.HeaderViewClick(position % mUrlList.size());
                                }
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mAutoRollRunnable.start();
                            break;
                    }
                    return true;
                }
            });

            //加载图片
//
//            Picasso.with(mContext).load(mUrlList.get(position % mUrlList.size()))
//                    .into(iv);
//            ((ViewPager) container).addView(iv);

//            return iv;
            Glide.with(mContext).load(mUrlList.get(position % mUrlList.size()))
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
        dotList.get(prePosition).setBackgroundResource(R.drawable.sprite_nav1_3);
        dotList.get(position % dotList.size()).setBackgroundResource(R.drawable.sprite_nav_3);
        prePosition = position % dotList.size();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }


    //停止轮播
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRoll();
    }
}
