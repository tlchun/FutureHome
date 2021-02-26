package com.goldze.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import androidx.core.content.FileProvider;
import me.goldze.mvvmhabit.utils.ToastUtils;


/**
 * 头像管理工具类： 上传头像，修改头像
 */
public class AvatarManager {

    //保存头像图片的目录名.
    public static final String AVATAR_DIR_NAME = "Avatar";
    //头像临时文件名
    public static final String AVATAR_TEMP_NAME = "temp.jpg";
    public static final String AVATAR_CROP_NAME = "crop.jpg";


    private static final String TAG = "头像管理类";

    private static final String MIME_TYPE_IMAGE = "image/*";

    public static final int REQUEST_CODE_PHOTO_CAMERA = 1;

    public static final int REQUEST_CODE_PHOTO_ALBUM = 2;

    public static final int REQUEST_CODE_PHOTO_COMPRESS = 3;

    private static AvatarManager mAvatarManager;

    public String getCurImageName() {
        return curImageName;
    }

    public void setCurImageName(String curImageName) {
        this.curImageName = curImageName;
    }

    private String curImageName;

    public static Uri mAvatarUri;


    public String getZoomImageSaveName() {
        return zoomImageSaveName;
    }

    public void setZoomImageSaveName(String zoomImageSaveName) {
        this.zoomImageSaveName = zoomImageSaveName;
    }


    private String zoomImageSaveName = "";  //裁剪后的图片保存路径


    public static AvatarManager getInstance() {
        if (mAvatarManager == null) {
            synchronized (AvatarManager.class) {
                if (mAvatarManager == null) {
                    mAvatarManager = new AvatarManager();
                }
            }
        }
        return mAvatarManager;
    }


    /**
     * 打开相册
     */
    public void openGallery(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> listResolveInfos = packageManager
                .queryIntentActivities(intent, 0);
        if (listResolveInfos.size() > 0) {
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MIME_TYPE_IMAGE);
            ((Activity) context).startActivityForResult(intent,
                    REQUEST_CODE_PHOTO_ALBUM);
        } else {
            ToastUtils.showShort("您的设备不支持此功能");
        }
    }

    /**
     * 获取头像保存路径
     *
     * @return
     */
    public String getImagePath() {

        String path = "";
        if (LogSDCardUtil.isSdCardAvailable()) {
            path = LogSDCardUtil.getPersonalSDCradPath(LoginSDKContext.getInstance().getContext());
        } else {
            path = LogSDCardUtil.getDataPath();
        }
        path = path + File.separator + "Avatar";
        return path;
    }


    /**
     * 拍照
     */
    public void takePhoto(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> listResolveInfos = packageManager
                .queryIntentActivities(intent, 0);
        if (listResolveInfos.size() > 0) {
            File file = new File(getImagePath(), System.currentTimeMillis() + AVATAR_TEMP_NAME);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            setCurImageName(file.toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String packageName = SystemInfoUtils.getPackageName(context);
                Uri imageUri = FileProvider.getUriForFile(context, packageName + ".provider", file);//通过FileProvider创建一个content类型的Uri
                mAvatarUri = imageUri;
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                ((Activity) context).startActivityForResult(intent,
                        REQUEST_CODE_PHOTO_CAMERA);
            } else {
                mAvatarUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                ((Activity) context).startActivityForResult(intent,
                        REQUEST_CODE_PHOTO_CAMERA);
            }

        } else {
            ToastUtils.showShort("您的设备不支持此功能");
        }
    }


    /**
     * 调用系统组件缩放图片.
     *
     * @param uri 图片Uri
     */
    public void startZoomPhoto(Activity activity, final Uri uri) {
        String path = getImagePath() + File.separator + AVATAR_CROP_NAME;
        setZoomImageSaveName(path);
        File file = new File(path);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        mAvatarUri = uri;
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("noFaceDetection", true);
        }
        activity.startActivityForResult(intent, REQUEST_CODE_PHOTO_COMPRESS);

    }

    /**
     * uCrop 缩放图片.
     *
     * @param inputUri 图片Uri
     */
    public void startCropPhotoForUCrop(final Uri inputUri, Activity activity) {
        String path = getImagePath() + File.separator + AVATAR_CROP_NAME;
        setZoomImageSaveName(path);
        File file = new File(path);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri outputUri = Uri.fromFile(file);
        UCrop.of(inputUri, outputUri).withAspectRatio(1, 1).start(activity, REQUEST_CODE_PHOTO_COMPRESS);
    }


}
