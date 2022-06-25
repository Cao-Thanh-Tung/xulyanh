package com.example.xulyanh;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.util.Log;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.appcompat.app.AppCompatActivity;

public class SmoothAction {

    static Context context;
    private static RenderScript rs;
    public static Bitmap blur(Bitmap src, int radius) {
        rs = RenderScript.create(context.getApplicationContext());
        final Allocation input = Allocation.createFromBitmap(rs, src);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(src);
        return src;
    }

    public static Bitmap convertBlackWhite(Bitmap bitmap) {
        Bitmap output =  Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint colorFilterMatrixPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorFilterMatrixPaint.setColorFilter(new ColorMatrixColorFilter(new float[] {
                0, 0, 0, 0, 1,
                0, 0, 0, 0, 1,
                0, 0, 0, 0, 1,
                0, 0, 0, 1, 0
        }));
        canvas.drawBitmap(bitmap, 0, 0, colorFilterMatrixPaint);
        return output;
    }

    /**
     * Dung de remove background lai anh theo mask da duoc blur.
     * @param mask : mask da duoc blur
     * @param resourceBitmap : anh goc
     * @return
     */
    public static Bitmap removeBG(Bitmap mask, Bitmap resourceBitmap)
    {
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(),  mask.getConfig());
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mask, new Matrix(), null);
        canvas.drawBitmap(resourceBitmap, 0, 0, paint);
        return result;
    }
}
