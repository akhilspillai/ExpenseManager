package com.trip.expensemanager.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.trip.expensemanager.CloudEndpointUtils;
import com.trip.expensemanager.R;
import com.trip.expensemanager.activities.ExpenseActivity;
import com.trip.expensemanager.activities.TripsActivity;
import com.trip.expensemanager.database.LocalDB;
import com.trip.expensemanager.deviceinfoendpoint.Deviceinfoendpoint;
import com.trip.expensemanager.deviceinfoendpoint.model.CollectionResponseDeviceInfo;
import com.trip.expensemanager.deviceinfoendpoint.model.DeviceInfo;
import com.trip.expensemanager.fragments.dialogs.ConfirmDialogListener;
import com.trip.expensemanager.fragments.dialogs.ConfirmationFragment;
import com.trip.expensemanager.fragments.dialogs.InfoDialogListener;
import com.trip.expensemanager.fragments.dialogs.InformationFragment;
import com.trip.expensemanager.fragments.dialogs.ThreeButtonDialogListener;
import com.trip.expensemanager.fragments.dialogs.ThreeButtonFragment;
import com.trip.expensemanager.loginendpoint.Loginendpoint;
import com.trip.expensemanager.loginendpoint.model.LogIn;
import com.trip.expensemanager.messageDataApi.MessageDataApi;
import com.trip.expensemanager.messageDataApi.model.MessageData;
import com.trip.utils.Constants;
import com.trip.utils.Global;
import com.trip.utils.billing.IabHelper;
import com.trip.utils.billing.IabResult;
import com.trip.utils.billing.Purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NewUserFragment extends CustomFragment implements OnClickListener {

    public String strRegId;
    public String strDeviceName;
    IntentFilter filter;
    private String strMobNo, strCountryCode, strToken, strScreenName;
    private EditText etxtMobNo, etxtToken;
    private AutoCompleteTextView etxtCountryCode;
    private ScrollView sc, scName;
    private RelativeLayout rl;
    private LinearLayout llExit;
    private LinearLayout llNext;
    private View anim;
    private IabHelper mHelper;
    private boolean isPurchaseReady = false;
    private String strUniqueString;
    private Button btnVerify;
    private BroadcastReceiver smsReceiver;
    private RegisterTask registerTask;
    private ValidateTokenTask tokenTask;
    private SendSMSTask sendSMSTask;
    private List<String> lstCountryCodes;
    private HashMap<String, String> hmCountries = new HashMap<>();
    private EditText etxtName;
    private boolean blIsNameEntry;

    public static NewUserFragment newInstance() {
        NewUserFragment fragment = null;
        try {
            fragment = new NewUserFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_new_user, container, false);

        anim = rootView.findViewById(R.id.anim_line);
        sc = (ScrollView) rootView.findViewById(R.id.sc);
        scName = (ScrollView) rootView.findViewById(R.id.sc_name);
        rl = (RelativeLayout) rootView.findViewById(R.id.footer);
        llExit = (LinearLayout) rootView.findViewById(R.id.ll_exit);
        llNext = (LinearLayout) rootView.findViewById(R.id.ll_next);
        etxtMobNo = (EditText) rootView.findViewById(R.id.etxt_mob_no);
        etxtCountryCode = (AutoCompleteTextView) rootView.findViewById(R.id.etxt_country);
        btnVerify = (Button) rootView.findViewById(R.id.btn_verify);
        etxtToken = (EditText) rootView.findViewById(R.id.etxt_token);
        etxtName = (EditText) rootView.findViewById(R.id.etxt_name);

        lstCountryCodes = Arrays.asList(getResources().getStringArray(R.array.country_codes));

        ArrayAdapter<String> autocompletetextAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lstCountryCodes);

        etxtCountryCode.setAdapter(autocompletetextAdapter);
        etxtCountryCode.setText("+");

        etxtCountryCode.setSelection(1);

        etxtCountryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etxtCountryCode.getText().equals("")) {
                        etxtCountryCode.setText("+");
                    }
                    etxtCountryCode.showDropDown();
                }
            }
        });

        etxtCountryCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etxtMobNo.requestFocus();
            }
        });

        btnVerify.setOnClickListener(this);
        llExit.setOnClickListener(this);
        llNext.setOnClickListener(this);

        new GetCountriesTask().execute();

        return rootView;
    }

    private void showErrorDialog(String strMessage) {
        InfoDialogListener listener = new InfoDialogListener() {

            @Override
            public void onDialogButtonClick(DialogFragment dialog) {
                anim.setVisibility(View.INVISIBLE);
                rl.setVisibility(View.VISIBLE);
                sc.setVisibility(View.VISIBLE);
                enableVerification();
                dialog.dismiss();
            }
        };
        InformationFragment.newInstance("Error", strMessage, "OK", R.layout.fragment_dialog_info,
                listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }


    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.equals(llExit)) {
            getActivity().finish();
        } else if (v.equals(llNext)) {
            if(blIsNameEntry) {
                if (Global.validate(etxtName)) {

                    strScreenName = etxtName.getText().toString();

                    startRegistration();
                }
            } else {
                if (Global.validate(etxtCountryCode, etxtMobNo, etxtToken)) {
                    strCountryCode = etxtCountryCode.getText().toString();
                    strMobNo = etxtMobNo.getText().toString();
                    strToken = etxtToken.getText().toString();

                    strMobNo = strCountryCode + strMobNo;

                    startTokenCheck();
                }
            }
        } else if (v.equals(btnVerify)) {
            if (Global.validate(etxtCountryCode, etxtMobNo)) {

                strCountryCode = etxtCountryCode.getText().toString();
                strMobNo = etxtMobNo.getText().toString();
                strScreenName = etxtName.getText().toString();

                if (lstCountryCodes.contains(strCountryCode)) {
                    smsReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Bundle extras = intent.getExtras();

                            if (extras != null) {
                                Object[] smsextras = (Object[]) extras.get("pdus");

                                for (Object smsExtra:smsextras) {
                                    SmsMessage smsmsg = SmsMessage.createFromPdu((byte[]) smsExtra);

                                    String strMsgBody = smsmsg.getMessageBody();

                                    int index = strMsgBody.indexOf("Your verification code is ");
                                    if (index != -1) {
                                        index = index + 26;
                                        etxtToken.setText(strMsgBody.substring(index));
                                        getActivity().unregisterReceiver(smsReceiver);
                                        smsReceiver = null;
                                        llNext.performClick();
                                    }
                                }

                            }
                        }
                    };
                    filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                    getActivity().registerReceiver(smsReceiver, filter);
                    verifyMobNo();
                } else {
                    Toast.makeText(getActivity(), "Please enter a valid country code", Toast.LENGTH_LONG).show();
                    etxtCountryCode.setText("");
                }
            }
        }
    }

    private void verifyMobNo() {
        btnVerify.setEnabled(false);
        etxtCountryCode.setEnabled(false);
        etxtMobNo.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableVerification();
            }
        }, 180 * 1000);


        if (Global.isConnected(getActivity())) {

            if (sendSMSTask != null && !sendSMSTask.isCancelled()) {
                sendSMSTask.cancel(true);
                sendSMSTask = null;
            }

            sendSMSTask = new SendSMSTask(strCountryCode, strMobNo);
            sendSMSTask.execute();

            showInfoMessage(Constants.SMS_DELIVERY_PROBLEM);
        } else {
            showErrorDialog("Please connect to internet to continue.");
        }
    }

    private void enableVerification() {
        btnVerify.setEnabled(true);
        etxtMobNo.setEnabled(true);
        etxtCountryCode.setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();


        if (smsReceiver != null && filter != null) {
            getActivity().registerReceiver(smsReceiver, filter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (smsReceiver != null) {
            getActivity().unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    private void startRegistration() {
        if (Global.isConnected(getActivity())) {
            if (registerTask != null && !registerTask.isCancelled()) {
                registerTask.cancel(true);
                registerTask = null;
            }

            registerTask = new RegisterTask(strMobNo, strScreenName);
            registerTask.execute();
        } else {
            showErrorDialog("Please connect to internet to continue.");
        }
    }

    private void startTokenCheck() {
        if (Global.isConnected(getActivity())) {
            if (tokenTask != null && !tokenTask.isCancelled()) {
                tokenTask.cancel(true);
                tokenTask = null;
            }

            tokenTask = new ValidateTokenTask(strMobNo, strToken);
            tokenTask.execute();
        } else {
            showErrorDialog("Please connect to internet to continue.");
        }
    }

    private void showPurchaseOrNotDialog(String strMessage) {
        ThreeButtonDialogListener listener = new ThreeButtonDialogListener() {

            @Override
            public void onDialogFirstButtonClick(DialogFragment dialog) {
                purchaseItem();
                dialog.dismiss();
            }

            @Override
            public void onDialogSecondButtonClick(DialogFragment dialog) {
                new PurchaseLoginTask(strMobNo).execute(false);
                dialog.dismiss();
            }

            @Override
            public void onDialogThirdButtonClick(DialogFragment dialog) {
                getActivity().finish();
                dialog.dismiss();
            }

        };
        ThreeButtonFragment.newInstance(strMessage, listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    protected void purchaseItem() {
        if (!isPurchaseReady) {
            String base64EncodedPublicKey = Constants.STR_LICENSE_1 +
                    Constants.STR_LICENSE_2 + Constants.STR_LICENSE_3 +
                    Constants.STR_LICENSE_4 + Constants.STR_LICENSE_5 +
                    Constants.STR_LICENSE_6 + Constants.STR_LICENSE_7;

            mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        Log.d("Akhil", "Problem setting up In-app Billing: " + result);
                        return;
                    }
                    isPurchaseReady = true;
                    continuePurchase();
                }
            });
        } else {
            continuePurchase();
        }
    }

    private void continuePurchase() {
        IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d("Akhil", "Error purchasing: " + result);
                    showMessage("There was an error in purchasing! Please try again.");
                } else if (purchase.getSku().equals(Constants.STR_SKU_PREMIUM)) {
                    String strUniqueReturn = purchase.getDeveloperPayload();
                    if (strUniqueReturn != null && strUniqueReturn.equals(strUniqueString)) {
                        SharedPreferences prefs = getActivity().getSharedPreferences(
                                Constants.STR_PREFERENCE, Activity.MODE_PRIVATE);
                        prefs.edit().putBoolean(Constants.STR_PURCHASED, true).apply();
                        if (Global.isConnected(getActivity())) {
                            new PurchaseLoginTask(strMobNo).execute(true);
                        }
                    } else {
                        showMessage("There was an error in purchasing! Please try again.");
                    }
                }
            }
        };
        strUniqueString = Global.randomString(25);
        if (mHelper != null) {
            mHelper.flagEndAsync();
            mHelper.launchPurchaseFlow(getActivity(), Constants.STR_SKU_PREMIUM, Constants.ORDER_ID,
                    mPurchaseFinishedListener, strUniqueString);
        }
    }

    private void showRegistrationError(String strMessage) {
        ConfirmDialogListener listener = new ConfirmDialogListener() {

            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                startRegistration();
                dialog.dismiss();
            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                anim.setVisibility(View.VISIBLE);
                rl.setVisibility(View.INVISIBLE);
                sc.setVisibility(View.INVISIBLE);
                etxtCountryCode.setEnabled(true);
                etxtMobNo.setEnabled(true);
                dialog.dismiss();
            }
        };
        ConfirmationFragment.newInstance("Error", strMessage, "Try Again", R.layout.fragment_dialog_confirm,
                listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    private class GetCountriesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getCountryData();
            return null;
        }

        private void getCountryData() {
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale locale : locales) {
                String countryCode = locale.getCountry();
                String country = locale.getDisplayCountry();
                if (country.trim().length() > 0 && !hmCountries.containsKey(countryCode)) {
                    hmCountries.put(countryCode, country);
                }
            }
        }

    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private class SendSMSTask extends AsyncTask<Void, Void, MessageData> {

        String strMobNo;
        String strCountryCode;

        SendSMSTask(String strCountryCode, String strMobNo) {
            this.strMobNo = strMobNo;
            this.strCountryCode = strCountryCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MessageData doInBackground(Void... params) {

            strMobNo = strCountryCode + strMobNo;

            return createToken();
        }

        @Override
        protected void onPostExecute(MessageData messageData) {
            super.onPostExecute(messageData);
            if (messageData == null) {
                showErrorDialog("Unable to contact the server. Please try again.");
                enableVerification();
            }
        }

        private MessageData createToken() {
            MessageData retMessageData = new MessageData();
            MessageDataApi.Builder messageBuilder = new MessageDataApi.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            messageBuilder = CloudEndpointUtils.updateBuilder(messageBuilder);
            MessageDataApi messageEndpoint = messageBuilder.build();
            MessageData messageData = new MessageData();
            messageData.setId(Long.parseLong(strMobNo.substring(1)));
            messageData.setMobNo(strMobNo);
            messageData.setCreationDate(new DateTime(new Date()));
            try {
                retMessageData = messageEndpoint.insertMessageData(messageData).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retMessageData;
        }
    }

    private class ValidateTokenTask extends AsyncTask<Void, Void, String> {

        String strMobNo;
        String strToken;

        ValidateTokenTask(String strMobNo, String strToken) {
            this.strMobNo = strMobNo;
            this.strToken = strToken;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((ExpenseActivity) getActivity()).setCustomTitle(R.string.done);
            anim.setVisibility(View.VISIBLE);
            rl.setVisibility(View.INVISIBLE);
            sc.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = Constants.STR_FAILURE;
            try {
                result = isTokenValid(strToken);
            } catch (IOException e) {

            }
            return result;

        }

        private String isTokenValid(String strToken) throws IOException{
            MessageDataApi.Builder messageBuilder = new MessageDataApi.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            messageBuilder = CloudEndpointUtils.updateBuilder(messageBuilder);
            MessageDataApi messageEndpoint = messageBuilder.build();
            MessageData messageData;
            messageData = messageEndpoint.getMessageData(Long.parseLong(strMobNo.substring(1)))
                        .execute();
            if(messageData != null && strToken.equals(messageData.getToken())) {
                return Constants.STR_SUCCESS;
            }
            return Constants.STR_WRONG_TOKEN;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null || result.equals(Constants.STR_FAILURE)) {
                showRegistrationError("Oops!! Something went wrong. Please try again.");
                anim.setVisibility(View.INVISIBLE);
                rl.setVisibility(View.VISIBLE);
                sc.setVisibility(View.VISIBLE);
            } else if (result.equals(Constants.STR_WRONG_TOKEN)) {
                showRegistrationError("The token you entered is wrong Please try again.");
                anim.setVisibility(View.INVISIBLE);
                rl.setVisibility(View.VISIBLE);
                sc.setVisibility(View.VISIBLE);
            } else if (result.equals(Constants.STR_SUCCESS)) {
                scName.setVisibility(View.VISIBLE);
                anim.setVisibility(View.INVISIBLE);
                rl.setVisibility(View.VISIBLE);
                blIsNameEntry = true;

            }
        }
    }

    private class RegisterTask extends AsyncTask<Void, Void, String> {

        public static final String PROJECT_NUMBER = "28707109757";
        private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        String strMobNo;
        String strScreenName;
        private GoogleCloudMessaging gcm;

        RegisterTask(String strMobNo, String strScreenName) {
            this.strMobNo = strMobNo;
            this.strScreenName = strScreenName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((ExpenseActivity) getActivity()).setCustomTitle(R.string.done);
            anim.setVisibility(View.VISIBLE);
            rl.setVisibility(View.INVISIBLE);
            sc.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String strResult = null;
            try {
                strResult = getUserId();
            } catch (IOException e) {

            }
            return strResult;
        }


        private String getUserId() throws IOException {
            String strResult = Constants.STR_FAILURE;
            LogIn login = null;
            DeviceInfo device;
            LocalDB localDb = new LocalDB(getActivity());
            Loginendpoint.Builder loginBuilder = new Loginendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            loginBuilder = CloudEndpointUtils.updateBuilder(loginBuilder);
            Loginendpoint loginEndpoint = loginBuilder.build();
            Deviceinfoendpoint.Builder devInfoBuilder = new Deviceinfoendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            devInfoBuilder = CloudEndpointUtils.updateBuilder(devInfoBuilder);
            Deviceinfoendpoint devInfoEndpoint = devInfoBuilder.build();
            strDeviceName = getDeviceName();
            strRegId = registerForGCM();
            if (strRegId == null) {
                return strResult;
            }
            long id = Long.parseLong(strMobNo.substring(1));
            try {

                login = loginEndpoint.getLogIn(id).execute();

            } catch (IOException e) {

            }
            CollectionResponseDeviceInfo devInfoEntities = devInfoEndpoint.listDeviceInfo()
                    .setGcmRegId(strRegId).execute();

            if (login == null || login.getDeviceIDs() == null) {
                boolean isUserPresent = true;
                if (devInfoEntities == null || devInfoEntities.getItems() == null || devInfoEntities
                        .getItems().size() < 1) {
                    device = new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device = devInfoEndpoint.insertDeviceInfo(device).execute();
                } else {
                    device = devInfoEntities.getItems().get(0);
                }

                List<Long> devList = new ArrayList<>(1);
                devList.add(device.getId());

                if(login == null) {
                    login = new LogIn();
                    login.setId(id);
                    login.setCountryCode(strCountryCode);
                    login.setUsername(strMobNo);
                    isUserPresent = false;
                }

                login.setPrefferedName(strScreenName);
                login.setDeviceIDs(devList);

                login = loginEndpoint.insertLogIn(login).execute();

                localDb.insert(login.getId(), login.getUsername(), login.getCountryCode(), Constants.STR_YOU,
                        device.getId());
                localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU, Constants.STR_SYNCHED);
                if(!isUserPresent) {
                    strResult = Constants.STR_SUCCESS;
                } else {
                    strResult = Constants.STR_SYNC_NEEDED;
                }

            } else {
                if (devInfoEntities == null || devInfoEntities.getItems() == null || devInfoEntities
                        .getItems().size() < 1) {
                    device = new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device = devInfoEndpoint.insertDeviceInfo(device).execute();
                } else {
                    device = devInfoEntities.getItems().get(0);
                }
                List<Long> lstDevIds = login.getDeviceIDs();


                if (lstDevIds == null) {
                    lstDevIds = new ArrayList<>();
                }

                if (isPurchased() || lstDevIds.contains(device.getId())) {
                    if (!lstDevIds.contains(device.getId())) {
                        lstDevIds.add(device.getId());
                    }

                    login.setDeviceIDs(lstDevIds);
                    login = loginEndpoint.updateLogIn(login).execute();
                    localDb.insert(login.getId(), login.getUsername(), login.getCountryCode(),
                            Constants.STR_YOU, device.getId());
                    localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU,
                            Constants.STR_SYNCHED);
                    strResult = Constants.STR_SYNC_NEEDED;

                } else {

                    strResult = Constants.STR_NOT_PURCHASED;

                }

            }
            return strResult;
        }

        private String getDeviceName() {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return model;
            } else {
                return manufacturer + " " + model;
            }
        }

        private String registerForGCM() {
            String strRegId = null;
            int count = 5;
            long sleepTime = 100;
            if (checkPlayServices()) {
                while (!isCancelled()) {
                    if (!Global.isConnected(getActivity())) {
                        break;
                    }
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(getActivity());
                        }
                        strRegId = gcm.register(PROJECT_NUMBER);
                        break;
                    } catch (IOException e) {
                        if (--count == 0) {
                            break;
                        } else {
                            try {
                                Thread.sleep(sleepTime * 2);
                            } catch (InterruptedException e1) {
                                break;
                            }
                        }
                    }
                }
            }
            return strRegId;
        }

        private boolean checkPlayServices() {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
            if (resultCode != ConnectionResult.SUCCESS) {
                try {
                    if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                        GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                                PLAY_SERVICES_RESOLUTION_REQUEST).show();
                    } else {
                        Log.i("Expense", "This device is not supported.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ActionBarActivity context = (ActionBarActivity) getActivity();

            if (result == null || result.equals(Constants.STR_FAILURE)) {
                showRegistrationError("Oops!! Something went wrong. Please try again.");
            } else if (result.equals(Constants.STR_NOT_PURCHASED)) {
                String strMessage = getActivity().getResources().getString(R.string.upgrade_features);
                strMessage = Constants.STR_LOGIN_DIFF_DEV + strMessage;
                showPurchaseOrNotDialog(strMessage);
            } else if (result.equals(Constants.STR_SYNC_NEEDED)) {
                Intent activityIntent = new Intent(context, TripsActivity.class);
                activityIntent.putExtra(Constants.STR_SYNC_NEEDED, true);
                startActivity(activityIntent);
                context.finish();
            } else if (result.equals(Constants.STR_SUCCESS)) {
                Intent activityIntent = new Intent(context, TripsActivity.class);
                activityIntent.putExtra(Constants.STR_SYNC_NEEDED, false);
                startActivity(activityIntent);
                context.finish();
            } else if (result.equals(Constants.STR_TOKEN_INVALID)) {
                showErrorDialog("Invalid verification token. Please try again!!");
            }
        }

    }

    private class PurchaseLoginTask extends AsyncTask<Boolean, Void, String> {

        String strMobNo;

        PurchaseLoginTask(String strMobNo) {
            this.strMobNo = strMobNo;
        }

        @Override
        protected String doInBackground(Boolean... params) {
            int count = 5;
            long sleepTime = 100L;
            String result = null;
            while (!isCancelled()) {
                if (!Global.isConnected(getActivity())) {
                    break;
                }
                try {
                    result = updateLoginDetails(params[0]);
                    break;
                } catch (IOException e) {
                    try {
                        if (--count == 0) {
                            break;
                        } else {
                            Thread.sleep(sleepTime * 2);
                        }
                    } catch (InterruptedException e1) {
                        break;
                    }
                }
            }
            return result;
        }

        private String updateLoginDetails(boolean isPurchased) throws IOException {
            String result = Constants.STR_FAILURE;
            LocalDB localDb = new LocalDB(getActivity());
            Loginendpoint.Builder loginBuilder = new Loginendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            loginBuilder = CloudEndpointUtils.updateBuilder(loginBuilder);
            Loginendpoint loginEndpoint = loginBuilder.build();
            Deviceinfoendpoint.Builder devInfoBuilder = new Deviceinfoendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            devInfoBuilder = CloudEndpointUtils.updateBuilder(devInfoBuilder);
            Deviceinfoendpoint devInfoEndpoint = devInfoBuilder.build();
            CollectionResponseDeviceInfo devInfoEntities = devInfoEndpoint.listDeviceInfo().setGcmRegId(strRegId).execute();
            LogIn login = loginEndpoint.getLogIn(Long.parseLong(strMobNo.substring(1))).execute();
            if  (login != null) {
                DeviceInfo device;
                if (devInfoEntities.getItems().size() > 0) {
                    device = devInfoEntities.getItems().get(0);
                } else {
                    device = new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device = devInfoEndpoint.insertDeviceInfo(device).execute();
                }
                List<Long> lstDevIds = login.getDeviceIDs();
                if (lstDevIds == null) {
                    lstDevIds = new ArrayList<>();
                }
                if (!isPurchased) {
                    lstDevIds.removeAll(lstDevIds);
                } else {
                    login.setPurchaseId(strUniqueString);
                }
                if (!lstDevIds.contains(device.getId())) {
                    lstDevIds.add(device.getId());
                }
                login.setPrefferedName(strScreenName);
                login.setDeviceIDs(lstDevIds);
                login = loginEndpoint.updateLogIn(login).execute();
                if (isPurchased) {
                    localDb.updatePurchaseId(strUniqueString);
                    localDb.updatePurchaseToSynced();
                }
                localDb.insert(login.getId(), login.getUsername(), login.getCountryCode(), Constants.STR_YOU, device.getId());
                localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU, Constants.STR_SYNCHED);
                result = Constants.STR_SYNC_NEEDED;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals(Constants.STR_SYNC_NEEDED)) {
                ActionBarActivity context = (ActionBarActivity) getActivity();
                Intent activityIntent = new Intent(context, TripsActivity.class);
                activityIntent.putExtra(Constants.STR_SYNC_NEEDED, true);
                startActivity(activityIntent);
                context.finish();
            } else {
                showRegistrationError("Oops!! Something went wrong. Please try again.");
            }
        }
    }

}