package com.armatys.std.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
import com.armatys.std.IntentExtraKeys;
import com.armatys.std.R;

public class PlayerActivity extends Activity {
    private static final String TAG = PlayerActivity.class.getName();

    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "Event: " + event.getX() + " " + event.getY());
                Log.d(TAG, "View:  " + videoView.getWidth() + " " + videoView.getHeight());
                return false;
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

    private String getPath() {
        if (getIntent().getDataString() != null) {
            return getIntent().getDataString();
        }
        return getIntent().getStringExtra(IntentExtraKeys.MOVIE_PATH.getKey());
    }
}
