package com.photoeditor.holiframe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import com.photoeditor.holiframe.util.Util;


import java.util.ArrayList;

public class FrameActivity extends BaseActivity {
    RecyclerView rvFrame;
    public static int postion;
    public static ArrayList<Integer> Framlist;



    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        rvFrame = findViewById(R.id.rvFrame);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Happy Holi");

        Framlist = new ArrayList<>();
        Framlist = Util.FrameList();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvFrame.setLayoutManager(layoutManager);
        FrameAdapter frameAdapter = new FrameAdapter(FrameActivity.this, Framlist);
        rvFrame.setAdapter(frameAdapter);

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Glide.with(context).load(Framlist.get(position)).into(holder.ivFrame);


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
                        postion=getBindingAdapterPosition();
                        Intent intent=new Intent(context, EditFrameActivity.class);

                        intent.putExtra("postion",getBindingAdapterPosition());
                        context.startActivity(intent);


                    }
                });

            }


        }


    }

}