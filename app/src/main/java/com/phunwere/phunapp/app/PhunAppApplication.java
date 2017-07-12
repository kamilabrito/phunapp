/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phunwere.phunapp.app;

import android.app.Application;
import android.content.res.Configuration;

import com.phunwere.phunapp.dagger.AppModule;
import com.phunwere.phunapp.interfaces.AppComponent;
import com.phunwere.phunapp.dagger.NetworkModule;
import com.phunwere.phunapp.interfaces.DaggerAppComponent;
import com.phunwere.phunapp.utils.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Custom base Application class with dagger
 * injection tor maintain the global state
 * of the application
 * <p>
 * Created by kamilabrito on 6/17/17.
 */

public class PhunAppApplication extends Application {

    private AppComponent appComponent;

    /**
     * Returns an instance of AppComponent
     *
     * @return AppComponent
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(getClient()))
                .build();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Interceptor to cache data and maintain it for a minute.
     * <p>
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */
    private class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }

    /**
     * Interceptor to cache data and maintain it for four weeks.
     * <p>
     * If the device is offline, stale (at most four weeks old)
     * response is fetched from the cache.
     */
    private class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utils.isNetworkAvailable(PhunAppApplication.this.getApplicationContext())) {
                request = request.newBuilder()
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale=" + 2419200)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    /**
     * Returns the OkHttpClient necessary to cache information downloaded from the server
     *
     * @return
     */

    public OkHttpClient getClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // Enable response caching
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .addInterceptor(new OfflineResponseCacheInterceptor())
                // Set the cache location and size (5 MB)
                .cache(new Cache(new File(getApplicationContext().getCacheDir(),
                        "apiResponses"), 5 * 1024 * 1024))
                .build();

        return okHttpClient;
    }
}
