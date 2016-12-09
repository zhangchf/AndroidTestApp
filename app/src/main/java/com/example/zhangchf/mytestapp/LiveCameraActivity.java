package com.example.zhangchf.mytestapp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by zhangchf on 1/22/16.
 */
public class LiveCameraActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;

    private MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);

        setContentView(mTextureView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));

        mediaPlayer = new MediaPlayer();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        Surface s = new Surface(surface);
        Canvas c = s.lockCanvas(new Rect(0, 0, width, height));
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        s.unlockCanvasAndPost(c);
        mediaPlayer.setSurface(s);

/*        mCamera = Camera.open();

        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }*/
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
/*        mCamera.stopPreview();
        mCamera.release();*/
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
    }
}
