package com.example.buxiaohui.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.example.buxiaohui.myapplication.ui.MainActivity;
import com.example.buxiaohui.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;

/**
 * Created by buxiaohui on 5/10/2016.
 */
public class FrescoSelfDefineView extends View {
    MultiDraweeHolder<GenericDraweeHierarchy> mMultiDraweeHolder;
    DraweeHolder<GenericDraweeHierarchy> draweeHolder0;
    DraweeHolder<GenericDraweeHierarchy> draweeHolder1;
    DraweeHolder<GenericDraweeHierarchy> draweeHolder2;
    DraweeHolder<GenericDraweeHierarchy> draweeHolder3;

    DraweeController controller0;
    DraweeController controller1;
    DraweeController controller2;
    DraweeController controller3;

    private boolean mIsSingle;

    private void init(Context context) {
        Uri uri0 = Uri.parse(MainActivity.imagePath0);
        Uri uri1 = Uri.parse(MainActivity.imagePath1);
        Uri uri2 = Uri.parse(MainActivity.imagePath2);
        Uri uri3 = Uri.parse(MainActivity.imagePath3);
        GenericDraweeHierarchy hierarchy0 = new GenericDraweeHierarchyBuilder(getResources())
                .setFadeDuration(R.drawable.icon_downloading)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        GenericDraweeHierarchy hierarchy1 = new GenericDraweeHierarchyBuilder(getResources())
                //.set
                .build();
        GenericDraweeHierarchy hierarchy2 = new GenericDraweeHierarchyBuilder(getResources())
                //.set
                .build();
        GenericDraweeHierarchy hierarchy3 = new GenericDraweeHierarchyBuilder(getResources())
                //.set
                .build();

        draweeHolder0 = DraweeHolder.create(hierarchy0, context);
        draweeHolder1 = DraweeHolder.create(hierarchy1, context);
        draweeHolder2 = DraweeHolder.create(hierarchy2, context);
        draweeHolder3 = DraweeHolder.create(hierarchy3, context);

        controller0 = Fresco.newDraweeControllerBuilder()
                .setUri(uri0)
                .setOldController(draweeHolder0.getController())
                .build();

        controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri1)
                .setOldController(draweeHolder1.getController())
                .build();
        controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .setOldController(draweeHolder2.getController())
                .build();
        controller3 = Fresco.newDraweeControllerBuilder()
                .setUri(uri3)
                .setOldController(draweeHolder3.getController())
                .build();


        draweeHolder0.setController(controller0);
        draweeHolder1.setController(controller1);
        draweeHolder2.setController(controller2);
        draweeHolder3.setController(controller3);

        mMultiDraweeHolder = new MultiDraweeHolder<GenericDraweeHierarchy>();
        mMultiDraweeHolder.add(draweeHolder0);
        mMultiDraweeHolder.add(draweeHolder1);
        mMultiDraweeHolder.add(draweeHolder2);
        mMultiDraweeHolder.add(draweeHolder3);
        // repeat for more hierarchies
    }

    public void showImage() {
        mIsSingle = !mIsSingle;
        if (mIsSingle) {
            draweeHolder0.setController(controller0);
        } else {
            draweeHolder0.setController(controller0);
            draweeHolder1.setController(controller1);
            draweeHolder2.setController(controller2);
            draweeHolder3.setController(controller3);
        }
        invalidate();
    }

    public boolean getState() {
        return mIsSingle;
    }

    public FrescoSelfDefineView(Context context) {
        super(context);
        init(context);
    }

    public FrescoSelfDefineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FrescoSelfDefineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        debugTypeDetach();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        debugTypeAttach();
    }

    public void debugTypeDetach() {
        if (mIsSingle) {
            draweeHolder0.onDetach();
        } else {
            mMultiDraweeHolder.onDetach();
        }
    }

    public void debugTypeAttach() {
        if (mIsSingle) {
            draweeHolder0.onAttach();
        } else {
            mMultiDraweeHolder.onAttach();
        }
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        debugTypeDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        debugTypeAttach();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Drawable drawable = null;
        if (mIsSingle) {
            drawable = draweeHolder0.getTopLevelDrawable();
            if (drawable != null) {
                drawable.setBounds(
                        getPaddingLeft(),
                        getPaddingTop(),
                        getWidth() - getPaddingRight(),
                        getHeight() - getPaddingBottom());
                drawable.draw(canvas);
            }

        } else {
            /**
             * 0 | 3
             * -----
             * 1 | 2
             */
            int a = getPaddingLeft();
            int b = getPaddingRight();
            int c = getPaddingTop();
            int d = getPaddingBottom();

            //交叉点的坐标
            int midX = (getWidth() - getPaddingRight() - getPaddingLeft()) / 2 + getPaddingLeft();
            int midY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();

            Drawable drawableChild0 = mMultiDraweeHolder.get(0).getTopLevelDrawable();
            if (drawableChild0 != null) {
                drawableChild0.setBounds(
                        getPaddingLeft(),
                        getPaddingTop(),
                        midX,
                        midY);
                drawableChild0.draw(canvas);
            }
            Drawable drawableChild1 = mMultiDraweeHolder.get(1).getTopLevelDrawable();
            if (drawableChild1 != null) {
                drawableChild1.setBounds(
                        (getPaddingLeft()),
                        midY,
                        midX,
                        (getHeight() - getPaddingBottom()));
                drawableChild1.draw(canvas);
            }
            Drawable drawableChild2 = mMultiDraweeHolder.get(2).getTopLevelDrawable();
            if (drawableChild2 != null) {
                drawableChild2.setBounds(
                        midX,
                        midY,
                        getWidth() - getPaddingRight(),
                        getHeight() - getPaddingBottom());
                drawableChild2.draw(canvas);
            }
            Drawable drawableChild3 = mMultiDraweeHolder.get(3).getTopLevelDrawable();
            if (drawableChild3 != null) {
                drawableChild3.setBounds(
                        midX,
                        getPaddingTop(),
                        getWidth() - getPaddingRight(),
                        midY);
                drawableChild3.draw(canvas);
            }
        }

    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (who == draweeHolder0.getHierarchy().getTopLevelDrawable()) {
            return true;
        }
        return super.verifyDrawable(who);
    }
}
