package com.jaedongchicken.ytplayer_sample;

import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;
import com.jaedongchicken.ytplayer_sample.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class MainAbility extends Ability {

    private Text currentSec;
    private YoutubePlayerView youtubePlayerView;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        currentSec = (Text) findComponentById(ResourceTable.Id_currentSec);

        // get id from XML
        youtubePlayerView = (YoutubePlayerView) findComponentById(ResourceTable.Id_youtubePlayerView);

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);

        // if you want to change white backgrond, #default is black.
        // youtubePlayerView.setWhiteBackgroundColor();

        // Control values : see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0);
        // params.setAutoplay(1);
        params.setVolume(100);
        params.setPlaybackQuality(PlaybackQuality.SMALL);

        currentSec.setText(String.valueOf(0));

        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // youtubePlayerView.initializeWithUrl("https://www.youtube.com/watch?v=dxWvtMOGAhw", params, new YoutubePlayerView.YouTubeListener())

        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // To Use - avoid UMG block!!!! but you'd better make own your server for your real service.
        // youtubePlayerView.initializeWithCustomURL("p1Zt47V3pPw" or "http://jaedong.net/youtube/p1Zt47V3pPw", params, new YoutubePlayerView.YouTubeListener())

        // Have to use old version user, if you have already set own your handler.
        // youtubePlayerView.setHandlerDisable();

        youtubePlayerView.initializeWithCustomURL("B2zhLYz4pYo", params, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                JLog.i("onReady()");
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */

                JLog.i("onStateChange(" + state + ")");
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
                String message = "onPlaybackQualityChange(" + arg + ")";
                Util.createToast(MainAbility.this, message).show();
            }

            @Override
            public void onPlaybackRateChange(String arg) {
                String message = "onPlaybackRateChange(" + arg + ")";
                Util.createToast(MainAbility.this, message).show();
            }

            @Override
            public void onError(String arg) {
                String message = "onError(" + arg + ")";
                Util.createToast(MainAbility.this, message).show();
            }

            @Override
            public void onApiChange(String arg) {
                String message = "onApiChange(" + arg + ")";
                Util.createToast(MainAbility.this, message).show();
            }

            @Override
            public void onCurrentSecond(double second) {
                currentSec.setText(String.valueOf(second));
            }

            @Override
            public void onDuration(double duration) {
                String message = "onDuration(" + duration + ")";
                JLog.i(message);
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
                JLog.d(log);
            }
        });

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        youtubePlayerView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        youtubePlayerView.onDestroy();
    }
}
