package com.jiangkang.ktools;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.jiangkang.ktools.image.GalleryActivity;
import com.jiangkang.ktools.web.WebActivity;
import com.jiangkang.tools.permission.RxPermissions;
import com.jiangkang.tools.utils.FileUtils;
import com.jiangkang.tools.utils.ImageUtils;
import com.jiangkang.tools.utils.ToastUtils;
import com.jiangkang.tools.widget.KDialog;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = ImageActivity.class.getSimpleName();

    private static final int REQUEST_OPEN_ALBUM = 1000;

    private static final int REQUEST_IMAGE_CAPTURE = 1001;

    private static final int REQUEST_CODE_CAPTURE_IMAGE_WITHOUT_COMPRESS = 1002;

    private static final int REQUEST_CODE_TAKE_VIDEO = 1003;

    @BindView(R.id.btn_choose_picture_from_album)
    Button btnChoosePictureFromAlbum;

    Unbinder unbinder;
    @BindView(R.id.btn_take_picture)
    Button btnTakePicture;

    @BindView(R.id.btn_take_video)
    Button btnTakeVideo;

    @BindView(R.id.btn_screen_capture)
    Button btnScreenCapture;

    RxPermissions rxPermissions;

    @BindView(R.id.btn_show_base64_img_in_web)
    Button btnShowBase64ImgInWeb;
    @BindView(R.id.btn_scale_image_by_max_width_and_height)
    Button btnScaleImageByMaxWidthAndHeight;
    @BindView(R.id.et_max_width)
    EditText etMaxWidth;
    @BindView(R.id.et_max_height)
    EditText etMaxHeight;
    @BindView(R.id.btn_image_gallery)
    Button btnImageGallery;

    private File outputImageFile;

    private File outputVideoFile;

    @BindView(R.id.btn_take_picture_without_compress)
    Button btnTakePictureWithoutCompress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setTitle("Image");

        initVar();

        unbinder = ButterKnife.bind(this);
    }

    private void initVar() {
        rxPermissions = new RxPermissions(this);
    }

    @OnClick(R.id.btn_choose_picture_from_album)
    public void onBtnChoosePictureFromAlbumClicked() {
        openAlbum();
    }

    private void openAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setType("image/*");
        startActivityForResult(albumIntent, REQUEST_OPEN_ALBUM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_OPEN_ALBUM:
                if (resultCode == RESULT_OK) {
                    handleAlbumData(data);
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    handleImageCaptureData(data);
                }
                break;
            case REQUEST_CODE_CAPTURE_IMAGE_WITHOUT_COMPRESS:
                if (resultCode == RESULT_OK) {
                    handleImageCaptureWithoutCompress(data);
                }
                break;
            case REQUEST_CODE_TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    handleVideoData(data);
                }
                break;
            default:
                break;
        }
    }

    private void handleVideoData(Intent data) {
        showVideoInDialog(outputVideoFile);
    }

    private void showVideoInDialog(File file) {
        final VideoView videoView = new VideoView(this);
        videoView.setVideoPath(file.getAbsolutePath());
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(videoView)
                .setPositiveButton("播放", null)
                .setNeutralButton("暂停", null)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoView.start();
                    }
                });

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoView.pause();
                    }
                });


    }

    private void handleImageCaptureWithoutCompress(Intent data) {
        final Bitmap bitmap = BitmapFactory.decodeFile(outputImageFile.getAbsolutePath());
        KDialog.showImgInDialog(this, bitmap);
    }

    private void handleImageCaptureData(Intent data) {
        //这张图是经过压缩的，清晰度低
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        KDialog.showImgInDialog(this, bitmap);
    }

    private void handleAlbumData(Intent data) {
        Uri uri = data.getData();
        String[] projection = new String[]{
                MediaStore.Images.Media.DATA
        };

        Cursor cursor = getContentResolver().query(
                uri,
                projection,
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(dataIndex);
            cursor.close();
            showBitmap(imagePath);
        }

    }

    private void showBitmap(String imagePath) {
        ToastUtils.showShortToast(imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        KDialog.showImgInDialog(this, bitmap);
    }

    @OnClick(R.id.btn_take_picture)
    public void onBtnTakePictureClicked() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            openCamera();
                        } else {
                            ToastUtils.showShortToast("权限被拒绝");
                        }
                    }

                });

    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    @OnClick(R.id.btn_take_video)
    public void onBtnTakeVideoClicked() {
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            takeVideo();
                        } else {
                            ToastUtils.showShortToast("权限被拒绝");
                        }
                    }
                });

    }

    private void takeVideo() {
        File dirVideo = new File(Environment.getExternalStorageDirectory(), "ktools/videos/");
        if (!dirVideo.exists()) {
            dirVideo.mkdirs();
        }
        outputVideoFile = new File(dirVideo.getAbsolutePath() + System.currentTimeMillis() + ".mp4");
        if (!outputVideoFile.exists()) {
            try {
                outputVideoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //兼容7.0
        Uri videoUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID,
                outputVideoFile
        );
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //指定输出
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3000);
        startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
    }

    @OnClick(R.id.btn_screen_capture)
    public void onBtnScreenCaptureClicked() {
        View decorView = getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        decorView.buildDrawingCache();
        Bitmap screen = Bitmap.createBitmap(decorView.getDrawingCache());
        KDialog.showImgInDialog(this, screen);
    }

    @OnClick(R.id.btn_take_picture_without_compress)
    public void onBtnTakePictureWithoutCompressClicked() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            openCameraWithOutput();
                        } else {
                            ToastUtils.showShortToast("权限被拒绝");
                        }
                    }

                });
    }

    private void openCameraWithOutput() {
        String path = new File(Environment.getExternalStorageDirectory(), "ktools").getAbsolutePath();
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        outputImageFile = new File(path, System.currentTimeMillis() + ".png");
        if (!outputImageFile.exists()) {
            try {
                outputImageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //兼容7.0
        Uri contentUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID,
                outputImageFile
        );
        Log.d(TAG, "openCameraWithOutput: uri = " + contentUri.toString());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"));
        //指定输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE_WITHOUT_COMPRESS);
    }


    @OnClick(R.id.btn_show_base64_img_in_web)
    public void onBtnShowBase64ImgInWebClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("launchUrl", FileUtils.getAssetsPath("web/demo_img.html"));
        WebActivity.launch(this, bundle);
    }


    @OnClick(R.id.btn_scale_image_by_max_width_and_height)
    public void onBtnScaleImageByMaxWidthAndHeightClicked() {
        Bitmap srcBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.demo);
        int maxWidth = srcBitmap.getWidth();
        int maxHeight = srcBitmap.getHeight();
        if (!TextUtils.isEmpty(etMaxWidth.getText().toString()) && TextUtils.isDigitsOnly(etMaxWidth.getText().toString())) {
            maxWidth = Integer.parseInt(etMaxWidth.getText().toString());
        }
        if (!TextUtils.isEmpty(etMaxHeight.getText().toString()) && TextUtils.isDigitsOnly(etMaxHeight.getText().toString())) {
            maxHeight = Integer.parseInt(etMaxHeight.getText().toString());
        }
        Bitmap scaledBitmap = ImageUtils.scaleBitmap(srcBitmap, maxWidth, maxHeight);
        if (scaledBitmap != null) {
            KDialog.showImgInDialog(this, scaledBitmap);
        }
    }

    @OnClick(R.id.btn_image_gallery)
    public void onImageGalleryClicked() {
        GalleryActivity.launch(this,null);
    }
}
