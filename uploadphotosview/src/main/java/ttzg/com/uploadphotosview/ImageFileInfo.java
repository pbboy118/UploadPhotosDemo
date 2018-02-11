package ttzg.com.uploadphotosview;

import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.bean.ImageItem;

import java.io.Serializable;

/**
 * 车辆信息
 *
 * @author:Kevin
 */

public class ImageFileInfo implements Serializable {
    public View frame;
    public ImageView iv;
    public ImageView ivAnim;
    public ImageItem imageItem;
    public String id;

    public View getFrame() {
        return frame;
    }

    public void setFrame(View frame) {
        this.frame = frame;
    }

    public ImageView getIvAnim() {
        return ivAnim;
    }

    public void setIvAnim(ImageView ivAnim) {
        this.ivAnim = ivAnim;
    }

    public ImageItem getImageItem() {
        return imageItem;
    }

    public void setImageItem(ImageItem imageItem) {
        this.imageItem = imageItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }


}
