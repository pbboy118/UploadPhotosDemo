package ttzg.com.uploadphotosview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import ttzg.com.uploadphotosview.utils.CustomDialogUtil;
import ttzg.com.uploadphotosview.utils.ToastUtil;

/**
 * ClassName: UploadPhotosView
 *
 * @author pb
 * @Description: 选图片上传控件
 * @date 2015-11-16
 */
public class UploadPhotosView extends LinearLayout{
    public static final int IMAGE_PICKER = 0;

    private LinearLayout ll_img;
    private Button btn_addimg;
    private int img_size;
    private int img_maxnum = 5;
    private int img_selectnum;
    private int img_amount = 0;
    private ImagePicker imagePicker;
    private Context context;
    private ArrayList<ImageItem> imageItemList = new ArrayList<>();

    public CopyOnWriteArrayList<ImageFileInfo> getImageFileInfosList() {
        return imageFileInfosList;
    }

    public void setImageFileInfosList(CopyOnWriteArrayList<ImageFileInfo> imageFileInfosList) {
        this.imageFileInfosList = imageFileInfosList;
    }

    public CopyOnWriteArrayList<View> getImageViewList() {
        return imageViewList;
    }

    public void setImageViewList(CopyOnWriteArrayList<View> imageViewList) {
        this.imageViewList = imageViewList;
    }

    private CopyOnWriteArrayList<ImageFileInfo> imageFileInfosList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<View> imageViewList = new CopyOnWriteArrayList<>();
    public static int viewId;

    public HashMap<String, Boolean> getUploadingMap() {
        return uploadingMap;
    }

    public void setUploadingMap(HashMap<String, Boolean> uploadingMap) {
        this.uploadingMap = uploadingMap;
    }

    private HashMap<String, Boolean> uploadingMap = new HashMap<>();

    public ImgUploadListener getImgUploadListener() {
        return imgUploadListener;
    }

    public void setImgUploadListener(ImgUploadListener imgUploadListener) {
        this.imgUploadListener = imgUploadListener;
    }

    private ImgUploadListener imgUploadListener;

    public ArrayList<String> getSuccessFileUrl() {
        return mSuccessFileUrl;
    }

    public void setSuccessFileUrl(ArrayList<String> mSuccessFileUrl) {
        this.mSuccessFileUrl = mSuccessFileUrl;
    }

    private ArrayList<String> mSuccessFileUrl = new ArrayList<>();


    public LinearLayout getLl_img() {
        return ll_img;
    }

    public void setLl_img(LinearLayout ll_img) {
        this.ll_img = ll_img;
    }

    public UploadPhotosView(Context context) {
        super(context);
        init(context);
    }

    public UploadPhotosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UploadPhotosView(Context context, AttributeSet attrs, int viewId, int f) {
        super(context, attrs);
        init(context);
    }

    public UploadPhotosView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        img_size = getWidth() / 5;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void init(final Context context) {

        this.context = context;
        initImageLoader();
        View.inflate(context, R.layout.view_pickimg, this);
        ll_img = (LinearLayout) findViewById(R.id.ll_img);
        btn_addimg = (Button) findViewById(R.id.btn_addimg);

        imageItemList = new ArrayList<>();


        btn_addimg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setImgMaxNum(img_maxnum);
                Intent intent = new Intent(context, ImageGridActivity.class);
                viewId = getId();
                requestFocus(btn_addimg);
                ((Activity) context).startActivityForResult(intent, IMAGE_PICKER);
            }
        });
    }

    public int getImg_amount() {
        return img_amount;
    }

    public void setImg_amount(int img_select) {
        this.img_amount = this.img_amount + img_select;
    }

    private void initImageLoader() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(img_maxnum);    //选中数量限制
        imagePicker.setMultiMode(true);
        //imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        //imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setOutPutX(img_size);//保存文件的宽度。单位像素
        //imagePicker.setOutPutY(img_size);//保存文件的高度。单位像素
    }

    public void handleResult(ArrayList<ImageItem> images, String type) {
        img_selectnum = images.size();
        final ArrayList<ImageFileInfo> filelist = new ArrayList<>();
        if (img_selectnum <= img_maxnum - img_amount) {
            imageItemList.addAll(images);
            for (final ImageItem i : images) {
                ImageView imageView;
                ImageView imageViewAnim;
                View mLoadingView = View.inflate(context, R.layout.loading_imgview, null);
                imageViewAnim = (ImageView) mLoadingView.findViewById(R.id.img_popup);
                imageView = (ImageView) mLoadingView.findViewById(R.id.img_file);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(img_size, img_size);
                mLoadingView.setPadding(15, 0, 15, 0);
                mLoadingView.setLayoutParams(params);
                Animation mAnim = AnimationUtils.loadAnimation(context, R.anim.popup_loading);
                imageViewAnim.startAnimation(mAnim);
                ImageFileInfo ifi = new ImageFileInfo();
                ifi.setIv(imageView);
                ifi.setIvAnim(imageViewAnim);
                ifi.setImageItem(i);
                ifi.setFrame(mLoadingView);
                ifi.setId(UUID.randomUUID().toString());
                imageFileInfosList.add(ifi);
                filelist.add(ifi);
                imageViewList.add(mLoadingView);
                //imagePicker.getImageLoader().displayImage((Activity) context, i.path, imageView, img_size, img_size);
                ll_img.addView(mLoadingView);


                final String finalPath = i.path;
                mLoadingView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!checkUploadStatus()) {
                            Intent it = new Intent(context, BigImageActivity.class);
                            it.putExtra(BigImageActivity.IMAGEINFO, finalPath);
                            context.startActivity(it);
                        } else {
                            ToastUtil.show(context, "上传图片中...");
                        }

                    }
                });
                mLoadingView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!checkUploadStatus()) {
                            final CustomDialogUtil c = new CustomDialogUtil();
                            final View cv = v;
                            c.showDialog(context, null, "确定删除这张图片吗", new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //删除对应的list成员
                                    imageItemList.remove(ll_img.indexOfChild(cv));
                                    if (mSuccessFileUrl.size() > 0)
                                        mSuccessFileUrl.remove(mSuccessFileUrl.get(ll_img.indexOfChild(cv)));
                                    imageViewList.remove(ll_img.indexOfChild(cv));
                                    ll_img.removeView(cv);
                                    img_amount--;
                                    c.dismiss();
                                }
                            });
                            c.show();
                            return false;
                        } else {
                            ToastUtil.show(context, "上传图片中...");
                            return false;
                        }
                        //return false;
                    }
                });
            }
            postFile(filelist, type);
//            for (ImageFileInfo i : filelist) {
//                postFileOnebyOne(i, type);
//            }
            setImg_amount(img_selectnum);
        } else {
            ToastUtil.show(context, "最多上传" + img_maxnum + "张照片");
        }

    }

    private void postFile(final ArrayList<ImageFileInfo> imageitem, String type) {
        final ArrayList<ImageItem> list = new ArrayList<>();
        for (ImageFileInfo i : imageitem) {
            list.add(i.getImageItem());
        }
        uploadingMap.put(imageitem.get(0).getId(), true);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (ImageFileInfo j : imageFileInfosList) {
                    for (ImageFileInfo i : imageitem) {
                        if (j.getId().equals(i.getId())) {
                            j.getIvAnim().clearAnimation();
                            j.getIvAnim().setVisibility(View.GONE);
                            imagePicker.getImageLoader().displayImage((Activity) context, i.getImageItem().path, j.getIv(), img_size, img_size);

                        }
                    }
                }
//        mFileService.uploadFileNoHttp(list, filePostUrl, type, new FileService.UploadFileListenr() {
//            @Override
//            public void onUploadSuccess(ArrayList<String> successFileUrl) {
//
//                for (ImageFileInfo j : imageFileInfosList) {
//                    for (ImageFileInfo i : imageitem) {
//                        if (j.getId().equals(i.getId())) {
//                            j.getIvAnim().clearAnimation();
//                            j.getIvAnim().setVisibility(View.GONE);
//                            imagePicker.getImageLoader().displayImage((Activity) context, i.getImageItem().path, j.getIv(), img_size, img_size);
//
//                        }
//                    }
//                }
                uploadingMap.put(imageitem.get(0).getId(), false);
//                mSuccessFileUrl.addAll(successFileUrl);
//                if (imgUploadListener != null)
//                    imgUploadListener.onUploadSuccess();
//            }
//
//            @Override
//            public void onUploadFailed(String s) {
//                for (ImageFileInfo j : imageFileInfosList) {
//                    for (ImageFileInfo i : imageitem) {
//                        if (j.getId().equals(i.getId())) {
//                            ll_img.removeView(j.getFrame());
//                            imageFileInfosList.remove(j);
//                            img_amount--;
//                        }
//                    }
//                }
//                uploadingMap.put(imageitem.get(0).getId(), false);
//                if (imgUploadListener != null)
//                    imgUploadListener.onUploadFailed();
//
//            }
//        });
    }


    public ArrayList<ImageItem> getImageItemList() {
        return imageItemList;
    }

    /**
     * 设置最大上传数量
     *
     * @param maxNum
     */
    public void setImgMaxNum(int maxNum) {
        img_maxnum = maxNum;
        imagePicker.setSelectLimit(img_maxnum);    //选中数量限制
    }

    /**
     * 获取焦点
     *
     * @param view
     */
    private void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public interface ImgUploadListener {
        void onUploadSuccess();

        void onUploadFailed();
    }

    private boolean checkUploadStatus() {
        Iterator iter = uploadingMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Boolean val = (Boolean) entry.getValue();
            if (val) {
                return true;
            }
        }
        return false;
    }

}

