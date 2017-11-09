package com.armatys.std.ui;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;
import com.armatys.std.IntentExtraKeys;
import com.armatys.std.R;
import com.armatys.std.ui.logic.LeavingByCornerClickedHandler;

public class PlayerActivity extends Activity implements View.OnTouchListener {
    private static final String TAG = PlayerActivity.class.getName();

    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_player);
        initView();
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.getRootView().setOnTouchListener(this);
        videoView.setOnTouchListener(this);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "onCompletion - will close activity");
                PlayerActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String path = getPath();
        Log.d("Path to movie: ", path);
        if (path != null) {
            videoView.setVideoPath(path);
            videoView.start();
        } else {
            Toast.makeText(this, getString(R.string.player_unknown_movie), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getString(R.string.player_back_pressed), Toast.LENGTH_SHORT).show();
    }

    private String getPath() {
        if (getIntent().getDataString() != null) {
            return getIntent().getDataString();
        }
        return getIntent().getStringExtra(IntentExtraKeys.MOVIE_PATH.getKey());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (LeavingByCornerClickedHandler.shouldExitAfterClicked(event, v)) {
            LeavingByCornerClickedHandler.clearState();
            PlayerActivity.this.finish();
        }
        return true;
    }
}
