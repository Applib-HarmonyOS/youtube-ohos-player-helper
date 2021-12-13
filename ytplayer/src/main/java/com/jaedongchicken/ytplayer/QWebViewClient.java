package com.jaedongchicken.ytplayer;

import ohos.agp.components.webengine.ResourceRequest;
import ohos.agp.components.webengine.WebAgent;
import ohos.agp.components.webengine.WebView;
import ohos.media.image.PixelMap;

/**
 * Created by JD on 21/12/2017.
 */

public class QWebViewClient extends WebAgent {

    @Override
    public void onLoadingPage(WebView webView, String url, PixelMap icon) {

    }

    @Override
    public void onPageLoaded(WebView webView, String url) {

    }

    @Override
    public boolean isNeedLoadUrl(WebView webView, ResourceRequest request) {
        webView.load(request.getRequestUrl().toString());
        return true;
    }
}
