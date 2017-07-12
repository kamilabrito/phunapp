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

package com.phunwere.phunapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.phunwere.phunapp.app.PhunAppApplication;
import com.phunwere.phunapp.interfaces.DetailsPresenter;
import com.phunwere.phunapp.model.Event;

/**
 * DetailsPresenterImpl class is the implementation class
 * of DetailsPresenter
 * Created by kamilabrito on 6/17/17.
 */

public class DetailsPresenterImpl implements DetailsPresenter {

    public DetailsPresenterImpl(Context context) {
        ((PhunAppApplication) context).getAppComponent().inject(this);
    }

    @Override
    public void shareEvent(Context context, Event item) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/html");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Event");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getTitle());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getDescription());
        context.startActivity(Intent.createChooser(shareIntent, "Share Event"));
    }

    @Override
    public void callEvent(Context context, Event item) {
        String uri = "tel:" + item.getPhone().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }
}
