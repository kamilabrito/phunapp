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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.phunwere.phunapp.R;
import com.phunwere.phunapp.app.PhunAppApplication;
import com.phunwere.phunapp.interfaces.DetailsPresenter;
import com.phunwere.phunapp.model.Event;
import com.phunwere.phunapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kamilabrito on 6/17/17.
 */

public class DetailsActivity extends AppCompatActivity {

    @Inject
    DetailsPresenter mPresenterDetails;
    @BindView(R.id.activity_details_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_details_tv_timestamp)
    TextView mTimestampTv;
    @BindView(R.id.activity_details_tv_title)
    TextView mTitleTv;
    @BindView(R.id.activity_details_tv_description)
    TextView mDescriptionTv;
    @BindView(R.id.activity_details_iv_header)
    ImageView mHeaderIv;

    private Event mEvent;
    private static final String EVENT_DETAIL_ACTIVITY = "GOAL_DETAILS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ((PhunAppApplication) getApplication()).getAppComponent().inject(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setStatusBarTranslucent(true);
        invalidateOptionsMenu();

        mEvent = new Event();

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            mEvent = (Event) b.getSerializable(EVENT_DETAIL_ACTIVITY);
            mTitleTv.setText(mEvent.getTitle());
            mDescriptionTv.setText(mEvent.getDescription());
            Picasso.with(this)
                    .load(mEvent.getImage())
                    .placeholder(R.drawable.placeholder_nomoon)
                    .into(mHeaderIv);
            try {
                mTimestampTv.setText(Utils.formatDate(mEvent.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);

        MenuItem item = menu.findItem(R.id.action_call);
        if (mEvent.getPhone() == null) {
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            mPresenterDetails.shareEvent(this, mEvent);
            return true;
        }

        if (id == R.id.action_call) {
            mPresenterDetails.callEvent(this, mEvent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Responsible to change statusbar color to transparent
     *
     * @param makeTranslucent
     */
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
