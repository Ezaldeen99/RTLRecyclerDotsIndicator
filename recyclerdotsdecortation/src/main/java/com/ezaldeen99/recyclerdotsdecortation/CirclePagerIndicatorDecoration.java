/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Ezaldeen Sahb
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.ezaldeen99.recyclerdotsdecortation;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CirclePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    /*
     * circle colors
     */
    private int colorActive = Color.parseColor("#5A5A5A");
    private int colorInactive = Color.parseColor("#FFC7C7C7");

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /*
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight = (int) (DP * 36);

    /*
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 4;

    /*
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 4;
    /*
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 8;

    /*
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    /*
     * layout direction
     */
    int layoutDirection;

    /**
     * @param layoutDirection rtl or ltr for layout direction
     */
    public CirclePagerIndicatorDecoration(int layoutDirection) {
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        this.layoutDirection = layoutDirection;
    }

    /**
     * @param layoutDirection rtl or ltr for layout direction
     * @param colorActive active circle color
     * @param colorInactive inactive circle color
     */
    public CirclePagerIndicatorDecoration(int layoutDirection, int colorActive, int colorInactive) {
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        this.layoutDirection = layoutDirection;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        //set layout direction
        parent.setLayoutDirection(layoutDirection);

        int itemCount = parent.getAdapter().getItemCount();

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX;

        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            indicatorStartX = (parent.getWidth() + indicatorTotalWidth) / 2F;
        } else {
            indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;
        }

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount, layoutDirection);

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
        if (activeChild == null)
            return;
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        float progress = mInterpolator.getInterpolation(left * -1 / (float) width);
        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, layoutDirection);
    }

//  took part of it from https://github.com/bleeding182/recyclerviewItemDecorations/blob/master/app/src/main/java/com/github/bleeding182/recyclerviewdecorations/viewpager/LinePagerIndicatorDecoration.java
    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount, int layoutDirection) {
        mPaint.setColor(colorInactive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {

            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2F, mPaint);

            //change the draw direction
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                start -= itemWidth;
            } else {
                start += itemWidth;
            }
        }
    }

    //  took part of it from https://github.com/bleeding182/recyclerviewItemDecorations/blob/master/app/src/main/java/com/github/bleeding182/recyclerviewdecorations/viewpager/LinePagerIndicatorDecoration.java
    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress, int layoutDirection) {
        mPaint.setColor(colorActive);
        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;
        float highlightStart = calculateHighlightStart(indicatorStartX, itemWidth, highlightPosition, layoutDirection);
        if (progress == 0F) {
            // no swipe, draw a normal indicator
            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2F, mPaint);
        } else {
            // calculate partial highlight
            float partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress;

            // for rtl layouts the circle must move from right to left so we will subtract
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                c.drawCircle(highlightStart - partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaint);
            } else {
                c.drawCircle(highlightStart + partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaint);
            }
        }
    }

    private float calculateHighlightStart(float indicatorStartX, float itemWidth, int highlightPosition, int layoutDirection) {
        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            return indicatorStartX - itemWidth * highlightPosition;
        } else {
            return indicatorStartX + itemWidth * highlightPosition;
        }
    }

}
