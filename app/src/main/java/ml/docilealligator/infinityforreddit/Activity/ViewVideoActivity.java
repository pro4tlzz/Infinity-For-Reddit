package ml.docilealligator.infinityforreddit.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.pwittchen.swipe.library.rx2.SimpleSwipeListener;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import ml.docilealligator.infinityforreddit.R;

public class ViewVideoActivity extends AppCompatActivity {

    public static final String TITLE_KEY = "TK";
    public static final String SUBREDDIT_KEY = "SK";
    public static final String ID_KEY = "IK";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    @BindView(R.id.relative_layout_view_video_activity)
    RelativeLayout relativeLayout;
    @BindView(R.id.player_view_view_video_activity)
    PlayerView videoPlayerView;

    private Uri mVideoUri;
    private SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;

    private Menu mMenu;
    private Swipe swipe;

    private boolean wasPlaying;
    private boolean isDownloading = false;
    private float totalLengthY = 0.0f;
    private float touchY = -1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.Theme_Default, true);

        setContentView(R.layout.activity_view_video);
        ButterKnife.bind(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ActionBar actionBar = getSupportActionBar();
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparentActionBarAndExoPlayerControllerColor)));
        setTitle("");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || getResources().getBoolean(R.bool.isTablet)) {
            //Set player controller bottom margin in order to display it above the navbar
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            LinearLayout controllerLinearLayout = findViewById(R.id.linear_layout_exo_playback_control_view);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) controllerLinearLayout.getLayoutParams();
            params.bottomMargin = getResources().getDimensionPixelSize(resourceId);
        } else {
            //Set player controller right margin in order to display it above the navbar
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            LinearLayout controllerLinearLayout = findViewById(R.id.linear_layout_exo_playback_control_view);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) controllerLinearLayout.getLayoutParams();
            params.rightMargin = getResources().getDimensionPixelSize(resourceId);
        }

        Intent intent = getIntent();
        mVideoUri = intent.getData();

        final float pxHeight = getResources().getDisplayMetrics().heightPixels;

        int activityColorFrom = getResources().getColor(android.R.color.black);
        int actionBarColorFrom = getResources().getColor(R.color.transparentActionBarAndExoPlayerControllerColor);
        int actionBarElementColorFrom = getResources().getColor(android.R.color.white);
        int colorTo = getResources().getColor(android.R.color.transparent);

        final ValueAnimator activityColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), activityColorFrom, colorTo);
        final ValueAnimator actionBarColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), actionBarColorFrom, colorTo);
        final ValueAnimator actionBarElementColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), actionBarElementColorFrom, colorTo);

        activityColorAnimation.setDuration(300); // milliseconds
        actionBarColorAnimation.setDuration(300);
        actionBarElementColorAnimation.setDuration(300);

        activityColorAnimation.addUpdateListener(valueAnimator -> relativeLayout.setBackgroundColor((int) valueAnimator.getAnimatedValue()));

        actionBarColorAnimation.addUpdateListener(valueAnimator -> actionBar.setBackgroundDrawable(new ColorDrawable((int) valueAnimator.getAnimatedValue())));

        actionBarElementColorAnimation.addUpdateListener(valueAnimator -> {
            upArrow.setColorFilter((int) valueAnimator.getAnimatedValue(), PorterDuff.Mode.SRC_IN);
            if (mMenu != null) {
                Drawable drawable = mMenu.getItem(0).getIcon();
                drawable.setColorFilter((int) valueAnimator.getAnimatedValue(), PorterDuff.Mode.SRC_IN);
            }
        });

        swipe = new Swipe();
        swipe.setListener(new SimpleSwipeListener() {
            @Override
            public void onSwipingUp(final MotionEvent event) {
                float nowY = event.getY();
                float offset;
                if (touchY == -1.0f) {
                    offset = 0.0f;
                } else {
                    offset = nowY - touchY;
                }
                totalLengthY += offset;
                touchY = nowY;
                videoPlayerView.animate()
                        .y(totalLengthY)
                        .setDuration(0)
                        .start();
            }

            @Override
            public boolean onSwipedUp(final MotionEvent event) {
                videoPlayerView.animate()
                        .y(0)
                        .setDuration(300)
                        .start();

                if (totalLengthY < -pxHeight / 8) {
                    videoPlayerView.animate()
                            .y(-pxHeight)
                            .setDuration(300)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                    activityColorAnimation.start();
                                    actionBarColorAnimation.start();
                                    actionBarElementColorAnimation.start();
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    finish();
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            })
                            .start();
                } else {
                    videoPlayerView.animate()
                            .y(0)
                            .setDuration(300)
                            .start();
                }

                totalLengthY = 0.0f;
                touchY = -1.0f;
                return false;
            }

            @Override
            public void onSwipingDown(final MotionEvent event) {
                float nowY = event.getY();
                float offset;
                if (touchY == -1.0f) {
                    offset = 0.0f;
                } else {
                    offset = nowY - touchY;
                }
                totalLengthY += offset;
                touchY = nowY;
                videoPlayerView.animate()
                        .y(totalLengthY)
                        .setDuration(0)
                        .start();
            }

            @Override
            public boolean onSwipedDown(final MotionEvent event) {
                if (totalLengthY > pxHeight / 8) {
                    videoPlayerView.animate()
                            .y(pxHeight)
                            .setDuration(300)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                    activityColorAnimation.start();
                                    actionBarColorAnimation.start();
                                    actionBarElementColorAnimation.start();
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    finish();
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            })
                            .start();
                } else {
                    videoPlayerView.animate()
                            .y(0)
                            .setDuration(300)
                            .start();
                }

                totalLengthY = 0.0f;
                touchY = -1.0f;

                return false;
            }
        });

        videoPlayerView.setControllerVisibilityListener(visibility -> {
            switch (visibility) {
                case View.GONE:
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    break;
                case View.VISIBLE:
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        videoPlayerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "Infinity"));
        // Prepare the player with the source.
        player.prepare(new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mVideoUri));

        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.setPlayWhenReady(true);
        wasPlaying = true;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_video, menu);
        mMenu = menu;
        return true;
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        swipe.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_download_view_video:
                isDownloading = true;
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Permission is not granted
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                    } else {
                        // Permission has already been granted
                        download();
                    }
                } else {
                    download();
                }
                return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasPlaying) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasPlaying = player.getPlayWhenReady();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, R.string.no_storage_permission, Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && isDownloading) {
                download();
            }
            isDownloading = false;
        }
    }

    private void download() {
        isDownloading = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
