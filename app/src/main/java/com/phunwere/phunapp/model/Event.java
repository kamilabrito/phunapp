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

package com.phunwere.phunapp.model;

import java.io.Serializable;

/**
 * Event object implementation
 * <p>
 * Created by kamilabrito on 6/17/17.
 */

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private String title;
    private String timestamp;
    private String image;
    private String phone;
    private String date;
    private String locationline1;
    private String locationline2;

    public Event() {

    }

    /**
     * Returns event id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set event id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns event description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set event description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns event title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set event title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return event timestemp, publishing time
     *
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Set event timestemp, publishing time
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Return event image
     *
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Set event image
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Return event phone
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set event phone
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Return event date
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Set event date
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns first line of event location
     *
     * @return
     */
    public String getLocationline1() {
        return locationline1;
    }

    /**
     * Set first line of event location
     *
     * @param locationline1
     */
    public void setLocationline1(String locationline1) {
        this.locationline1 = locationline1;
    }

    /**
     * Returns second line of event location
     *
     * @return
     */
    public String getLocationline2() {
        return locationline2;
    }

    /**
     * Set second line of event location
     *
     * @param locationline2
     */
    public void setLocationline2(String locationline2) {
        this.locationline2 = locationline2;
    }
}
