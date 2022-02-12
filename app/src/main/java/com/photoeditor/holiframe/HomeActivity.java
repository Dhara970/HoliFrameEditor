package com.photoeditor.holiframe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.BuildConfig;
import com.bumptech.glide.Glide;


public class HomeActivity extends BaseActivity {


    boolean isGallery = false, isCraeation = false;

    ImageView llgallery, llStartCreation, llRateus, llshare, ivbanner;

    private static final String TAG = "MyActivity";
    TextView appname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llgallery = findViewById(R.id.ivGallery);
        llStartCreation = findViewById(R.id.ivcreation);
        llRateus = findViewById(R.id.ivrate);
        llshare = findViewById(R.id.ivshare);
        ivbanner = findViewById(R.id.ivbanner);
        appname = findViewById(R.id.txtappname);
        Glide.with(this).load(getDrawable(R.drawable.holi)).into(ivbanner);

        appname.setText("Happy Holi".toUpperCase());
        TextPaint paint = appname.getPaint();
        float width = paint.measureText("Happy Holi");

        Shader textShader = new LinearGradient(0, 0, width, appname.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#C33764"),
                        Color.parseColor("#89253e"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        appname.getPaint().setShader(textShader);

        requestPermission();


        llgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGallery = true;


                Intent intent = new Intent(HomeActivity.this, FrameActivity.class);
                startActivity(intent);


            }
        });
        llStartCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCraeation = true;
                Intent intent = new Intent(HomeActivity.this, CreationActivity.class);
                startActivity(intent);

            }
        });
        llRateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });
        llshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }


}