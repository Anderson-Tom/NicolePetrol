package com.example.chris.nicolepetrol.model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.chris.nicolepetrol.service.UploadFillUp;

/**
 * Created by chris on 25/11/2015.
 */
public class FillUp {
    Context context;
    private static final String TAG = "FillUp";
    private com.example.chris.myapplication.backend.fillUpApi.model.FillUp fillUpBean;

    /**
     * A fill-up represents the basic record of a fuel fillup at a petrol station
     * @param context the application's context
     * @param dateTime the time of the fill-up
     * @param odometer the odometer reading at the time of the fill-up
     * @param price the price per unit at the time of the fill-up
     * @param volume the volume of the fill-up
     * @param partial ture if the tank was not full
     */
    public FillUp(Context context, long dateTime, int odometer, int price, float volume, boolean partial) {
        this.context = context;
        if (odometer <= 0) {
            throw new IllegalArgumentException("Odometer must be positive:" + odometer);
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive:" + price);
        }
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume must be positive:" + volume);
        }
        fillUpBean = new com.example.chris.myapplication.backend.fillUpApi.model.FillUp();
        fillUpBean.setDateTime(dateTime);
        fillUpBean.setOdometer(odometer);
        fillUpBean.setPrice(price);
        fillUpBean.setVolume(volume);
        fillUpBean.setPartial(partial);
    }

    /**
     * send the fill-up to be uploaded
     * @return true if successful
     */
    public boolean upload() {
        Boolean status = true;
        //TODO
        //StorageService communicationService = new StorageService(this.context);
        //status = communicationService.upload(StorageService.communicationType.REPORT_FILLUP, this);
        Log.d(TAG, "status of upload of fill-up:" + status);
        new UploadFillUp().execute(new Pair<Context, com.example.chris.myapplication.backend.fillUpApi.model.FillUp>(this.context, this.fillUpBean));
        return status;
    }
}
