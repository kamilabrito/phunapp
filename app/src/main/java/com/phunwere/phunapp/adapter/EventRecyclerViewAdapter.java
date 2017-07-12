/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phunwere.phunapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.phunwere.phunapp.R;
import com.phunwere.phunapp.components.RoundedImageView;
import com.phunwere.phunapp.interfaces.OnItemClickListener;
import com.phunwere.phunapp.interfaces.OnShareButtonClickListener;
import com.phunwere.phunapp.model.Event;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Implements adapter for the homescreen recyclerview
 * <p>
 * Created by kamilabrito on 6/17/17.
 */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private List<Event> mEvents;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnShareButtonClickListener onShareClickListener;


    public EventRecyclerViewAdapter(@NonNull Context context) {
        this.context = context;
    }

    public void setEventsList(List<Event> eventsList) {
        mEvents = eventsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_row_home, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Event currentEvent = mEvents.get(position);

        if (currentEvent != null) {
            holder.eventTitle.setText(currentEvent.getTitle());
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(currentEvent, holder.eventImage);
                }
            };
            holder.eventCard.setOnClickListener(listener);
            holder.eventDescription.setText(currentEvent.getDescription());
            holder.eventLocation.setText(currentEvent.getLocationline1() + ", " + currentEvent.getLocationline2());
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShareClickListener.onShareClickListener(currentEvent);
                }
            });
            Picasso.with(context)
                    .load(currentEvent.getImage())
                    .placeholder(R.drawable.placeholder_nomoon)
                    .resize(60, 60)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.eventImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(currentEvent.getImage())
                                    .error(R.drawable.placeholder_nomoon)
                                    .into(holder.eventImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });

        }


    }

    @Override
    public int getItemCount() {
        return (null != mEvents ? mEvents.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_tv_title)
        TextView eventTitle;
        @BindView(R.id.row_tv_location)
        TextView eventLocation;
        @BindView(R.id.row_tv_description)
        TextView eventDescription;
        @BindView(R.id.row_card_event)
        CardView eventCard;
        @BindView(R.id.row_btn_share)
        Button shareButton;
        @BindView(R.id.row_iv_image)
        RoundedImageView eventImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public OnShareButtonClickListener getOnShareButtonClickListener() {
        return onShareClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnShareButtonClickListener(OnShareButtonClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
