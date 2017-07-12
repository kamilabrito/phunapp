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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;

import com.phunwere.phunapp.R;
import com.phunwere.phunapp.app.PhunAppApplication;
import com.phunwere.phunapp.components.RoundedImageView;
import com.phunwere.phunapp.interfaces.HomePresenter;
import com.phunwere.phunapp.interfaces.HomeView;
import com.phunwere.phunapp.interfaces.RetrofitService;
import com.phunwere.phunapp.model.Event;
import com.phunwere.phunapp.utils.Utils;
import com.phunwere.phunapp.view.DetailsActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * HomePresenterImpl class is the implementation class
 * of HomePresenter
 * Created by kamilabrito on 6/17/17.
 */

public class HomePresenterImpl implements HomePresenter {

    @Inject
    RetrofitService service;
    HomeView view;

    private static final String EVENT_DETAIL_ACTIVITY = "GOAL_DETAILS";

    public HomePresenterImpl(Context context) {
        ((PhunAppApplication) context).getAppComponent().inject(this);
    }

    @Override
    public void setView(HomeView view) {
        this.view = view;
    }

    @Override
    public Call<List<Event>> getEvents() {
        return service.allEvents();
    }

    @Override
    public List<Event> prepareEventsList(List<Event> responseBody) {

        Collections.sort(responseBody, new Comparator<Event>() {
            public int compare(Event m1, Event m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
        return responseBody;
    }

    @Override
    public void shareEvent(Context context, Event item) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/html");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getTitle());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getDescription());
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, "Share Event"));
    }

    @Override
    public void openDetailActivity(Activity homeActivity, Event item, RoundedImageView eventImage) {

        Intent intent = new Intent(homeActivity, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(EVENT_DETAIL_ACTIVITY, item);
        intent.putExtras(b);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(homeActivity,
                        eventImage,
                        ViewCompat.getTransitionName(eventImage));
        homeActivity.startActivity(intent, options.toBundle());
    }

    @Override
    public void loadEvents(Response<List<Event>> response, Context context) {
        if (response.body() == null) {
            if (!Utils.isNetworkAvailable(context)) {
                view.setAdapterData(R.string.no_connection);
            } else {
                view.setAdapterData(R.string.no_events);
            }
        } else {
            if (!response.body().isEmpty()) {
                view.setAdapterData(response);
            }
            if (!Utils.isNetworkAvailable(context) && response.body().isEmpty()) {
                view.setAdapterData(R.string.no_connection);
            }
        }
    }

}
