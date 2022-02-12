package com.photoeditor.holiframe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.photoeditor.holiframe.util.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class CreationActivity extends BaseActivity {
    RecyclerView rvImages;
    ArrayList<ImagesDetails> filelist;
    File[] listFile;
    ImageAdapter imageAdapter;

    MaterialTextView txtnodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        rvImages = findViewById(R.id.rvImges);

        txtnodata = findViewById(R.id.txtnodata);
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Creation");
        filelist = new ArrayList<ImagesDetails>();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        fillData();
        rvImages.setLayoutManager(layoutManager);




    }





    public ArrayList<File> getAllimages(File file) {

        ArrayList<File> arrayList = new ArrayList<>();
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            arrayList.addAll(Arrays.asList(listFiles));
        }

        int i = 0;
        if (arrayList.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            while (i < arrayList.size()) {
                arrayList2.add(Uri.fromFile(arrayList.get(i)));
                i++;
            }
            imageAdapter = new ImageAdapter(CreationActivity.this, arrayList2);
            rvImages.setAdapter(imageAdapter);


        }
        return arrayList;
    }
















    private void fillData() {

        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + Util.FolderName;

            File f = new File(path);
            File file[] = f.listFiles();
            String selection = null;
            String[] selectionArgs = null;
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            if (file.length > 0) {
                for (int i = 0; i < file.length; i++) {
                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);



                        ImagesDetails object = new ImagesDetails();
                        object.ImageName = file[i].getName();
                        object.uri = Uri.fromFile(file[i]);
                        filelist.add(object);
                        imageAdapter = new ImageAdapter(CreationActivity.this, filelist);
                        rvImages.setAdapter(imageAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {
        }
    }










    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        Context context;
        ArrayList<ImagesDetails> imagelist;

        public ImageAdapter(Context context, ArrayList<ImagesDetails> imagelist) {
            this.context = context;
            this.imagelist = imagelist;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Glide.with(context).load(imagelist.get(position).getUri()).into(holder.ivImges);

        }

        @Override
        public int getItemCount() {
            return imagelist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImges;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImges = itemView.findViewById(R.id.ivFrame);
                ivImges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendintent = new Intent(context, ImageShareActivity.class);
                        sendintent.putExtra("filepath123", imagelist.get(getBindingAdapterPosition()).getUri());
                        startActivity(sendintent);
                    }
                });
            }
        }
    }



}