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

package com.phunwere.phunapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kamilabrito on 6/15/17.
 */

public final class Utils {

    /**
     * Verifies if the device is connected to the internet
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Formats date to its final looksß as shown on details screen
     *
     * @param eventDate
     * @return
     */
    public static String formatDate(String eventDate) throws ParseException {

        SimpleDateFormat rawDateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date rawFormatedDate = rawDateFormater.parse(eventDate);
            rawDateFormater.setTimeZone(TimeZone.getDefault());
            rawDateFormater = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mma");
            String formatedDate = rawDateFormater.format(rawFormatedDate);
            String finalDate = formatedDate.replace("AM", "am").replace("PM", "pm");
            return finalDate;
        } catch (ParseException e) {
            throw new ParseException("The current date format is invalid", e.getErrorOffset());
        }
    }
}
