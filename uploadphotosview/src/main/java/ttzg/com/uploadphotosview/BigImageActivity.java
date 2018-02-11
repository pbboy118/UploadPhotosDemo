package ttzg.com.uploadphotosview;

import android.app.Activity;
import android.os.Bundle;
import com.lzy.imagepicker.ImagePicker;

import ttzg.com.uploadphotosview.utils.ImageNoHttpLoader;

/**
 * @author: xin.wu
 * @create time: 2016/4/19 14:03
 * @TODO: 描述
 */
public class BigImageActivity extends Activity {

    public static final String IMAGEINFO = "imageinfo";
    public static final String TITLE = "title";
    private TouchImageView imageView;
    private String imagePath;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimage);
        imagePath = getIntent().getStringExtra(IMAGEINFO);
        initViews();

    }


    private void initViews() {
        imageView = (TouchImageView) findViewById(R.id.big_img);
        ImageNoHttpLoader.displayImage(this, imagePath, imageView);
    }
}
