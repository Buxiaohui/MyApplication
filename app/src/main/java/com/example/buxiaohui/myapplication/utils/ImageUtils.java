package com.example.buxiaohui.myapplication.utils;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.style.TtsSpan;
import android.view.MotionEvent;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.GenericDraweeView;

/**
 * Created by buxiaohui on 4/10/2016.
 */
public class ImageUtils {

    public static void setImageUrl(GenericDraweeView view, String url) {
        setImageUrl(view, url, null);
    }

    public static void setImageUrl(GenericDraweeView view, String path, ControllerListener listener) {
        setImageUrl(view, path, -1, -1, -1, -1, listener);
    }

    public static void setImageUrl(GenericDraweeView view, String imagePath, int idFailureImage, int idPlaceHolderImage,
                                   int idProgressBarImage, int idRetryImage, ControllerListener listener) {
        Uri uri = Uri.parse(imagePath);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(Global.APP_CONTEXT.getResources());
        if (idFailureImage > 0) {
            builder.setFailureImage(idFailureImage, ScalingUtils.ScaleType.CENTER_CROP);
        }
        if (idPlaceHolderImage > 0) {
            builder.setPlaceholderImage(idPlaceHolderImage, ScalingUtils.ScaleType.CENTER_CROP);
        }
        if (idProgressBarImage > 0) {
            builder.setProgressBarImage(idProgressBarImage, ScalingUtils.ScaleType.CENTER_CROP);
        }
        if (idRetryImage > 0) {
            builder.setRetryImage(idRetryImage, ScalingUtils.ScaleType.CENTER_CROP);
        }
        builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                //高宽比
                .setDesiredAspectRatio(1.3f)
                .setFadeDuration(100)
                //按压状态下的图
                //.setPressedStateOverlay(Global.APP_CONTEXT.getResources().getDrawable(R.drawable.icon_press))
                //设置圆角等属性
                .setRoundingParams(new RoundingParams()
                        .setBorder(R.color.colorPrimaryDark, 5)
                        //裁剪为圆形图
                        //.setRoundAsCircle(true)
                );

        //GenericDraweeHierarchy hierarchy = view.getHierarchy() == null ? builder.build() : view.getHierarchy();
        GenericDraweeHierarchy hierarchy = builder.build();

        PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        pipelineDraweeControllerBuilder.setUri(uri);
        if (listener != null) {
            pipelineDraweeControllerBuilder.setControllerListener(listener);
        }
        //r如果有retryImage那么enable此功能
        if (idRetryImage > 0) {
            pipelineDraweeControllerBuilder.setTapToRetryEnabled(true);
        }
        if (view.getController() != null) {
            pipelineDraweeControllerBuilder.setOldController(view.getController());
        }
        DraweeController controller = pipelineDraweeControllerBuilder.build();


        //不知道这个什么用
        controller.setContentDescription("the description");
        view.setController(controller);
        view.setHierarchy(hierarchy);

    }


}
