package com.example.zhangchf.mytestapp;

import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.zhangchf.mytestapp.weex.WeexActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_CREATE_CALENDAR_EVENT = 100;

    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    OrientationEventListener orientationEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Surface s = new Surface(surface);
                Canvas c = s.lockCanvas(new Rect(0, 0, width, height));
                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                s.unlockCanvasAndPost(c);
                Log.i("zcf", "onSurfaceTexureAvailable");
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.i("zcf", "onSurfaceTexureDestroyed");
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Log.i("zcf", "onSurfaceTexureUpdated");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent = MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.ef.evc2015");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);*/


                openEvc();

//                startActivity(new Intent("com.ef.evc2015.action.EVC_CLASSROOM"));

/*                downloadReference = downloadData(downloadUri);
                Log.d(TAG, "DownloadReference:" + downloadReference);*/
            }
        });


        orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.i(TAG, "Orientation: " + String.valueOf(orientation));
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        };

        if (orientationEventListener.canDetectOrientation()){
            Log.i(TAG, "Can DetectOrientation");
//            orientationEventListener.enable();
        }
        else{
            Log.i(TAG, "Can not DetectOrientation");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

/*        doFlipAnimation();
        doFlip3DAnimation();
        flipCalendarView();*/

        scheduleNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationEventListener.disable();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CREATE_CALENDAR_EVENT:
                Log.d(TAG, "resultCode=" + resultCode);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDoTest(View v) {

        GsonTest.testGson();

//        new FileLockTest(this);

//        startActivity(new Intent(this, LiveCameraActivity.class));
    }

    String downloadUri = "http://www.englishtown.com.cn/Juno/EvcMobile/app-evc15-live-release-1.2.0.299.apk";
    DownloadManager downloadManager;
    long downloadReference;
    private long downloadData (String uri) {

        Uri downloadUri = Uri.parse(uri);

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFilenameFromUri(uri));

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
//        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.mp3");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);

        return downloadReference;
    }


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.d(TAG, "Download complete, referenceId:" + referenceId);
            if (referenceId == downloadReference) {
                Toast toast = Toast.makeText(MainActivity.this, "Download Complete:" + referenceId, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();


                Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadReference);
                String mimeType = downloadManager.getMimeTypeForDownloadedFile(downloadReference);
                Log.d(TAG, "download file path:" + downloadFileUri + ", mimeType=" + mimeType);
                installApk(downloadFileUri.getPath());


                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(referenceId);
                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        installApk(filePath);
//                        showDownload();
                    }
                }
            }
        }
    };

    private void openEvc() {
        Intent intent = MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.ef.evc2015");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("callingPackage", getPackageName());
        intent.putExtra("callingActivity", getClass().getName());
        MainActivity.this.startActivity(intent);
    }

    public void showDownload() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    public void installApk(String filePath) {
        File f = new File(filePath);
        installApk(Uri.fromFile(f));
    }

    public void installApk(Uri apkUri) {
        Log.d(TAG, "Uri:" + apkUri);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
//        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        installIntent.setDataAndType(apkUri, downloadManager.getMimeTypeForDownloadedFile(downloadReference));
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(installIntent);
    }

    private String getFilenameFromUri(String uriStr) {
        Uri uri = Uri.parse(uriStr);
        return uri.getLastPathSegment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "orientation landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "orientation portrait");
        }
    }

    private void doFlipAnimation() {
        ImageView imgFlipAnimation = (ImageView) findViewById(R.id.img_flip_animation);

        ObjectAnimator animation = ObjectAnimator.ofFloat(imgFlipAnimation, "rotationX", 0, -180);
        animation.setDuration(5000);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
    }


    private void doFlip3DAnimation() {
        final ImageView imgFlip3DAnimation = (ImageView) findViewById(R.id.img_flip_3d_animation);
        imgFlip3DAnimation.post(new Runnable() {
            @Override
            public void run() {
                Flip3DAnimation flipAnimation = new Flip3DAnimation(0, -90, imgFlip3DAnimation.getWidth()/2, imgFlip3DAnimation.getHeight()/2);
                flipAnimation.setDuration(5000);
                flipAnimation.setRepeatMode(Animation.RESTART);
                flipAnimation.setRepeatCount(Animation.INFINITE);

                imgFlip3DAnimation.startAnimation(flipAnimation);
            }
        });
    }

    private void flipCalendarView() {
        final FlipCalendarView flipCalendarView = (FlipCalendarView) findViewById(R.id.flipCalendarView);


//        for(int i = 0; i < 12; i++) {
//            final int finalI = i;
//            flipCalendarView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    flipCalendarView.animateTo(Integer.toString(finalI + 15));
//                }
//            }, i * 4000);
//        }
    }


    public void onOpenKotlinActivity(View view) {
        startActivity(new Intent(this, KotlinActivity.class));
    }

    public void onOpenAndroidActivity(View view) {
        startActivity(new Intent(this, WebTestActivity.class));
    }

    public void onOpenWeexActivity(View view) {
        startActivity(new Intent(this, WeexActivity.class));
    }

    @OnClick(R.id.uiTestActivity)
    public void onClickUITestActivity() {
        startActivity(new Intent(this, UITestActivity.class));
    }

    private void scheduleNotification() {
        Log.i(TAG, "scheduleNotification");
/*        Intent serviceIntent = new Intent(this, NotificationIntentService.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pi = PendingIntent.getService(this, 1000, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);*/

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, -1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pi);

        startService(new Intent(this, AlarmService.class));
    }
}
