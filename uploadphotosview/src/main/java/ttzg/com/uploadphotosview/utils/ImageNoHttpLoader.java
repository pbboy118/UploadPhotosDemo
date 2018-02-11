package ttzg.com.uploadphotosview.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * @author: xin.wu
 * @create time: 2016/5/18 10:51
 */
public class ImageNoHttpLoader {
    /**
     * TODO 图片下载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImage(Context context, String url, final ImageView imageView) {
        if (Utils.isNull(url)) {
            return;
        }

        if (url.startsWith("http")) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .animate(new ViewPropertyAnimation.Animator() {
                        @Override
                        public void animate(View view) {
                            view.setAlpha(0f);
                            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                            fadeAnim.setDuration(800);
                            fadeAnim.start();
                        }
                    })
                    .into(imageView);
        } else {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .animate(new ViewPropertyAnimation.Animator() {
                        @Override
                        public void animate(View view) {
                            view.setAlpha(0f);
                            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                            fadeAnim.setDuration(800);
                            fadeAnim.start();
                        }
                    })
                    .into(imageView);
        }
    }
}
