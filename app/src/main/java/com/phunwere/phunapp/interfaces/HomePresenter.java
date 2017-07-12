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

package com.phunwere.phunapp.interfaces;

import android.app.Activity;
import android.content.Context;

import com.phunwere.phunapp.components.RoundedImageView;
import com.phunwere.phunapp.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * HomePresenter class is the interface
 * responsible for all the logical and data work for the
 * class HomeActivity
 * Created by kamilabrito on 6/17/17.
 */

public interface HomePresenter {

    /**
     * Makes the call to the server and retrieves events
     *
     * @return
     */
    Call<List<Event>> getEvents();

    /**
     * Sorts events in crescent order of date
     *
     * @param body
     * @return
     */
    List<Event> prepareEventsList(List<Event> body);

    /**
     * Sends a intent to share the event in the main timeline
     *
     * @param context
     * @param item
     */
    void shareEvent(Context context, Event item);

    /**
     * Opens details activity of the event clicked in the timeline
     *
     * @param homeActivity
     * @param item
     * @param eventImage
     */
    void openDetailActivity(Activity homeActivity, Event item, RoundedImageView eventImage);

    /**
     * Verifies the events returned on the server call and makes the call to load
     * them on the recyclerview
     *
     * @param response
     */
    void loadEvents(Response<List<Event>> response, Context context);

    /**
     * Loads an instace of the view inside the presenter
     *
     * @param view
     */
    void setView(HomeView view);
}
