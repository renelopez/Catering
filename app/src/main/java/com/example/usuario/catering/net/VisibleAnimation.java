package com.example.usuario.catering.net;

import android.view.View;

/**
 * Created by Usuario on 05/11/2014.
 */
public class VisibleAnimation implements OnBackgroundTaskAnimation {
    private final View view;

    public VisibleAnimation(View viewById) {
        this.view = viewById;
    }

    @Override
    public void startAnimation() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopAnimation() {
        view.setVisibility(View.INVISIBLE);
    }
}
