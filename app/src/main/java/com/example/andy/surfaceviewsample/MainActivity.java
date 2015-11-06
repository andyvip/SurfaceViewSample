package com.example.andy.surfaceviewsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        MySurfaceView mySurfaceView = new MySurfaceView(this);

        FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lytp.gravity = Gravity.CENTER;
        lytp.setMargins(50, 50, 50, 50);
        mySurfaceView.setLayoutParams(lytp);
        frameLayout.addView(mySurfaceView);
    }

    public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        SurfaceHolder mHolder;

        public MySurfaceView(Context context) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            new Thread(new MyDrawThread()).start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        class MyDrawThread implements Runnable {

            @Override
            public void run() {
                Canvas canvas = null;
                while (true) {
                    try {
                        canvas = mHolder.lockCanvas();
                        Paint paint = new Paint();
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
                        float scaleW = (float) canvas.getWidth() / bitmap.getWidth();
                        float scaleH = (float) canvas.getHeight() / bitmap.getHeight();
                        float scale = scaleW < scaleH ? scaleW : scaleH;
                        float x = (canvas.getWidth() - bitmap.getWidth() * scale) / 2;
                        float y = (canvas.getHeight() - bitmap.getHeight() * scale) / 2;
                        RectF rect = new RectF(x, y, bitmap.getWidth() * scale + x, bitmap.getHeight() * scale + y);
                        canvas.drawBitmap(bitmap, null, rect, paint);
                        //Thread.sleep(10000);
                    } catch (Exception e) {

                    } finally {
                        if (canvas != null) {
                            mHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
    }
}
