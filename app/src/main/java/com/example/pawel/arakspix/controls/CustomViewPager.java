package com.example.pawel.arakspix.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.example.pawel.arakspix.animations.SlowDurationScroller;

import java.lang.reflect.Field;

/**
 * Created by Pawel on 2016-11-08.
 */

public class CustomViewPager extends android.support.v4.view.ViewPager {

    private SlowDurationScroller mScroller = null;
    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled ? super.onInterceptTouchEvent(event) : false;
    }

    public void setSwipeEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSwipeEnabled() {
        return enabled;
    }

    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new SlowDurationScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setCustomScrollerDuration(scrollFactor);
    }
}
