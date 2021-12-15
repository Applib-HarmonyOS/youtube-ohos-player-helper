[![Build](https://github.com/applibgroup/youtube-ohos-player-helper/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/youtube-ohos-player-helper/actions/workflows/main.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=applibgroup_youtube-ohos-player-helper&metric=alert_status)](https://sonarcloud.io/dashboard?id=applibgroup_youtube-ohos-player-helper)

Youtube-OHOS-Player-Helper(YTPlayer)
=====
Helper library for harmony developers looking to add YouTube video playback in their applications via the iframe player in WebView



# Source
This library has been inspired by [ JackDinealKIM /
youtube-android-player-helper](https://github.com/JackDinealKIM/youtube-android-player-helper).

## Integration

1. For using youtube-android-player-helper module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```
 implementation project(path: ':ytplayer')
```
2. For using youtube-android-player-helper module in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```
 implementation fileTree(dir: 'libs', include: ['*.har'])
```
3. For using youtube-android-player-helper module from a remote repository in separate application, add the below dependencies in entry/build.gradle file.
```
implementation 'dev.applibgroup:youtube-android-player-helper:1.0.0'
```


How do I use YTPlayer?
-------------------

Simple use cases will look something like this:
* XML

```xml
    <com.jaedongchicken.ytplayer.YoutubePlayerView
        ohos:id="$+id:youtubePlayerView"
        ohos:width="match_parent"
        ohos:height="match_content" />

```

* JAVA

```java
        // get id from XML
        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) findComponentById(ResourceTable.Id_youtubePlayerView);
       
         // Control values
         // see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0); // hide control
        // params.setVolume(100); // volume control
        // params.setPlaybackQuality(PlaybackQuality.small); // video quality control

        
         // initialize YoutubePlayerCallBackListener with Params and VideoID
        // youtubePlayerView.initialize("B2zhLYz4pYo", params, new YoutubePlayerView.YouTubeListener())

		// initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // To Use - avoid UMG block!!!! but you'd better make own your server for your real service.
        // youtubePlayerView.initializeWithCustomURL("p1Zt47V3pPw" or "http://jaedong.net/youtube/p1Zt47V3pPw", params, new YoutubePlayerView.YouTubeListener())
        

       // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize("YOUTUBE_ID", new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {
            }

            @Override
            public void onError(String error) {
            }

            @Override
            public void onApiChange(String arg) {
            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
            }

            @Override
            public void onDuration(double duration) {
                // total duration
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });


        // psuse video
        youtubePlayerView.pause();
        // play video when it's ready
        youtubePlayerView.play();
        
    
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
    
```


License
-------
```code
Copyright 2016 JD Kim

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



