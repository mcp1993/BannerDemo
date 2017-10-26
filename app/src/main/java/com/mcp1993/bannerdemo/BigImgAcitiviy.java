package com.mcp1993.bannerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by mcp1993 on 2017/10/26 0026.
 */

public class BigImgAcitiviy extends AppCompatActivity {
    private ViewPager viewpager;
    private List<String> imgeInfos = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimg);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        imgeInfos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436453&di=2a13f324ef4294a4e6c2ae4449386542&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F1f178a82b9014a90e7eb9d17ac773912b21bee47.jpg");
        imgeInfos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436452&di=1f5360572b0467e649e67a50491a74da&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F0d338744ebf81a4cebca559dd12a6059242da6ee.jpg");
        imgeInfos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508933436451&di=6d449bea178ad518d8076ebedffb2317&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2Fanime%2F4446%2F4446-8866.jpg");

        viewpager.setAdapter(new SamplePagerAdapter(imgeInfos));
    }


    class SamplePagerAdapter extends PagerAdapter {
        List<String> mImgeInfos = new ArrayList<>();
        SparseArray<PhotoView> mViews;

        public SamplePagerAdapter(List<String> imges) {
            mImgeInfos = imges;
            mViews = new SparseArray<PhotoView>();
        }

        @Override
        public int getCount() {
            return mImgeInfos.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = mViews.get(position);
            if (null == photoView) {
                photoView = new PhotoView(container.getContext());
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mViews.put(position, photoView);
                //  ImageManager.instance().loadImage(mImgeInfos.get(position).url, photoView, ImageConfig.fullImageConfig(), R.drawable.thum);

            }
            Glide.with(BigImgAcitiviy.this).load(mImgeInfos.get(position)).into(photoView);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            // Now just add PhotoView to ViewPager and return it


            return photoView;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mViews.remove(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
