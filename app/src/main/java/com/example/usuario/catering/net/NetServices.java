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
import org.apache.http.client.methods.HttpDelete;
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

public class NetServices extends AsyncTask<Integer, Void, String> {
    public static final int WS_CALL_GET = 0;
    public static final int WS_CALL_POST = 1;
    public static final int WS_CALL_DELETE = 2;
    //private final String APP_ID = "j2aLnaENSizTFXE725Lww3MTtqiMfEQhVO5HTmSD";
    //private final String REST_API_KEY = "jmKBd9wASHKDiBFLaQvExQ1xPh50OPONajo1VZN";
    private final int STATUS_OK = 200;
    private final int STATUS_CREATED = 201;
    private final int STATUS_ERROR = 400;
    private final int STATUS_NOT_FOUND = 404;
    private final int STATUS_UNKNOWN = 300;
    private final String ERROR = "error";
    public String ROOT_URL = "http://192.168.56.1:53837/api";
    private List<NameValuePair> messageList;
    private OnBackgroundTaskCallback callbacks;
    private OnBackgroundTaskAnimation animation;
    private int status;
    private int id;

    public NetServices(OnBackgroundTaskCallback callbacks,
                       OnBackgroundTaskAnimation animation,
                       List<NameValuePair> messageList, String apiResource) {
        this.callbacks = callbacks;
        this.animation = animation;
        this.messageList = messageList;
        this.ROOT_URL = this.ROOT_URL + apiResource;
    }
    public NetServices(OnBackgroundTaskCallback callbacks,
                       OnBackgroundTaskAnimation animation,
                       int id, String apiResource) {
        this.callbacks = callbacks;
        this.animation = animation;
        this.id = id;
        this.ROOT_URL = this.ROOT_URL + apiResource;
    }

    public NetServices(OnBackgroundTaskCallback callbacks,
                       OnBackgroundTaskAnimation animation, String apiResource) {
        this.callbacks = callbacks;
        this.animation = animation;
        this.ROOT_URL = this.ROOT_URL + apiResource;
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
                case WS_CALL_DELETE:
                    response = connectionDELETE();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String connectionDELETE() {
        HttpDelete httpDelete = new HttpDelete(ROOT_URL+"/"+this.id);

        httpDelete.setHeader("Accept", "application/json");
        httpDelete.setHeader("Content-type", "application/json");

        HttpParams httpParameters;
        httpParameters = new BasicHttpParams();
        //httpParameters.setIntParameter("id",this.id);
        int timeoutConnection = 100000; //Timeout until a connection is established.
        int timeoutSocket = 100000; //Timeout for waiting for data.

        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        try {
            HttpResponse response = httpClient.execute(httpDelete);
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

    private String connectionGET() {
        HttpGet httpGet = new HttpGet(ROOT_URL);

        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");

        HttpParams httpParameters;
        httpParameters = new BasicHttpParams();
        httpParameters.setIntParameter("Id",id);
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
        HttpPost httpPost = new HttpPost(ROOT_URL);

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