package com.example.buxiaohui.myapplication.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.signature.StringSignature;

/**
 * Created by buxiaohui on 10/19/16.
 */
public class ImageUtils {
    private static ImageUtils mInstance;

    //single instance
    public static ImageUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtils();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * UI thread
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    public static void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    public static void clearTarget(View view) {
        Glide.clear(view);
    }


    public static void showImage(Context context, ImageView view, String imgPath) {
        Glide.with(context).load(imgPath).into(view);
    }

    public static void showImage(Context context, ImageView view, String imgPath, int placeHolderImgId, int errorImgId) {
        showImage(context, view, imgPath, placeHolderImgId, errorImgId, null);
    }

    public static void showCircleImage(Context context, ImageView view, String imgPath, int placeHolderImgId, int errorImgId) {

    }

    public static void showImage(Context context, ImageView view, String imgPath, int placeHolderImgId, int errorImgId, RequestListener listener) {
        DrawableRequestBuilder builder = Glide.with(context).load(imgPath);
        if (placeHolderImgId > 0) {
            builder.placeholder(placeHolderImgId);
        }
        if (errorImgId > 0) {
            builder.error(errorImgId);
        }
        if (listener != null) {
            builder.listener(listener);
        }
        builder.centerCrop().into(view);
    }


    private static void showImage(Context context, ImageView view, Object objUrl, ImageLoadConfig config, final RequestListener listener) {
        if (null == objUrl) {
            throw new IllegalArgumentException("objUrl is null");
        } else if (null == config) {
            config = ImageLoadConfig.defConfig;
        }
        try {
            GenericRequestBuilder builder = null;
            if (config.isAsGif()) {//gif类型
                GifRequestBuilder request = Glide.with(context).load(objUrl).asGif();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isAsBitmap()) {  //bitmap 类型
                BitmapRequestBuilder request = Glide.with(context).load(objUrl).asBitmap();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                //transform bitmap
                if (config.getTransformation() != null) {
                    request.transform(config.getTransformation());
                }
                builder = request;
            } else if (config.isFade()) { // 渐入渐出动画
                DrawableRequestBuilder request = Glide.with(context).load(objUrl).crossFade();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }
            //缓存设置
            builder.diskCacheStrategy(config.getDiskCacheStrategy()).
                    skipMemoryCache(config.isSkipMemoryCache()).
                    priority(config.getPrioriy());
            builder.dontAnimate();
            if (null != config.getTag()) {
                builder.signature(new StringSignature(config.getTag()));
            } else {
                builder.signature(new StringSignature(objUrl.toString()));
            }
            if (null != config.getAnimator()) {
                builder.animate(config.getAnimator());
            } else if (config.getAnimResId() > 0) {
                builder.animate(config.getAnimResId());
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (config.getErrorResId() > 0) {
                builder.error(config.getErrorResId());
            }
            if (config.getPlaceHolderResId() > 0) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            if (null != config.getFinalImgSize()) {
                builder.override(config.getFinalImgSize().getWidth(), config.getFinalImgSize().getHeight());
            }
            if (null != listener) {
                builder.listener(listener);
            }
            if (null != config.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = Glide.with(context).load(config.getThumbnailUrl()).asBitmap();
                builder.thumbnail(thumbnailRequest).into(view);
            } else {
                setTargetView(builder, config, view);
            }
        } catch (Exception e) {
            view.setImageResource(config.getErrorResId());
        }
    }

    private static void setTargetView(GenericRequestBuilder request, ImageLoadConfig config, ImageView view) {
        //set targetView
        if (null != config.getSimpleTarget()) {
            request.into(config.getSimpleTarget());
        } else if (null != config.getViewTarget()) {
            request.into(config.getViewTarget());
        } else if (null != config.getNotificationTarget()) {
            request.into(config.getNotificationTarget());
        } else if (null != config.getAppWidgetTarget()) {
            request.into(config.getAppWidgetTarget());
        } else {
            request.into(view);
        }
    }

}
