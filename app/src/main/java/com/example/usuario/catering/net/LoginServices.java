package com.example.usuario.catering.net;

/**
 * Created by Usuario on 03/11/2014.
 */

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class LoginServices extends AsyncTask<Integer, Void, String> {
    public static final int WS_CALL_GET = 0;
    public static final int WS_CALL_POST = 1;
    private final String URL_WS_GET = "http://192.168.56.1:53837/api/login";
    private final String URL_WS_POST = "http://192.168.56.1:53837/api/login";
    //private final String APP_ID = "j2aLnaENSizTFXE725Lww3MTtqiMfEQhVO5HTmSD";
    //private final String REST_API_KEY = "jmKBd9wASHKDiBFLaQvExQY1xPh50OPONajo1VZN";
    private final int STATUS_OK = 200;
    private final int STATUS_CREATED = 201;
    private final int STATUS_ERROR = 400;
    private final int STATUS_NOT_FOUND = 404;
    private final int STATUS_UNKNOWN = 300;
    private final String ERROR = "error";
    private final List<NameValuePair> messageList;
    private OnBackgroundTaskCallback callbacks;
    private OnBackgroundTaskAnimation animation;
    private int status;

    public LoginServices(OnBackgroundTaskCallback callbacks, OnBackgroundTaskAnimation animation, List<NameValuePair> messageList) {
        this.callbacks = callbacks;
        this.animation = animation;
        this.messageList = messageList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (animation != null) {
            animation.startAnimation();
        }
    }

    protected String doInBackground(Integer... wsCall) {
        String response = null;
        try {
            switch (wsCall[0]) {
                case WS_CALL_GET:
                    response = connectionGET();
                    break;

                case WS_CALL_POST:
                    response = connectionPOST();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String connectionGET() {
        HttpGet httpGet = new HttpGet(URL_WS_GET);

        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");

        HttpParams httpParameters;
        httpParameters = new BasicHttpParams();
        int timeoutConnection = 100000; //Timeout until a connection is established.
        int timeoutSocket = 100000; //Timeout for waiting for data.

        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            status = response.getStatusLine().getStatusCode();

            switch (status) {
                case STATUS_OK:
                    HttpEntity e = response.getEntity();
                    return parseData(e.getContent());

                case STATUS_NOT_FOUND:
                case STATUS_UNKNOWN:
                default:
                    return ERROR;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ERROR;
    }

    private String connectionPOST() {
        HttpPost httpPost = new HttpPost(URL_WS_POST);

        //httpPost.setHeader("X-Parse-Application-Id", APP_ID);
        //httpPost.setHeader("X-Parse-REST-API-Key", REST_API_KEY);
        httpPost.setHeader("Accept", "application/json");
        //httpPost.setHeader("Content-type", "application/json");

        //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        //nameValuePairs.add(new BasicNameValuePair("deviceType", "android"));
        //nameValuePairs.add(new BasicNameValuePair("deviceToken", "anyDeviceToken"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(messageList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 100000; //Timeout until a connection is established.
        int timeoutSocket = 100000; //Timeout for waiting for data.

        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            status = response.getStatusLine().getStatusCode();

            switch (status) {
                case STATUS_OK:
                case STATUS_CREATED:
                case STATUS_ERROR:
                    HttpEntity e = response.getEntity();
                    return parseData(e.getContent());

                case STATUS_NOT_FOUND:
                case STATUS_UNKNOWN:
                default:
                    return ERROR;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ERROR;
    }

    private String parseData(InputStream content) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            content.close();
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ERROR;
    }

    protected void onPostExecute(String response) {
        switch (status) {
            case STATUS_OK:
            case STATUS_CREATED:
                callbacks.onTaskCompleted(response);
                animation.stopAnimation();
                break;

            case STATUS_NOT_FOUND:
            case STATUS_UNKNOWN:
            case STATUS_ERROR:
            default:
                callbacks.onTaskError(response);
                animation.stopAnimation();
                break;
        }
    }
}