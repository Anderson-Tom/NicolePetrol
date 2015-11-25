package com.example.chris.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by chris on 25/11/2015.
 */
@Entity
public class FillUp {
    @Id
    private Long id;
    /** UTC of the fill up */
    private long dateTime;
    /** odometer reading at the time of the fill up */
    private int odometer;
    /** price per litre at the time of the fill up in local currency */
    private int price;
    /** amount of litres of the fill up */
    private float volume;
    /** indicates if the fill up is partial (i.e. not full) or not */
    private boolean partial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public int getOdometer() {
        return odometer;
    }

    public int getPrice() {
        return price;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
