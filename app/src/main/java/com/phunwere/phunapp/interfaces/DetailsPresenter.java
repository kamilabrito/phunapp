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

import android.content.Context;

import com.phunwere.phunapp.model.Event;

/**
 * DetailsPresenter class is the interface
 * responsible for all the logical and data work for the
 * class DetailsActivity
 * Created by kamilabrito on 6/17/17.
 */

public interface DetailsPresenter {

    /**
     * Sends a intent to share the event in the details screen
     *
     * @param context
     * @param item
     */
    void shareEvent(Context context, Event item);

    /**
     * Sends a intent to open dialer with the phone number associated with the event
     *
     * @param context
     * @param item
     */
    void callEvent(Context context, Event item);
}
