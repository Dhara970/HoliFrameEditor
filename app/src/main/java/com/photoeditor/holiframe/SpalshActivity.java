package com.photoeditor.holiframe;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;





public class SpalshActivity extends BaseActivity {
    int SPLAH_IMAGE_DURATION = 1000;

    ImageView mainImage;
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);



        splashImage = (ImageView) findViewById(R.id.splashImageView);
        mainImage = (ImageView) findViewById(R.id.mainBCIMage);
        mainImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.splashImage, "rotationX", 180.0f, 0.0f);
        ofFloat.setDuration((long) this.SPLAH_IMAGE_DURATION);
        ofFloat.start();
        new Handler().postDelayed(new Runnable() { // from class: com.photo.app.collection.happyholiphotoframe2020.-$$Lambda$SplashActivity$zLggbJDmqLSDZbOR1lNvJxyriPU
            @Override // java.lang.Runnable
            public final void run() {
                Intent intent = new Intent(SpalshActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        },  2000);


    }
}