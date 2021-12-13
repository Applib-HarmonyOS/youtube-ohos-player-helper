package com.jaedongchicken.ytplayer;

import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.AttrSet;
import ohos.agp.components.webengine.*;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayAttributes;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Optional;

public class YoutubePlayerView extends WebView {

    private QualsonBridge bridge = new QualsonBridge();

    private YTParams params = new YTParams();

    private YouTubeListener youTubeListener;
    private String backgroundColor = "#000000";
    private final String customURL = "https://www.youtube.com/watch?v=";

    private Context context;

    private boolean isCustomDomain = false;
    private boolean isHandlerEnable = true;

    public YoutubePlayerView(Context context) {
        super(context);
        this.context = context;
        setWebAgent(new MyWebViewClient((Ability) context));
    }

    public YoutubePlayerView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        this.context = context;
        setWebAgent(new MyWebViewClient((Ability) context));
    }


    public void initialize(String videoId, YouTubeListener youTubeListener) {
        WebConfig set = this.getWebConfig();
        set.setJavaScriptPermit(true);
        set.setViewPortFitScreen(true);

        //TODO : API available in SDK7
        //set.setAutoFitOnLoad(true);
        //============================

        //set.setLoadWithOverviewMode(true);
        set.setTextAutoSizing(true);
        //set.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //set.setPluginState(WebSettings.PluginState.ON);
        //set.setPluginState(WebSettings.PluginState.ON_DEMAND);
        set.setDataAbilityPermit(true);
        //set.setAllowFileAccess(true);

        this.youTubeListener = youTubeListener;
        //this.setLayerType(View.LAYER_TYPE_NONE, null);
        estimateSize(EstimateSpec.UNCONSTRAINT, EstimateSpec.UNCONSTRAINT);
        this.addJsCallback("QualsonInterface", bridge);
        this.setLongClickable(true);
        this.setBrowserAgent(new BrowserAgent(context));
        this.setWebAgent(new QWebViewClient());

        if (isCustomDomain) {
            this.load(videoId);
        } else {

            this.load("http://www.youtube.com", getVideoHTML(videoId), "text/html", "utf-8", null);
        }
    }

    public void initialize(String videoId, YTParams params, YouTubeListener youTubeListener) {
        if (params != null) {
            this.params = params;
        }
        initialize(videoId, youTubeListener);
    }

    public void initializeWithUrl(String videoUrl, YTParams params, YouTubeListener youTubeListener) {
        if (params != null) {
            this.params = params;
        }
        String videoId = videoUrl.substring(videoUrl.indexOf('=') + 1);
        initialize(videoId, youTubeListener);
    }

    public void initializeWithCustomURL(String videoId, YTParams params, YouTubeListener youTubeListener) throws NullPointerException{
        if (params != null) {
            this.params = params;
            isCustomDomain = true;
            String webCustomUrl = customURL.concat(videoId);
            if (videoId.startsWith("http") || videoId.startsWith("https")) {
                webCustomUrl = videoId;
            }
            initialize(webCustomUrl.concat(params.toString()), youTubeListener);
        }
    }

    public void setWhiteBackgroundColor() {
        backgroundColor = "#ffffff";
    }

    public void setHandlerDisable() {
        isHandlerEnable = false;
    }

    public void setAutoPlayerHeight(Context context) {
        Optional<Display> display = DisplayManager.getInstance().getDefaultDisplay(context);
        DisplayAttributes displayAttributes = display.get().getAttributes();
        this.getLayoutConfig().height = (int) (displayAttributes.width * 0.5625);
    }

    public void setAutoPlayerHeight() {
        setAutoPlayerHeight(context);
    }

    /**
     * APP TO WEB
     */
    public void seekToMillis(double mil) {
        JLog.d("seekToMillis : ");
        this.load("javascript:onSeekTo(" + mil + ")");
    }

    public void pause() {
        JLog.d("pause");
        this.load("javascript:onVideoPause()");
    }

    public void play() {
        JLog.d("play");
        this.load("javascript:onVideoPlay()");
    }

    public void onLoadVideo(String videoId, float mil) {
        JLog.d("onLoadVideo : " + videoId + ", " + mil);
        this.load("javascript:loadVideo('" + videoId + "', " + mil + ")");
    }

    public void onCueVideo(String videoId) {
        JLog.d("onCueVideo : " + videoId);
        this.load("javascript:cueVideo('" + videoId + "')");
    }

    public void playFullscreen() {
        Optional<Display> display = DisplayManager.getInstance().getDefaultDisplay(context);
        DisplayAttributes displayAttributes = display.get().getAttributes();
        this.getLayoutConfig().height = (int) (displayAttributes.width * 0.5625);

        JLog.d("playFullscreen");
        this.load("javascript:playFullscreen(" + displayAttributes.width + ", " + displayAttributes.height + ")");
    }


    /**
     * WEB TO APP
     */
    private class QualsonBridge implements JsCallback {


        public void onReady(String arg) {
            JLog.d("onReady(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onReady();
            }
        }


        public void onStateChange(String arg) {
            JLog.d("onStateChange(" + arg + ")");
            if (youTubeListener != null) {
                if ("UNSTARTED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.UNSTARTED);
                } else if ("ENDED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.ENDED);
                } else if ("PLAYING".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.PLAYING);
                } else if ("PAUSED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.PAUSED);
                } else if ("BUFFERING".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.BUFFERING);
                } else if ("CUED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.CUED);
                }
            }
        }


        public void onPlaybackQualityChange(String arg) {
            JLog.d("onPlaybackQualityChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onPlaybackQualityChange(arg);
            }
        }


        public void onPlaybackRateChange(String arg) {
            JLog.d("onPlaybackRateChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onPlaybackRateChange(arg);
            }
        }


        public void onError(String arg) {
            JLog.e("onError(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onError(arg);
            }
        }


        public void onApiChange(String arg) {
            JLog.d("onApiChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onApiChange(arg);
            }
        }


        public void currentSeconds(String seconds) {
            if (youTubeListener != null) {
                // currentTime callback
                float second = Float.parseFloat(seconds);
                if (isHandlerEnable) {
                    InnerEvent innerEvent = InnerEvent.get(100, second);
                    handler.sendEvent(innerEvent);
                } else {
                    youTubeListener.onCurrentSecond(second);
                }
            }
        }


        public void duration(String seconds) {
            JLog.d("duration -> " + seconds);
            if (youTubeListener != null) {
                youTubeListener.onDuration(Double.parseDouble(seconds));
            }
        }


        public void logs(String arg) {
            JLog.d("logs(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.logs(arg);
            }
        }

        private EventHandler handler = new EventHandler(EventRunner.getMainEventRunner()) {
            @Override
            protected void processEvent(InnerEvent event) {
                float second = (float) event.object;
                if (youTubeListener != null) {
                    youTubeListener.onCurrentSecond(second);
                }
            }
        };

        @Override
        public String onCallback(String s) {
            return null;
        }
    }


    /**
     * NonLeakingWebView
     */
    private static Field sConfigCallback;

    static {
        try {
            sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            sConfigCallback.setAccessible(true);
        } catch (Exception e) {
            // ignored
        }
    }

    public void onDestroy() {
        //super.onDetachedFromWindow();
        // View is now detached, and about to be destroyed

        //TODO : API available in SDK7
        //this.clearAllCache();
        //this.clearMemoryCache();
        //=============================

        try {
            if (sConfigCallback != null)
                sConfigCallback.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class MyWebViewClient extends WebAgent {
        protected WeakReference<Ability> activityRef;

        public MyWebViewClient(Ability activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public boolean isNeedLoadUrl(WebView webView, ResourceRequest request) {
            try {
                final Ability activity = activityRef.get();
                if (activity != null)
                    activity.startAbility(new Intent().setUri(request.getRequestUrl()));
            } catch (RuntimeException ignored) {
                // ignore any url parsing exceptions
            }
            return true;
        }

        @Override
        public void onPageLoaded(WebView webView, String url) {
            super.onPageLoaded(webView, url);
            JLog.d("onPageFinished()");
        }
    }

    public interface YouTubeListener {
        void onReady();

        void onStateChange(STATE state);

        void onPlaybackQualityChange(String arg);

        void onPlaybackRateChange(String arg);

        void onError(String arg);

        void onApiChange(String arg);

        void onCurrentSecond(double second);

        void onDuration(double duration);

        void logs(String log);
    }

    public enum STATE {
        UNSTARTED,
        ENDED,
        PLAYING,
        PAUSED,
        BUFFERING,
        CUED,
        NONE
    }

    private String getVideoHTML(String videoId) {
        InputStreamReader stream = null;
        try {
            ResourceManager resManager = context.getResourceManager();
            RawFileEntry rawFileEntry = resManager.getRawFileEntry("resources/rawfile/players.qualson");
            Resource in = null;
            try {
                in = rawFileEntry.openRawFile();
            } catch (IOException e) {
                JLog.i("Exception", e.getLocalizedMessage());
            }

            if (in != null) {
                stream = new InputStreamReader(in, "utf-8");
                try(BufferedReader buffer = new BufferedReader(stream)){
                    String read;
                    StringBuilder sb = new StringBuilder("");

                    while ((read = buffer.readLine()) != null) {
                        sb.append(read + "\n");
                    }

                    in.close();

                    String html = sb.toString().replace("[VIDEO_ID]", videoId).replace("[BG_COLOR]", backgroundColor);
                    PlaybackQuality playbackQuality = params.getPlaybackQuality();
                    html = html.replace("[AUTO_PLAY]", String.valueOf(params.getAutoplay()))
                            .replace("[AUTO_HIDE]", String.valueOf(params.getAutohide()))
                            .replace("[REL]", String.valueOf(params.getRel()))
                            .replace("[SHOW_INFO]", String.valueOf(params.getShowinfo()))
                            .replace("[ENABLE_JS_API]", String.valueOf(params.getEnablejsapi()))
                            .replace("[DISABLE_KB]", String.valueOf(params.getDisablekb()))
                            .replace("[CC_LANG_PREF]", String.valueOf(params.getCc_lang_pref()))
                            .replace("[CONTROLS]", String.valueOf(params.getControls()))
                            .replace("[AUDIO_VOLUME]", String.valueOf(params.getVolume()))
                            .replace("[PLAYBACK_QUALITY]", playbackQuality == null ? String.valueOf("default") : playbackQuality.name());
                    return html;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
