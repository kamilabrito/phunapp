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

import com.phunwere.phunapp.dagger.AppModule;
import com.phunwere.phunapp.dagger.NetworkModule;
import com.phunwere.phunapp.dagger.PresenterModule;
import com.phunwere.phunapp.presenter.DetailsPresenterImpl;
import com.phunwere.phunapp.presenter.HomePresenterImpl;
import com.phunwere.phunapp.view.DetailsActivity;
import com.phunwere.phunapp.view.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface class that loads all the necessary components and injection methods
 * Created by kamilabrito on 6/17/17.
 */

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(HomeActivity homeView);

    void inject(DetailsActivity detailsView);

    void inject(HomePresenterImpl homePresenter);

    void inject(DetailsPresenterImpl detailsPresenter);

}
