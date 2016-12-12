package com.mohsin.exoplayersimpleapp;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        final DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(handler, null);
        System.out.println("bandwidthMeter.getBitrateEstimate()bandwidthMeter.getBitrateEstimate():"+bandwidthMeter.getBitrateEstimate());
        /*MediaSource sampleSource = new ExtractorMediaSource(
                Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0bf97f815217cd5784e/vod/57bca0bf97f815217cd5784e.m3u8"),
                new DefaultDataSourceFactory(this, "Android-ExoPlayer", bandwidthMeter),
                new DefaultExtractorsFactory(), null, null);*/
        MediaSource sampleSource = new HlsMediaSource(
                Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0bf97f815217cd5784e/vod/57bca0bf97f815217cd5784e.m3u8"),
                new DefaultDataSourceFactory(this, "Android-ExoPlayer", bandwidthMeter),
                1, null, null);

        if (player == null) {
            Handler mainHandler = new Handler();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        }

        player.prepare(sampleSource);
        player.setVideoTextureView((TextureView)findViewById(R.id.textureView));
        player.setPlayWhenReady(true);

        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == ExoPlayer.STATE_BUFFERING) {
                    System.out.println("buffering bandwidthMeter.getBitrateEstimate():"+bandwidthMeter.getBitrateEstimate());
                }
                if(playbackState == ExoPlayer.STATE_READY) {
                    System.out.println("ready bandwidthMeter.getBitrateEstimate():"+bandwidthMeter.getBitrateEstimate());
                }
                if(playbackState == ExoPlayer.STATE_ENDED) {
                    System.out.println("ExoPlayer.STATE_ENDEDExoPlayer.STATE_ENDEDExoPlayer.STATE_ENDED");
                    player.seekTo(0);
                    player.setPlayWhenReady(true);
                }
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
    }
}
