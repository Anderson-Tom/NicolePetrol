package com.example.chris.nicolepetrol.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.chris.myapplication.backend.fillUpApi.FillUpApi;
import com.example.chris.myapplication.backend.fillUpApi.model.FillUp;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by chris on 25/11/2015.
 */
public class UploadFillUp extends AsyncTask<Pair<Context, FillUp>, Void, FillUp> {
    private static FillUpApi myApiService = null;
    private Context context;

    @Override
    protected FillUp doInBackground(Pair<Context, FillUp>... params) {
        if(myApiService == null) {  // Only do this once
            FillUpApi.Builder builder = new FillUpApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://nicolebackend.appspot.com//_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            //abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        this.context = params[0].first;
        FillUp fillUp = params[0].second;

        try {
            return myApiService.insert(fillUp).execute();
        } catch (IOException e) {
            //return e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(FillUp result) {
        Toast.makeText(this.context, result.getOdometer().toString() + result.getPrice().toString(), Toast.LENGTH_LONG).show();
    }
}