package com.photoeditor.holiframe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.photoeditor.holiframe.util.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImageShareActivity extends BaseActivity {
    ImageView imgfinal;
    Uri myUri;
    String path;
    private static final String t = "MediaUtils";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_share);
        imgfinal = (ImageView) findViewById(R.id.ivFinal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Happy Holi");
        Bundle extras = getIntent().getExtras();
        path=extras.get("filepath123").toString();

        myUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(path));

        Log.e("myimageuri", "" + path);
        Log.e("myimageuri1", "" + myUri);
        Glide.with(this).load(path).fitCenter().into(imgfinal);
        //imgfinal.setImageURI(Uri.parse(path));

    }

    public void Shareimage() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.photoeditor.holiframe"+ ".provider", new File(this.path));
        Log.e("sharuri",""+photoURI);
       shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
      //  shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share images..."));

    }

    public void delete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageShareActivity.this);
        builder.setMessage("Are you sure  delete image...");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                File fdelete = new File(path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {


                        Intent intent = new Intent(ImageShareActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else {
                        finish();
                        System.out.println("file not Deleted :" + myUri.getPath());
                    }
                }


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_share:
                Shareimage();
                return true;
            case R.id.menu_delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}