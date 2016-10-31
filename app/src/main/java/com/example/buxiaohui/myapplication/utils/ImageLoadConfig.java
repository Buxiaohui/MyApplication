package com.example.buxiaohui.myapplication.utils;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.buxiaohui.myapplication.R;

/**
 * Created by buxiaohui on 10/19/16.
 */
public class ImageLoadConfig {
    public static final int CENTER_CROP = 0;
    public static final int FIT_CENTER = 1;
    public static ImageLoadConfig defConfig = new ImageLoadConfig.Builder().
            setCropType(ImageLoadConfig.CENTER_CROP).
            setAsBitmap(true).
            setPlaceHolderResId(R.drawable.icon_placeholder).
            setErrorResId(R.drawable.icon_failure).
            setDiskCacheStrategy(DiskCacheStrategy.SOURCE).
            setPrioriy(Priority.HIGH).build();
    private int placeHolderResId;
    private int errorResId;
    private boolean isFade; //是否淡入淡出动画
    private int fadeDuration; //淡入淡出动画持续的时间
    private OverrideSize finalImgSize; //图片最终显示在ImageView上的宽高度像素
    private int CropType = CENTER_CROP; //裁剪类型
    private boolean asGif; //true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
    private boolean asBitmap;//true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
    private boolean skipMemoryCache;//true,跳过内存缓存,默认false
    private DiskCacheStrategy diskCacheStrategy; //硬盘缓存,默认为all类型
    private Priority prioriy;
    private float thumbnail;
    private String thumbnailUrl;
    private SimpleTarget<Bitmap> simpleTarget;
    private ViewTarget<? extends View, GlideDrawable> viewTarget;
    private NotificationTarget notificationTarget;
    private AppWidgetTarget appWidgetTarget;
    private int animResId;
    private ViewPropertyAnimation.Animator animator;
    private Transformation transformation;
    private String tag;

    private ImageLoadConfig(ImageLoadConfig.Builder builder) {
        this.placeHolderResId = builder.placeHolderResId;
        this.errorResId = builder.errorResId;
        this.isFade = builder.crossFade;
        this.fadeDuration = builder.crossDuration;
        this.finalImgSize = builder.size;
        this.CropType = builder.CropType;
        this.asGif = builder.asGif;
        this.asBitmap = builder.asBitmap;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.diskCacheStrategy = builder.diskCacheStrategy;
        this.thumbnail = builder.thumbnail;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.simpleTarget = builder.simpleTarget;
        this.viewTarget = builder.viewTarget;
        this.notificationTarget = builder.notificationTarget;
        this.appWidgetTarget = builder.appWidgetTarget;
        this.animResId = builder.animResId;
        this.animator = builder.animator;
        this.prioriy = builder.prioriy;
        this.transformation = builder.transformation;
        this.tag = tag;
    }

    public static Builder parseBuilder(ImageLoadConfig config) {
        Builder builder = new Builder();
        builder.placeHolderResId = config.placeHolderResId;
        builder.errorResId = config.errorResId;
        builder.crossFade = config.isFade;
        builder.crossDuration = config.fadeDuration;
        builder.size = config.finalImgSize;
        builder.CropType = config.CropType;
        builder.asGif = config.asGif;
        builder.asBitmap = config.asBitmap;
        builder.skipMemoryCache = config.skipMemoryCache;
        builder.diskCacheStrategy = config.diskCacheStrategy;
        builder.thumbnail = config.thumbnail;
        builder.thumbnailUrl = config.thumbnailUrl;
        builder.simpleTarget = config.simpleTarget;
        builder.viewTarget = config.viewTarget;
        builder.notificationTarget = config.notificationTarget;
        builder.appWidgetTarget = config.appWidgetTarget;
        builder.animResId = config.animResId;
        builder.animator = config.animator;
        builder.prioriy = config.prioriy;
        builder.transformation = config.transformation;
        builder.tag = config.tag;
        return builder;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public boolean isFade() {
        return isFade;
    }

    public int getFadeDuration() {
        return fadeDuration;
    }

    public OverrideSize getFinalImgSize() {
        return finalImgSize;
    }

    public int getCropType() {
        return CropType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public Priority getPrioriy() {
        return prioriy;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public SimpleTarget<Bitmap> getSimpleTarget() {
        return simpleTarget;
    }

    public ViewTarget<? extends View, GlideDrawable> getViewTarget() {
        return viewTarget;
    }

    public NotificationTarget getNotificationTarget() {
        return notificationTarget;
    }

    public AppWidgetTarget getAppWidgetTarget() {
        return appWidgetTarget;
    }

    public int getAnimResId() {
        return animResId;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }


    public Transformation getTransformation() {
        return transformation;
    }

    public String getTag() {
        return tag;
    }

    /**
     * Builder类
     */
    public static class Builder {
        private Integer placeHolderResId;
        private Integer errorResId;
        private boolean crossFade;
        private int crossDuration;
        private OverrideSize size;
        private int CropType = CENTER_CROP;
        private boolean asGif;
        private boolean asBitmap;
        private boolean skipMemoryCache;
        private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.ALL;
        private Priority prioriy = Priority.HIGH;
        private float thumbnail;
        private String thumbnailUrl;
        private SimpleTarget<Bitmap> simpleTarget;
        private ViewTarget<? extends View, GlideDrawable> viewTarget;
        private NotificationTarget notificationTarget;
        private AppWidgetTarget appWidgetTarget;
        private Integer animResId;
        private ViewPropertyAnimation.Animator animator;
        private Transformation transformation;
        private String tag;

        public Builder setPlaceHolderResId(Integer placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public Builder setErrorResId(Integer errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder setCrossFade(boolean crossFade) {
            this.crossFade = crossFade;
            return this;
        }

        public Builder setCrossDuration(int crossDuration) {
            this.crossDuration = crossDuration;
            return this;
        }

        public Builder setSize(OverrideSize size) {
            this.size = size;
            return this;
        }

        public Builder setCropType(int cropType) {
            CropType = cropType;
            return this;
        }

        public Builder setAsGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder setAsBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public Builder setSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder setDiskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public Builder setPrioriy(Priority prioriy) {
            this.prioriy = prioriy;
            return this;
        }

        public Builder setThumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setSimpleTarget(SimpleTarget<Bitmap> simpleTarget) {
            this.simpleTarget = simpleTarget;
            return this;
        }

        public Builder setViewTarget(ViewTarget<? extends View, GlideDrawable> viewTarget) {
            this.viewTarget = viewTarget;
            return this;
        }

        public Builder setNotificationTarget(NotificationTarget notificationTarget) {
            this.notificationTarget = notificationTarget;
            return this;
        }

        public Builder setAppWidgetTarget(AppWidgetTarget appWidgetTarget) {
            this.appWidgetTarget = appWidgetTarget;
            return this;
        }

        public Builder setAnimResId(Integer animResId) {
            this.animResId = animResId;
            return this;
        }

        public Builder setAnimator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public Builder setTransformation(Transformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public ImageLoadConfig build() {
            return new ImageLoadConfig(this);
        }
    }

    public static class OverrideSize {
        private final int width;
        private final int height;

        public OverrideSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

}
