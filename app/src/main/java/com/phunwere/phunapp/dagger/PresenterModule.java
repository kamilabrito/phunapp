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

package com.phunwere.phunapp.dagger;

import android.content.Context;

import com.phunwere.phunapp.interfaces.DetailsPresenter;
import com.phunwere.phunapp.interfaces.HomePresenter;
import com.phunwere.phunapp.presenter.DetailsPresenterImpl;
import com.phunwere.phunapp.presenter.HomePresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module responsible to provide objects for the connection with presenters
 * <p>
 * Created by kamilabrito on 6/17/17.
 */

@Module
public class PresenterModule {

    @Provides
    @Singleton
    HomePresenter provideHomePresenter(Context context) {
        return new HomePresenterImpl(context);
    }

    @Provides
    @Singleton
    DetailsPresenter provideDetailsPresenter(Context context) {
        return new DetailsPresenterImpl(context);
    }

}
