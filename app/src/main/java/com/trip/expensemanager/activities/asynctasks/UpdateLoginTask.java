package com.trip.expensemanager.activities.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.trip.expensemanager.CloudEndpointUtils;
import com.trip.expensemanager.database.LocalDB;
import com.trip.expensemanager.loginendpoint.Loginendpoint;
import com.trip.expensemanager.loginendpoint.model.LogIn;
import com.trip.utils.Constants;

import java.io.IOException;

public class UpdateLoginTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public UpdateLoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        LogIn retLogin = null;
        LocalDB localDb = new LocalDB(context);
        String[] strArrPurchases = localDb.getPurchaseId();
        try {
            if (strArrPurchases != null) {
                if (strArrPurchases[1] != null && strArrPurchases[0].equals(Constants.STR_NOT_SYNCHED)) {
                    Loginendpoint.Builder builder = new Loginendpoint.Builder(
                            AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
                    builder = CloudEndpointUtils.updateBuilder(builder);
                    Loginendpoint endpoint = builder.build();
                    retLogin = endpoint.getLogIn(localDb.retrieve()).execute();
                    if (retLogin.getPurchaseId() == null) {
                        retLogin.setPurchaseId(strArrPurchases[1]);
                        endpoint.updateLogIn(retLogin).execute();
                    }
                    localDb.updatePurchaseToSynced();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
