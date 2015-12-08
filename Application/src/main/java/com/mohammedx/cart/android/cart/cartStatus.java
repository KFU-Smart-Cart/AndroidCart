package com.mohammedx.cart.android.cart;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class cartStatus extends IntentService {

    private Bundle bundle;
    private boolean status;

    public cartStatus() {
        super("cartStatus");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        bundle = intent.getExtras();
        status = bundle.getBoolean("status");
        if (isNetworkAvailable()) {
            updateStatus(status);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void updateStatus(boolean status) {
        String s = status ? "Active" : "InActive";
        SharedPreferences idSharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);


        String id = idSharedPreferences.getString("id", null);
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+Constants.IP+"/SmartCartWeb/CartStatusUpdate.php?Status=" + s + "&ID=" + id + "";
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }
}
