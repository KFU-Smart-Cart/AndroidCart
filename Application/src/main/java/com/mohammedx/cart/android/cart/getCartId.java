package com.mohammedx.cart.android.cart;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class getCartId extends IntentService {

    private BluetoothAdapter mBluetoothAdapter = null;
    public getCartId() {
        super("getCartId");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (isNetworkAvailable()) {
            getId();
        }
    }

    private void getId() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.3/SmartCartWeb/cart.php?mac="+mBluetoothAdapter.getAddress().toUpperCase()+"";
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String id = response.body().string();
                writeId(id);
            }
        });
    }

    private void writeId(String id) {
        SharedPreferences idSharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor idEditor = idSharedPreferences.edit();

        idEditor.clear();
//        idEditor.putInt("id", Integer.parseInt(id));
        idEditor.putString("id",id);
        idEditor.apply();
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
}
