package com.photoeditor.holiframe;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.util.EthiopicCalendar;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.easystudio.rotateimageview.RotateZoomImageView;


import com.photoeditor.holiframe.util.Util;



import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import petrov.kristiyan.colorpicker.ColorPicker;

public class EditFrameActivity extends BaseActivity implements View.OnClickListener {


    RotateZoomImageView rotateZoomImageView;

    // private InterstitialAd interstitialAd;
    private static final String TAG = "MyActivity";
    ImageView miss_iv_frm;
    LinearLayout llCamera, LlFrame, llFrame, llGallery, llColorFilter, llSeekContrast, llRotate, llSeekColor, llSeekContrastOpacity, llSeekContrastColorfilter, llColorFilter1, miss_iv_text, miss_iv_opacity, miss_iv_contrast, llSticker, miss_iv_sticker;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    RelativeLayout rlzoomimge;
    TextView miss_txtContrastP;
    StickerView stickerView;
    TextSticker textSticker;
    SeekBar seekBarColor, seekBarColorfilter, seekBarOpacity, miss_seekBarContrast;
    RecyclerView rvframe, rvSticker;
    ArrayList<Integer> stickerList = new ArrayList<>();
    ColorMatrix f16692M = new ColorMatrix();

    RelativeLayout miss_main_frm;

    private float mScaleFactor = 1.0f;

    private String filepath;
    private static final int pic_id = 123;
    private int height;
    private int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_frame_activty);
        miss_iv_frm = (ImageView) findViewById(R.id.miss_iv_frm);
        LlFrame = (LinearLayout) findViewById(R.id.LlFrame);
        llFrame = (LinearLayout) findViewById(R.id.llFrame);
        llGallery = (LinearLayout) findViewById(R.id.llGallery);
        llCamera = (LinearLayout) findViewById(R.id.llCamera);
        llSeekColor = (LinearLayout) findViewById(R.id.llSeekColor);
        llSticker = (LinearLayout) findViewById(R.id.llSticker);
        llSeekContrast = (LinearLayout) findViewById(R.id.miss_llSeek);
        miss_iv_sticker = (LinearLayout) findViewById(R.id.miss_iv_sticker);
        rotateZoomImageView = findViewById(R.id.ivZoom);
        llColorFilter1 = (LinearLayout) findViewById(R.id.llColorFilter1);
        miss_iv_opacity = (LinearLayout) findViewById(R.id.miss_iv_opacity);
        miss_iv_contrast = (LinearLayout) findViewById(R.id.miss_iv_contrast);
        miss_iv_text = (LinearLayout) findViewById(R.id.miss_iv_text);
        llSeekContrastOpacity = (LinearLayout) findViewById(R.id.miss_llSeekOpacity);
        llRotate = (LinearLayout) findViewById(R.id.llRotate);
        llSeekContrastColorfilter = (LinearLayout) findViewById(R.id.miss_llSeekColorfilter);
        seekBarColor = (SeekBar) findViewById(R.id.seekBarColor);
        miss_seekBarContrast = (SeekBar) findViewById(R.id.miss_seekBarContrast);
        seekBarColorfilter = (SeekBar) findViewById(R.id.seekBarColorfilter);
        seekBarOpacity = (SeekBar) findViewById(R.id.miss_seekBarOpacity);
        llColorFilter = (LinearLayout) findViewById(R.id.llColorFilter);
        miss_txtContrastP = (TextView) findViewById(R.id.miss_txtContrastP);
        miss_main_frm = findViewById(R.id.miss_main_frm);
        stickerView = (StickerView) findViewById(R.id.stickerview);
        rvframe = (RecyclerView) findViewById(R.id.rvframe);
        rvSticker = (RecyclerView) findViewById(R.id.rvSticker);
        Toolbar toolbar = findViewById(R.id.toolbar);
        rlzoomimge = (RelativeLayout) findViewById(R.id.rlzoomimge);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Happy Holi");
        rvframe.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        FrameAdapter frameAdapter = new FrameAdapter(EditFrameActivity.this, FrameActivity.Framlist);
        rvframe.setAdapter(frameAdapter);

        Intent intent = getIntent();
        int postion = intent.getIntExtra("postion", 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



   /*     height = displayMetrics.heightPixels+270;
        width = displayMetrics.widthPixels+570;*/

        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), FrameActivity.Framlist.get(postion));
        Bitmap bitmap = getResizedBitmap(icon, width, height);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) miss_main_frm.getLayoutParams();
        layoutParams.width = bitmap.getWidth();
        layoutParams.height = bitmap.getHeight();
        miss_main_frm.setLayoutParams(layoutParams);
        miss_main_frm.requestLayout();

      /* RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) miss_iv_frm.getLayoutParams();

        params.height = bitmap.getHeight();
        params.width = bitmap.getWidth();
        this.miss_iv_frm.setLayoutParams(params);
        this.miss_iv_frm.requestLayout();*/
        this.miss_iv_frm.setImageBitmap(bitmap);



        seekBarOpacity.setMax(255);
        seekBarOpacity.setProgress(255);
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());


        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));

        textSticker = new TextSticker(this);


        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                miss_iv_frm.setAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Random random = new Random();
                if (progress == 0) {
                    miss_iv_frm.clearColorFilter();
                } else {
                    miss_iv_frm.setColorFilter(new PorterDuffColorFilter(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)), PorterDuff.Mode.MULTIPLY));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarColorfilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Random random = new Random();
                if (progress == 0) {

                    rotateZoomImageView.clearColorFilter();


                } else {
                    rotateZoomImageView.setColorFilter(new PorterDuffColorFilter(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)), PorterDuff.Mode.OVERLAY));
                      }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        miss_seekBarContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getMatrix(f16692M, ((float) progress) / 180.0f);

               rotateZoomImageView.setColorFilter(new ColorMatrixColorFilter(f16692M));




                miss_txtContrastP.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rvSticker.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stickerList = new ArrayList<>();
        stickerList = Util.StickerList();
        StickerAdapter stickerAdapter = new StickerAdapter(EditFrameActivity.this, stickerList);
        rvSticker.setAdapter(stickerAdapter);
        llSeekContrast.setVisibility(View.GONE);
        llSeekContrastOpacity.setVisibility(View.GONE);
        llSeekContrastColorfilter.setVisibility(View.GONE);
        llSeekColor.setVisibility(View.GONE);
        llSticker.setVisibility(View.GONE);
        llFrame.setVisibility(View.GONE);
        LlFrame.setOnClickListener(this);
        llGallery.setOnClickListener(this);
        miss_iv_contrast.setOnClickListener(this);

        llColorFilter.setOnClickListener(this);


        llColorFilter1.setOnClickListener(this);
        miss_iv_text.setOnClickListener(this);
        miss_iv_opacity.setOnClickListener(this);
        llRotate.setOnClickListener(this);
        llCamera.setOnClickListener(this);
        miss_iv_sticker.setOnClickListener(this);



        rotateZoomImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (rotateZoomImageView.getDrawable() == null) {

                } else {

                    Log.e("event",""+event);
                    return rotateZoomImageView.onTouch(v, event);
                }

                return false;
            }
        });


    }


    @Override
    public void onBackPressed() {
        showResetDialog(EditFrameActivity.this, new OnResetListner() {
            @Override
            public void onReset() {
               finish();


            }
        });

    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();

        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);


        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return resizedBitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
               showResetDialog(EditFrameActivity.this, new OnResetListner() {
                   @Override
                   public void onReset() {
                       finish();
                   }
               });
                return true;
            case R.id.menu_save:


               // new SaveImage().execute();
                stickerView.setLocked(true);
                this.miss_main_frm.setDrawingCacheEnabled(true);
                Bitmap drawingCache = this.miss_main_frm.getDrawingCache();


                try {
                    saveImage(getApplicationContext(),drawingCache,Util.FolderName,Util.IMAGENAME+generateRandomName(1000000, 5000000) + ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
               /* try {
                    Uri imaguri=     saveImage(getApplicationContext(),drawingCache,Util.FolderName,System.currentTimeMillis() + ".jpg");
                    Intent sendintent = new Intent(this, ImageShareActivity.class);
                    sendintent.putExtra("imageUri", imaguri.toString());
                  //  sendintent.putExtra("filepath123", imaguri);
                    startActivity(sendintent);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    private class SaveImage extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... params) {

            try {
                stickerView.setLocked(true);
                miss_main_frm.setDrawingCacheEnabled(true);
                Bitmap drawingCache = miss_main_frm.getDrawingCache();


                saveImageToSD(drawingCache, Util.FolderName + generateRandomName(1000000, 5000000) + ".jpg", Bitmap.CompressFormat.JPEG);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent sendintent = new Intent(EditFrameActivity.this, ImageShareActivity.class);

            sendintent.putExtra("filepath123", filepath);
            startActivity(sendintent);


        }
    }
    private int generateRandomName(int LowerLimit, int UpperLimit) {

        Random r = new Random();
        return r.nextInt((UpperLimit - LowerLimit) + 1) + LowerLimit;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), FrameActivity.Framlist.get(FrameActivity.postion).intValue());
            Bitmap bitmap=getResizedBitmap(icon, width, height);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) miss_main_frm.getLayoutParams();
            layoutParams.width = bitmap.getWidth();
            layoutParams.height = bitmap.getHeight();
            miss_main_frm.setLayoutParams(layoutParams);
            miss_main_frm.requestLayout();
            this.miss_iv_frm.setImageBitmap(bitmap);

        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            rotateZoomImageView.setImageURI(imageUri);



        } else if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()) + "/");
            file.mkdirs();
            File file2 = new File(file, Util.IMAGENAME + System.currentTimeMillis() + ".jpg");
            String path = file2.getPath();
            if (file2.exists()) {
                file2.delete();
            }
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                photo.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream);
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(path)));
            sendBroadcast(intent);

            rotateZoomImageView.setForegroundGravity(Gravity.CENTER);

           rotateZoomImageView.setImageURI(Uri.fromFile(new File(path)));


        }
    }
    public String saveImageToSD(Bitmap bmp, String filename, Bitmap.CompressFormat format) {
        File file2 = null;
        try {
            String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            FileOutputStream fos = null;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(format, 100, bytes);
            File file1 = new File(path1 + "/"+Util.FolderName+"/");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            // Log.e("TAG", "File name : " + file1.getAbsolutePath());
            file2 = new File(file1, filename);
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos = new FileOutputStream(file2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fos.write(bytes.toByteArray());
                fos.close();
                Log.e("Success", "Final Image Saved - " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }


            filepath =  path1 + "/"+Util.FolderName+"/" + filename;


            ContentValues image = new ContentValues();
            String dateStr = "04/05/2010";

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormater.parse(dateStr);
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

            String newDateStr = postFormater.format(dateObj);
            image.put(MediaStore.Images.Media.TITLE, filename);
            image.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            image.put(MediaStore.Images.Media.DESCRIPTION, filename);
            image.put(MediaStore.Images.Media.DATE_ADDED, newDateStr);
            image.put(MediaStore.Images.Media.DATE_TAKEN, "");
            image.put(MediaStore.Images.Media.DATE_MODIFIED, "");
            image.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            image.put(MediaStore.Images.Media.ORIENTATION, 0);

            File parent = file2.getParentFile();
            String path = parent.toString().toLowerCase();
            String name = parent.getName().toLowerCase();
            image.put(MediaStore.Images.ImageColumns.BUCKET_ID, path.hashCode());
            image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, name);
            image.put(MediaStore.Images.Media.SIZE, file2.length());

            image.put(MediaStore.Images.Media.DATA, file2.getAbsolutePath());


            Uri result = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
            filepath="file://"+file2.getPath().toString();
          //  nextactivity();

            return file2.getPath().toString();

        } catch (NullPointerException e) {
            // TODO: handle exception
            Log.e("error", "SAve to disk");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
            private Uri saveImage(Context context, Bitmap bitmap, @NonNull String folderName, @NonNull String fileName) throws IOException {
                OutputStream fos = null;
                File imageFile = null;
                Uri imageUri = null;

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ContentResolver resolver = context.getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                        contentValues.put(
                                MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + folderName);
                        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                        if (imageUri == null)
                            throw new IOException("Failed to create new MediaStore record.");

                        fos = resolver.openOutputStream(imageUri);
                    } else {
                        File imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DCIM).toString() + File.separator + folderName);

                        if (!imagesDir.exists())
                            imagesDir.mkdir();

                        imageFile = new File(imagesDir, fileName );
                        fos = new FileOutputStream(imageFile);
                    }


                    if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos))
                        throw new IOException("Failed to save bitmap.");
                    fos.flush();
                } finally {
                    if (fos != null)
                        fos.close();
                }

                if (imageFile != null) {//pre Q
                    MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
                    imageUri = Uri.fromFile(imageFile);
                }

                return imageUri;


            }

    private void save() {
        stickerView.setLocked(true);
        this.miss_main_frm.setDrawingCacheEnabled(true);
        Bitmap drawingCache = this.miss_main_frm.getDrawingCache();

        File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()) + "/" + Util.FolderName);
        file.mkdirs();
        File file2 = new File(file, Util.IMAGENAME + System.currentTimeMillis() + ".jpg");
        String path = file2.getPath();
        if (file2.exists()) {
            file2.delete();
        }
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);

            drawingCache.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream);
            fileOutputStream.close();
            this.miss_main_frm.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(path)));
        sendBroadcast(intent);
        filepath = path;
        Log.e("path", "" + path);
        nextactivity();


    }

    public void nextactivity() {
        Intent sendintent = new Intent(this, ImageShareActivity.class);

        sendintent.putExtra("filepath123", filepath);
        startActivity(sendintent);
    }

    public static void getMatrix(ColorMatrix colorMatrix, float f) {
        float f2 = f + 1.0f;
        float f3 = ((-0.5f * f2) + 0.5f) * 255.0f;
        colorMatrix.set(new float[]{f2, 0.0f, 0.0f, 0.0f, f3, 0.0f, f2, 0.0f, 0.0f, f3, 0.0f, 0.0f, f2, 0.0f, f3, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LlFrame:

                if (llFrame.getVisibility() == View.VISIBLE) {
                    llFrame.setVisibility(View.INVISIBLE);
                } else {
                    llFrame.setVisibility(View.VISIBLE);
                }

                llSeekContrastOpacity.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);

                break;
            case R.id.llGallery:
                llFrame.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);

                if (checkPermission()) {

                    openGallery();

                } else {

                    if (!checkPermission()) {

                        requestPermission();

                    } else {

                        openGallery();
                    }
                }


                break;
            case R.id.llColorFilter:
                llFrame.setVisibility(View.GONE);
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                if (llSeekColor.getVisibility() == View.VISIBLE) {
                    llSeekColor.setVisibility(View.INVISIBLE);
                } else {

                    llSeekColor.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.llCamera:

                if (checkPermission()) {
                    Intent camera_intent
                            = new Intent(MediaStore
                            .ACTION_IMAGE_CAPTURE);


                    startActivityForResult(camera_intent, pic_id);


                } else {

                    if (!checkPermission()) {

                        requestPermission();

                    } else {
                        Intent camera_intent
                                = new Intent(MediaStore
                                .ACTION_IMAGE_CAPTURE);


                        startActivityForResult(camera_intent, pic_id);


                    }
                }


                break;
            case R.id.llColorFilter1:
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                llFrame.setVisibility(View.GONE);

                if (llSeekContrastColorfilter.getVisibility() == View.VISIBLE) {
                    llSeekContrastColorfilter.setVisibility(View.INVISIBLE);
                } else {
                    llSeekContrastColorfilter.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.miss_iv_text:
                ViewDialog alert = new ViewDialog();
                alert.showDialog(EditFrameActivity.this);
                break;
            case R.id.miss_iv_sticker:

                llSeekContrastOpacity.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);
                llFrame.setVisibility(View.GONE);
                if (llSticker.getVisibility() == View.VISIBLE) {
                    llSticker.setVisibility(View.INVISIBLE);
                } else {
                    llSticker.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.llRotate:
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);

                llSeekColor.setVisibility(View.GONE);

                llFrame.setVisibility(View.GONE);

              rotateZoomImageView.setRotation(rotateZoomImageView.getRotation() + 90.0f);


                break;
            case R.id.miss_iv_opacity:
                llFrame.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);

                llSeekContrastColorfilter.setVisibility(View.GONE);
                llFrame.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);
                llSeekContrast.setVisibility(View.GONE);
                if (llSeekContrastOpacity.getVisibility() == View.VISIBLE) {
                    llSeekContrastOpacity.setVisibility(View.INVISIBLE);
                } else {
                    llSeekContrastOpacity.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.miss_iv_contrast:
                llFrame.setVisibility(View.GONE);
                llSticker.setVisibility(View.GONE);
                llSeekContrastOpacity.setVisibility(View.GONE);
                llSeekContrastColorfilter.setVisibility(View.GONE);
                llFrame.setVisibility(View.GONE);
                llSeekColor.setVisibility(View.GONE);
                if (llSeekContrast.getVisibility() == View.VISIBLE) {
                    llSeekContrast.setVisibility(View.INVISIBLE);
                } else {

                    llSeekContrast.setVisibility(View.VISIBLE);
                }
                break;
        }

    }


    public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {

        public ArrayList<Integer> Framlist;
        Context context;


        public FrameAdapter(Context context, ArrayList<Integer> framlist) {
            this.context = context;
            Framlist = framlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(EditFrameActivity.this).load(Framlist.get(position)).into(holder.ivFrame);

        }


        @Override
        public int getItemCount() {
            return Framlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivFrame;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivFrame = itemView.findViewById(R.id.ivFrame);
                ivFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), FrameActivity.Framlist.get(getBindingAdapterPosition()).intValue());
                        Bitmap bitmap = getResizedBitmap(icon, width, height);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) miss_main_frm.getLayoutParams();
                        layoutParams.width = bitmap.getWidth();
                        layoutParams.height = bitmap.getHeight();
                        miss_main_frm.setLayoutParams(layoutParams);
                        miss_main_frm.requestLayout();
                        miss_iv_frm.setImageBitmap(bitmap);


                    }
                });

            }


        }


    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        public ArrayList<Integer> Stickerlist;
        Context context;


        public StickerAdapter(Context context, ArrayList<Integer> stickerlist) {
            this.context = context;
            Stickerlist = stickerlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Glide.with(EditFrameActivity.this).load(Stickerlist.get(position)).into(holder.ivSticker);

        }


        @Override
        public int getItemCount() {
            return Stickerlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivSticker;
            Sticker sticker;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivSticker = itemView.findViewById(R.id.ivSticker);
                ivSticker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Drawable drawable = ContextCompat.getDrawable(context, Stickerlist.get(getAdapterPosition()));
                        stickerView.addSticker(new DrawableSticker(drawable));


                        stickerView.addSticker(sticker);


                    }
                });

            }


        }


    }

    public class ViewDialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            List asList;
            int selectedcolor;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.newtextdialog);

            EditText edit_new_text_dialog = (EditText) dialog.findViewById(R.id.edit_new_text_dialog);
            Spinner font = (Spinner) dialog.findViewById(R.id.font);
            ImageView ivColor = (ImageView) dialog.findViewById(R.id.ivColor);
            Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
            Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
            ArrayList<String> arrayList = new ArrayList<>();
            asList = Arrays.asList(EditFrameActivity.this.getResources().getStringArray(R.array.fontcategory));
            FondAdapter fondAdapter = new FondAdapter(getApplicationContext(), asList);

            font.setAdapter(fondAdapter);

            font.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AssetManager assets = EditFrameActivity.this.getAssets();
                    Typeface unused = Typeface.createFromAsset(assets, "font/" + ((String) asList.get(position)));
                    edit_new_text_dialog.setTypeface(unused);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ivColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ColorPicker colorPicker = new ColorPicker(EditFrameActivity.this);
                    colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                        @Override
                        public void onChooseColor(int position, int color) {

                            edit_new_text_dialog.setTextColor(color);
                            textSticker.setTextColor(color);
                        }

                        @Override
                        public void onCancel() {
                            // put code
                        }
                    })

                            .disableDefaultButtons(false)

                            .setColumns(5)

                            .show();
                }
            });

            btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String getname;
                    getname = edit_new_text_dialog.getText().toString();
                    if (getname.matches("")) {
                        Toast.makeText(getApplicationContext(), "please enter Text", Toast.LENGTH_LONG).show();

                    } else {
                        textSticker.setText(getname);
                        textSticker.setMaxTextSize(25.5f);
                        textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                        textSticker.resizeText();
                        stickerView.addSticker(textSticker);
                        dialog.dismiss();
                    }

                }
            });


            dialog.show();

        }


        public class FondAdapter extends BaseAdapter {
            LayoutInflater inflter;

            public FondAdapter(Context context, List<String> list) {
                this.context = context;
                this.list = list;
                inflter = (LayoutInflater.from(getApplicationContext()));
            }

            Context context;
            List<String> list;

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return (long) position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View row = inflter.inflate(R.layout.dropdown_item, parent, false);
                TextView textView = (TextView) row.findViewById(R.id.ab_drop_menu_item_title);
                textView.setText(list.get(position).substring(1, list.get(position).length() - 4));
                AssetManager assets = this.context.getAssets();

                textView.setTypeface(Typeface.createFromAsset(assets, "font/" + this.list.get(position)));
                return row;
            }
        }
    }


}