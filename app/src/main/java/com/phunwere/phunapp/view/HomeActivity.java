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

package com.phunwere.phunapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.phunwere.phunapp.R;
import com.phunwere.phunapp.adapter.EventRecyclerViewAdapter;
import com.phunwere.phunapp.app.PhunAppApplication;
import com.phunwere.phunapp.components.RecyclerViewEmptySupport;
import com.phunwere.phunapp.components.RoundedImageView;
import com.phunwere.phunapp.interfaces.HomePresenter;
import com.phunwere.phunapp.interfaces.HomeView;
import com.phunwere.phunapp.interfaces.OnItemClickListener;
import com.phunwere.phunapp.interfaces.OnShareButtonClickListener;
import com.phunwere.phunapp.model.Event;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener, OnShareButtonClickListener, HomeView {

    @Inject
    HomePresenter mHomePresenter;
    @BindView(R.id.activity_home_rv_event)
    RecyclerViewEmptySupport mEventRecyclerView;
    @BindView(R.id.activity_home_rv_empty)
    TextView mListEmptyTextView;
    @BindView(R.id.activity_home_toolbar)
    Toolbar toolbar;

    private EventRecyclerViewAdapter mEventRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ((PhunAppApplication) getApplication()).getAppComponent().inject(this);
        mHomePresenter.setView(this);

        mEventRecyclerViewAdapter = new EventRecyclerViewAdapter(getApplicationContext());
        mEventRecyclerViewAdapter.setOnItemClickListener(this);
        mEventRecyclerViewAdapter.setOnShareButtonClickListener(this);

        requestEvents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            requestEvents();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Event item, RoundedImageView eventImage) {
        mHomePresenter.openDetailActivity(this, item, eventImage);
    }


    @Override
    public void onShareClickListener(Event item) {
        mHomePresenter.shareEvent(this, item);
    }


    public void requestEvents() {
        mHomePresenter.getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                //loadEvents(response);
                mHomePresenter.loadEvents(response, getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("onFailure", "Display error code " + t.toString());
                mListEmptyTextView.setText(getApplicationContext().getResources().getString(R.string.response_error));
                mEventRecyclerView.setEmptyView(mListEmptyTextView);
            }
        });
    }


    @Override
    public void setAdapterData(Response<List<Event>> response) {
        mEventRecyclerViewAdapter.setEventsList(mHomePresenter.prepareEventsList(response.body()));
        mEventRecyclerView.setLayoutManager(getRecyclerView());
        mEventRecyclerView.setAdapter(mEventRecyclerViewAdapter);
        mEventRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void setAdapterData(int text) {
        mEventRecyclerView.setLayoutManager(getRecyclerView());
        mListEmptyTextView.setText(getApplicationContext().getResources().getString(text));
        mEventRecyclerView.setEmptyView(mListEmptyTextView);
        mEventRecyclerView.setAdapter(mEventRecyclerViewAdapter);
        mEventRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * Returns the type of layout manager accordingly to the device type
     *
     * @return
     */
    public RecyclerView.LayoutManager getRecyclerView() {
        if (this.getResources().getString(R.string.device).equals(R.string.tablet)) {
            return new GridLayoutManager(this, 2);
        } else {
            return new LinearLayoutManager(this);
        }
    }

}
