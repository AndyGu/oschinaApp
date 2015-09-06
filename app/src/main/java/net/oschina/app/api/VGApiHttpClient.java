package net.oschina.app.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.oschina.app.AppContext;
import net.oschina.app.util.TLog;

import org.apache.http.client.params.ClientPNames;

import java.util.Locale;

public class VGApiHttpClient {

    public final static String VGTIME_HOST = "http://121.40.207.181/game_time/api/game/%s";

    private static String API_URL = "http://www.oschina.net/%s";
//     public final static String HOST = "192.168.1.11";
//     private static String API_URL = "http://192.168.1.11/%s";
    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static AsyncHttpClient client;

    public VGApiHttpClient() {}

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void clearUserCookies(Context context) {
        // (new HttpClientCookieStore(context)).a();
    }

    public static void deleteVG(String partUrl, AsyncHttpResponseHandler handler) {
        client.delete(getVGAbsoluteApiUrl(partUrl), handler);
        logVG(new StringBuilder("DELETE ").append(partUrl).toString());
    }

    public static void getVG(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getVGAbsoluteApiUrl(partUrl), handler);
        logVG(new StringBuilder("GET ").append(partUrl).toString());
    }

    public static void getVG(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.get(getVGAbsoluteApiUrl(partUrl), params, handler);
        logVG(new StringBuilder("GET ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static String getVGAbsoluteApiUrl(String partUrl) {
        String url = String.format(VGTIME_HOST, partUrl);
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }

    public static String getVGHost() {
        return VGTIME_HOST;
    }

    public static void getVGDirect(String url, AsyncHttpResponseHandler handler) {
        client.get(url, handler);
        logVG(new StringBuilder("GET ").append(url).toString());
    }

    public static void logVG(String log) {
        Log.d("BaseApi", log);
        TLog.log("Test", log);
    }

    public static void postVG(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getVGAbsoluteApiUrl(partUrl), handler);
        logVG(new StringBuilder("POST ").append(partUrl).toString());
    }

    public static void postVG(String partUrl, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.post(getVGAbsoluteApiUrl(partUrl), params, handler);
        logVG(new StringBuilder("POST ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void postVGDirect(String url, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
        logVG(new StringBuilder("POST ").append(url).append("&").append(params)
                .toString());
    }

    public static void putVG(String partUrl, AsyncHttpResponseHandler handler) {
        client.put(getVGAbsoluteApiUrl(partUrl), handler);
        logVG(new StringBuilder("PUT ").append(partUrl).toString());
    }

    public static void putVG(String partUrl, RequestParams params,
                             AsyncHttpResponseHandler handler) {
        client.put(getVGAbsoluteApiUrl(partUrl), params, handler);
        logVG(new StringBuilder("PUT ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void setVGApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public static void setVGHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
//        client.addHeader("Host", VGTIME_HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        setVGUserAgent(ApiClientHelper.getUserAgent(AppContext.getInstance()));
    }

    public static void setVGUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static void setVGCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    private static String appCookie;

    public static void cleanVGCookie() {
        appCookie = "";
    }

    public static String getVGCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }
}
