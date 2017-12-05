package com.hy.mvp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hy.mvp.R;


public class GlideUtils {

   public static void load(Context context,String url,ImageView iv){
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(iv);
   }

    public static void load(Context context,String url,ImageView iv,int placeHolderResId){
        Glide.with(context)
                .load(url)
                .placeholder(placeHolderResId)
                .into(iv);
    }

    public static void loadRound(final Context context, String url, final ImageView iv){
        Glide.with(context)//
                .load(url)//
                .asBitmap()//
                .placeholder(R.mipmap.ic_launcher_round)//
                .centerCrop()//
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
