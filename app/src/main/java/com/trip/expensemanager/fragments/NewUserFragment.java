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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
import com.trip.expensemanager.ExpenseActivity;
import com.trip.expensemanager.R;
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
import com.trip.expensemanager.loginendpoint.model.CollectionResponseLogIn;
import com.trip.expensemanager.loginendpoint.model.LogIn;
import com.trip.expensemanager.messageDataApi.MessageDataApi;
import com.trip.expensemanager.messageDataApi.model.MessageData;
import com.trip.utils.Constants;
import com.trip.utils.Global;
import com.trip.utils.LocalDB;
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

    public static NewUserFragment newInstance() {
        NewUserFragment fragment=null;
        try {
            fragment=new NewUserFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    protected Animation bounceImage;
    protected Animation exitAnim;
    protected Animation enterAnim;
    protected boolean pwdChecked=false;
    protected boolean usernameChecked=false;
    protected boolean prefferedNameChecked;
    protected String strPrefferedName;
    private String strMobNo, strCountryCode, strToken;
    private EditText etxtMobNo, etxtToken;
    private AutoCompleteTextView etxtCountryCode;
    private ScrollView sc;
    private RelativeLayout rl;
    private LinearLayout llExit;
    private LinearLayout llNext;
    private View anim;
    private IabHelper mHelper;
    private boolean isPurchaseReady=false;
    private String strUniqueString;
    public String strRegId;
    public String strDeviceName;
    private Button btnVerify;
    private BroadcastReceiver smsReceiver;
    private RegisterTask registerTask;
    private SendSMSTask sendSMSTask;
    IntentFilter filter;

    private List<String> lstCountryCodes;

    private HashMap<String, String> hmCountries = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView=null;
        super.onCreateView(inflater, container, savedInstanceState);
        rootView= inflater.inflate(R.layout.fragment_new_user,container, false);

        anim = rootView.findViewById(R.id.anim_line);
        sc = (ScrollView) rootView.findViewById(R.id.sc);
        rl = (RelativeLayout) rootView.findViewById(R.id.footer);
        llExit = (LinearLayout) rootView.findViewById(R.id.ll_exit);
        llNext = (LinearLayout) rootView.findViewById(R.id.ll_next);
        etxtMobNo = (EditText) rootView.findViewById(R.id.etxt_mob_no);
        etxtCountryCode = (AutoCompleteTextView) rootView.findViewById(R.id.etxt_country);
        btnVerify = (Button) rootView.findViewById(R.id.btn_verify);
        etxtToken = (EditText) rootView.findViewById(R.id.etxt_token);

        lstCountryCodes = Arrays.asList(getResources().getStringArray(R.array.country_codes));

        ArrayAdapter<String> autocompletetextAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lstCountryCodes);

        etxtCountryCode.setAdapter(autocompletetextAdapter);

        btnVerify.setOnClickListener(this);
        llExit.setOnClickListener(this);
        llNext.setOnClickListener(this);

        new GetCountriesTask().execute();

        return rootView;
    }


    private void showErrorDialog(String strMessage) {
        InfoDialogListener listener=new InfoDialogListener() {

            @Override
            public void onDialogButtonClick(DialogFragment dialog) {
                anim.setVisibility(View.VISIBLE);
                rl.setVisibility(View.INVISIBLE);
                sc.setVisibility(View.INVISIBLE);
                enableVerification();
                dialog.dismiss();
            }
        };
        InformationFragment.newInstance("Error", strMessage, "OK", R.layout.fragment_dialog_info, listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }



    @Override
    public void onClick(View v) {
        if(v.equals(llExit)){
            getActivity().finish();
        } else if(v.equals(llNext)) {
            if (Global.validate(etxtToken)) {
                strCountryCode = etxtCountryCode.getText().toString();
                strMobNo = etxtMobNo.getText().toString();
                strToken = etxtToken.getText().toString();

                strMobNo = strCountryCode+strMobNo;

                startRegistration();
            }
        } else if(v.equals(btnVerify)) {
            if (Global.validate(etxtCountryCode, etxtMobNo)) {
                strCountryCode = etxtCountryCode.getText().toString();
                strMobNo = etxtMobNo.getText().toString();
                if(lstCountryCodes.contains(strCountryCode)) {
                    smsReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Bundle extras = intent.getExtras();

                            if (extras != null) {
                                Object[] smsextras = (Object[]) extras.get("pdus");

                                for (int i = 0; i < smsextras.length; i++) {
                                    SmsMessage smsmsg = SmsMessage.createFromPdu((byte[]) smsextras[i]);

                                    String strMsgBody = smsmsg.getMessageBody().toString();

                                    int index = strMsgBody.indexOf("Your verification code is ");
                                    if (index != -1) {
                                        index = index + 26;
                                        etxtToken.setText(strMsgBody.substring(index));
                                        getActivity().unregisterReceiver(this);
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
                    Toast.makeText(getActivity(), "Please enter a valid country code", Toast.LENGTH_LONG);
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


        if(Global.isConnected(getActivity())) {

            if (sendSMSTask != null && !sendSMSTask.isCancelled()) {
                sendSMSTask.cancel(true);
                sendSMSTask = null;
            }

            sendSMSTask = new SendSMSTask(strCountryCode, strMobNo);
            sendSMSTask.execute();
        } else{
            showErrorDialog("Please connect to internet to continue.");
            return;
        }
    }

    private void enableVerification(){
        btnVerify.setEnabled(true);
        etxtMobNo.setEnabled(true);
        etxtCountryCode.setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(smsReceiver != null && filter != null) {
            getActivity().registerReceiver(smsReceiver, filter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(smsReceiver);
    }

    private void startRegistration(){
        if(Global.isConnected(getActivity())) {
            if (registerTask != null && !registerTask.isCancelled()) {
                registerTask.cancel(true);
                registerTask = null;
            }

            registerTask = new RegisterTask(strMobNo, strToken);
            registerTask.execute();
        } else {
            showErrorDialog("Please connect to internet to continue.");
        }
    }

    private class GetCountriesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getCountryData();
            return null;
        }

        private void getCountryData()
        {
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale locale : locales)
            {
                String countryCode = locale.getCountry();
                String country = locale.getDisplayCountry();
                if (country.trim().length()>0 && !hmCountries.containsKey(countryCode))
                {
                    hmCountries.put(countryCode, country);
                }
            }
        }

    }

    private class SendSMSTask extends AsyncTask<Void, Void, MessageData> {

        String strMobNo;
        String strCountryCode;

        SendSMSTask(String strCountryCode, String strMobNo){
            this.strMobNo = strMobNo;
            this.strCountryCode = strCountryCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MessageData doInBackground(Void... params) {

            strMobNo = strCountryCode+strMobNo;
            MessageData retMessageData = createToken();

            return retMessageData;
        }

        @Override
        protected void onPostExecute(MessageData messageData) {
            super.onPostExecute(messageData);
            if(messageData == null){
                showErrorDialog("Unable to contact the server. Please try again.");
                enableVerification();
            }
        }

        private MessageData createToken(){
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
            } catch(IOException e){
                e.printStackTrace();
            }
            return retMessageData;
        }
    }

    private class RegisterTask extends AsyncTask<Void, Void, String> {

        String strMobNo;
        String strToken;

        private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        public static final String PROJECT_NUMBER = "28707109757";
        private GoogleCloudMessaging gcm;

        RegisterTask(String strMobNo, String strToken) {
            this.strMobNo = strMobNo;
            this.strToken = strToken;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((ExpenseActivity)getActivity()).setCustomTitle(R.string.done);
            anim.setVisibility(View.VISIBLE);
            rl.setVisibility(View.INVISIBLE);
            sc.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String strResult = null;
            try {
                if(isTokenValid(strToken)){
                    strResult = getUserId();
                } else {
                    strResult = Constants.STR_TOKEN_INVALID;
                }
            } catch(IOException e){

            }
            return strResult;
        }

        private boolean isTokenValid(String strToken){
            MessageDataApi.Builder messageBuilder = new MessageDataApi.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            messageBuilder = CloudEndpointUtils.updateBuilder(messageBuilder);
            MessageDataApi messageEndpoint = messageBuilder.build();
            MessageData messageData = null;
            try {
                messageData = messageEndpoint.getMessageData(Long.parseLong(strMobNo.substring(1))).execute();
            } catch(IOException e){
                e.printStackTrace();
            }
            if(messageData !=null){
                return strToken.equals(messageData.getToken());
            }
            return false;
        }

        private String updateLogin() throws IOException {
            String result;
            LocalDB localDb=new LocalDB(getActivity());
            long lngUserId=localDb.retrieve();
            Loginendpoint.Builder loginBuilder = new Loginendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            loginBuilder = CloudEndpointUtils.updateBuilder(loginBuilder);
            Loginendpoint loginEndpoint = loginBuilder.build();
            LogIn login=loginEndpoint.getLogIn(lngUserId).execute();
            login.setUsername(strMobNo);
            login=loginEndpoint.updateLogIn(login).execute();
            localDb.update(login.getUsername(), login.getId());
            localDb.updatePerson(login.getUsername(), login.getId());
            result=Constants.STR_SUCCESS;
            return result;
        }

        private String getUserId() throws IOException {
            String strResult=Constants.STR_FAILURE;
            LogIn login = null;
            DeviceInfo device;
            LocalDB localDb=new LocalDB(getActivity());
            Loginendpoint.Builder loginBuilder = new Loginendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            loginBuilder = CloudEndpointUtils.updateBuilder(loginBuilder);
            Loginendpoint loginEndpoint = loginBuilder.build();
            Deviceinfoendpoint.Builder devInfoBuilder = new Deviceinfoendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            devInfoBuilder = CloudEndpointUtils.updateBuilder(devInfoBuilder);
            Deviceinfoendpoint devInfoEndpoint = devInfoBuilder.build();
            strDeviceName = getDeviceName();
            strRegId=registerForGCM();
            if(strRegId == null){
                return strResult;
            }
            long id = Long.parseLong(strMobNo.substring(1));
            try {
                login = loginEndpoint.getLogIn(id).execute();
            } catch (IOException e) {

            }
            CollectionResponseDeviceInfo devInfoEntities = devInfoEndpoint.listDeviceInfo().setGcmRegId(strRegId).execute();
            if (login == null) {
                if (devInfoEntities == null || devInfoEntities.getItems() == null || devInfoEntities.getItems().size() < 1) {
                    device=new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device=devInfoEndpoint.insertDeviceInfo(device).execute();
                } else{
                    device=devInfoEntities.getItems().get(0);
                }
                login=new LogIn();
                List<Long> devList=new ArrayList<Long>(1);
                devList.add(device.getId());
                login.setId(id);
                login.setDeviceIDs(devList);
                login.setUsername(strMobNo);
                login=loginEndpoint.insertLogIn(login).execute();
                localDb.insert(login.getId(), login.getUsername(), login.getPassword(), Constants.STR_YOU, device.getId());
                localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU);
                strResult=Constants.STR_SUCCESS;
            } else{
                if (devInfoEntities == null || devInfoEntities.getItems() == null || devInfoEntities.getItems().size() < 1) {
                    device=new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device=devInfoEndpoint.insertDeviceInfo(device).execute();
                    if(isPurchased()){
                        List<Long> lstDevIds=login.getDeviceIDs();
                        if(lstDevIds==null){
                            lstDevIds=new ArrayList<Long>();
                        }
                        lstDevIds.add(device.getId());
                        login.setDeviceIDs(lstDevIds);
                        login=loginEndpoint.updateLogIn(login).execute();
                        localDb.insert(login.getId(), login.getUsername(), login.getPassword(), Constants.STR_YOU, device.getId());
                        localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU);
                        strResult=Constants.STR_SYNC_NEEDED;
                    } else{
                        strResult=Constants.STR_NOT_PURCHASED;
                    }
                } else{
                    device=devInfoEntities.getItems().get(0);
                    localDb.insert(login.getId(), login.getUsername(), login.getPassword(), Constants.STR_YOU, device.getId());
                    localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU);
                    localDb.insert(login.getId(), login.getUsername(), login.getPassword(), Constants.STR_YOU, device.getId());
                    localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU);
                    strResult=Constants.STR_SYNC_NEEDED;
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
            String strRegId=null;
            int count=5;
            long sleepTime=100;
            if(checkPlayServices()){
                while(true){
                    try{
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(getActivity());
                        }
                        strRegId = gcm.register(PROJECT_NUMBER);
                        break;
                    } catch (IOException e) {
                        if(--count==0){
                            break;
                        } else{
                            try {
                                Thread.sleep(sleepTime*2);
                            } catch (InterruptedException e1) {

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
                        GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
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
            ActionBarActivity context=(ActionBarActivity)getActivity();
            LocalDB localDb=new LocalDB(context);
            long lngUserId=localDb.retrieve();
            if(result == null) {

            } else if(result.equals(Constants.STR_FAILURE)){
                showRegistrationError("Oops!! Something went wrong. Please try again.");
            } else if(result.equals(Constants.STR_NOT_PURCHASED)){
                String strMessage=getActivity().getResources().getString(R.string.upgrade_features);
                strMessage=Constants.STR_LOGIN_DIFF_DEV+strMessage;
                showPurchaseOrNotDialog(strMessage);
            } else if(result.equals(Constants.STR_SYNC_NEEDED)){
//				Intent intent=new Intent(getActivity(), ExpenseActivity.class);
//				intent.putExtra(Constants.STR_SYNC_NEEDED, true);
//				getActivity().startActivity(intent);
//				getActivity().finish();
                context.setContentView(R.layout.activity_expense);
                Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);

                if (toolbar != null) {
                    context.setSupportActionBar(toolbar);
                }
                context.getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                context.getSupportFragmentManager().beginTransaction().replace(R.id.container, AddTripFragment.newInstance(lngUserId, true)).commit();
            } else if(result.equals(Constants.STR_SUCCESS)){
//				Intent intent=new Intent(getActivity(), ExpenseActivity.class);
//				intent.putExtra(Constants.STR_SYNC_NEEDED, false);
//				getActivity().startActivity(intent);
//				getActivity().finish();
                context.setContentView(R.layout.activity_expense);
                Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);

                if (toolbar != null) {
                    context.setSupportActionBar(toolbar);
                }
                context.getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                context.getSupportFragmentManager().beginTransaction().replace(R.id.container, AddTripFragment.newInstance(lngUserId, false)).commit();
            } else if(result.equals(Constants.STR_TOKEN_INVALID)){
                showErrorDialog("Invalid verificstion token. Please try again!!");
            }
        }

    }

    private void showPurchaseOrNotDialog(String strMessage) {
        ThreeButtonDialogListener listener=new ThreeButtonDialogListener() {

            @Override
            public void onDialogFirstButtonClick(DialogFragment dialog) {
                purchaseItem();
            }

            @Override
            public void onDialogSecondButtonClick(DialogFragment dialog) {
                new PurchaseLoginTask(strMobNo).execute(false);
            }

            @Override
            public void onDialogThirdButtonClick(DialogFragment dialog) {
                getActivity().finish();
            }

        };
        ThreeButtonFragment.newInstance(strMessage, listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    protected void purchaseItem() {
        if(!isPurchaseReady){
            String base64EncodedPublicKey=Constants.STR_LICENSE_1+
                    Constants.STR_LICENSE_2+Constants.STR_LICENSE_3+
                    Constants.STR_LICENSE_4+Constants.STR_LICENSE_5+
                    Constants.STR_LICENSE_6+Constants.STR_LICENSE_7;

            mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        Log.d("Akhil", "Problem setting up In-app Billing: " + result);
                        return;
                    }
                    isPurchaseReady=true;
                    continuePurchase();
                }
            });
        } else{
            continuePurchase();
        }
    }

    private void continuePurchase(){
        IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase)
            {
                if (result.isFailure()) {
                    Log.d("Akhil", "Error purchasing: " + result);
                    showMessage("There was an error in purchasing! Please try again.");
                    return;
                }
                else if (purchase.getSku().equals(Constants.STR_SKU_PREMIUM)) {
                    String strUniqueReturn=purchase.getDeveloperPayload();
                    if(strUniqueReturn!=null && strUniqueReturn.equals(strUniqueString)){
                        if(Global.isConnected(getActivity())){
                            new PurchaseLoginTask(strMobNo).execute(true);
                        }
                    } else{
                        showMessage("There was an error in purchasing! Please try again.");
                    }
                }
            }
        };
        strUniqueString=Global.randomString(25);
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(getActivity(), Constants.STR_SKU_PREMIUM, Constants.ORDER_ID,
                mPurchaseFinishedListener, strUniqueString);
    }

    private void showRegistrationError(String strMessage) {
        ConfirmDialogListener listener=new ConfirmDialogListener() {

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
        ConfirmationFragment.newInstance("Error", strMessage, "Try Again", R.layout.fragment_dialog_confirm, listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    private class PurchaseLoginTask extends AsyncTask<Boolean, Void, String>{

        String strMobNo;

        PurchaseLoginTask(String strMobNo) {
            this.strMobNo = strMobNo;
        }

        @Override
        protected String doInBackground(Boolean... params) {
            long sleepTime=100L;
            String result=null;
            while (true) {
                try {
                    if(!Global.isConnected(getActivity())){
                        break;
                    }
                    result=updateLoginDetails(params[0]);
                } catch (IOException e) {
                    try {
                        Thread.sleep(sleepTime*2);
                    } catch (InterruptedException e1) {

                    }
                }
            }
            return result;
        }

        private String updateLoginDetails(boolean isPurchased) throws IOException {
            String result=Constants.STR_FAILURE;
            LocalDB localDb=new LocalDB(getActivity());
            Loginendpoint.Builder loginBuilder = new Loginendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            loginBuilder = CloudEndpointUtils.updateBuilder(loginBuilder);
            Loginendpoint loginEndpoint = loginBuilder.build();
            Deviceinfoendpoint.Builder devInfoBuilder = new Deviceinfoendpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            devInfoBuilder = CloudEndpointUtils.updateBuilder(devInfoBuilder);
            Deviceinfoendpoint devInfoEndpoint = devInfoBuilder.build();
            CollectionResponseLogIn loginEntities = loginEndpoint.listLogIn().setUsername(strMobNo).execute();
            CollectionResponseDeviceInfo devInfoEntities = devInfoEndpoint.listDeviceInfo().setGcmRegId(strRegId).execute();
            if(loginEntities == null || loginEntities.getItems() == null || loginEntities.getItems().size() < 1 ){
                return result;
            } else{
                LogIn login = loginEntities.getItems().get(0);
                DeviceInfo device=null;
                if(loginEntities.getItems().size() > 0){
                    device = devInfoEntities.getItems().get(0);
                } else{
                    device=new DeviceInfo();
                    device.setMake(strDeviceName);
                    device.setGcmRegId(strRegId);
                    device=devInfoEndpoint.insertDeviceInfo(device).execute();
                }
                List<Long> lstDevIds=login.getDeviceIDs();
                if(lstDevIds==null){
                    lstDevIds=new ArrayList<Long>();
                }
                if(!isPurchased){
                    lstDevIds.removeAll(lstDevIds);
                }
                lstDevIds.add(device.getId());
                login.setDeviceIDs(lstDevIds);
                login.setPurchaseId(strUniqueString);
                login=loginEndpoint.updateLogIn(login).execute();
                SharedPreferences prefs = getActivity().getSharedPreferences(Constants.STR_PREFERENCE, Activity.MODE_PRIVATE);
                prefs.edit().putBoolean(Constants.STR_PURCHASED, true).commit();
                localDb.updatePurchaseId(strUniqueString);
                localDb.updatePurchaseToSynced();
                localDb.insert(login.getId(), login.getUsername(), login.getPassword(), Constants.STR_YOU, device.getId());
                localDb.insertPerson(login.getId(), login.getUsername(), Constants.STR_YOU);
                result=Constants.STR_SYNC_NEEDED;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals(Constants.STR_SYNC_NEEDED)){
                ActionBarActivity context=(ActionBarActivity)getActivity();
                LocalDB localDb=new LocalDB(context);
                long lngUserId=localDb.retrieve();
//				Intent intent=new Intent(getActivity(), ExpenseActivity.class);
//				intent.putExtra(Constants.STR_SYNC_NEEDED, true);
//				getActivity().startActivity(intent);
//				getActivity().finish();
                context.setContentView(R.layout.activity_expense);
                Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);

                if (toolbar != null) {
                    context.setSupportActionBar(toolbar);
                }
                context.getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                context.getSupportFragmentManager().beginTransaction().replace(R.id.container, AddTripFragment.newInstance(lngUserId, true)).commit();
            } else{
                showRegistrationError("Oops!! Something went wrong. Please try again.");
            }
        }
    }

}