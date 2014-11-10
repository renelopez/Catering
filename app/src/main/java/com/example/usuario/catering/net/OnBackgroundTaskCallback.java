package com.example.usuario.catering.net;

/**
 * Created by Usuario on 04/11/2014.
 */
public interface OnBackgroundTaskCallback {
    public void onTaskCompleted(String response);

    public void onTaskError(String error);
}
