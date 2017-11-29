package com.guilhermecardoso.githubtest.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class GitHubApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
