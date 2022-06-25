package com.example.xulyanh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView= (ImageView) findViewById(R.id.img_mask);
        Bitmap resourceBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.anhdatachnen);
        SmoothAction.context = this;

//        Tao ra mask
        Bitmap maskBlackWhite = SmoothAction.convertBlackWhite(resourceBitmap);
        Bitmap maskSmooth = SmoothAction.blur(maskBlackWhite, 15);
//        Remove background anh theo mask
        Bitmap resultBitmap = SmoothAction.removeBG( maskSmooth, BitmapFactory.decodeResource(getResources(),R.drawable.anhchuatachnen));
        imageView.setImageBitmap(resultBitmap);
    }
}