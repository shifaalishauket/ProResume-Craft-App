package com.pro.resume.craft.utils;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) { // [-Infinity,-1)
            page.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            page.setAlpha(1);

            // Counteract the default slide transition
            page.setTranslationX(-position * pageWidth);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = Math.max(0.85f, 1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            page.setAlpha(0);
        }
    }
}
