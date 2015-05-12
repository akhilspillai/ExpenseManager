package com.trip.expensemanager.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.trip.expensemanager.AllDetailsActivity;
import com.trip.expensemanager.R;
import com.trip.expensemanager.SyncIntentService;
import com.trip.expensemanager.adapters.CustomPeopleListAdapter;
import com.trip.expensemanager.beans.ExpenseBean;
import com.trip.expensemanager.beans.TripBean;
import com.trip.expensemanager.beans.UserBean;
import com.trip.expensemanager.database.LocalDB;
import com.trip.expensemanager.fragments.dialogs.ConfirmDialogListener;
import com.trip.expensemanager.fragments.dialogs.ConfirmationFragment;
import com.trip.utils.Constants;
import com.trip.utils.Global;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TripPeopleFragment extends CustomFragment implements OnItemClickListener, OnClickListener {

    private static final int REQUEST_CODE_SHOW_EXP = 1;
    private static final int PICK_CONTACT = 2;

    protected AlertDialog alert;
    private ListView listPeople;
    private long lngUserId;
    private ArrayAdapter<String> listAdapter;
    private List<String> arrPeople = new ArrayList<String>();
    private List<Long> arrPeopleIds = new ArrayList<Long>();
    private List<String> arrAmount = new ArrayList<String>();
    private TextView txtNoPeople;
    private long lngTripId;
    private String strTripName;
    private List<ExpenseBean> arrExpenses;
    private List<Boolean> arrSynced = new ArrayList<Boolean>();
    private long lngAdminId;
    private String strDate;
    private ImageButton btnAddPeople;
    private List<Integer> arrColors = new ArrayList<Integer>();

    public static TripPeopleFragment newInstance(String strTrip, long lngUserId, long lngTripId) {
        TripPeopleFragment fragment = null;
        try {
            fragment = new TripPeopleFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.STR_TRIP_NAME, strTrip);
            bundle.putLong(Constants.STR_USER_ID, lngUserId);
            bundle.putLong(Constants.STR_TRIP_ID, lngTripId);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_trip_people, container, false);
            listPeople = (ListView) rootView.findViewById(R.id.li_people);
            txtNoPeople = (TextView) rootView.findViewById(R.id.txt_no_people);
            btnAddPeople = (ImageButton) rootView.findViewById(R.id.btn_add_people);

            Bundle bundle = getArguments();
            lngUserId = bundle.getLong(Constants.STR_USER_ID);
            strTripName = bundle.getString(Constants.STR_TRIP_NAME);
            lngTripId = bundle.getLong(Constants.STR_TRIP_ID);
            listAdapter = new CustomPeopleListAdapter(getActivity(), arrPeople, arrAmount, arrSynced, arrColors);
            listPeople.setAdapter(listAdapter);
            listPeople.setOnItemClickListener(this);
            btnAddPeople.setOnClickListener(this);
            loadData();
            setHasOptionsMenu(true);
            //			registerForContextMenu(listPeople);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void loadData() {
        LocalDB localDb = new LocalDB(getActivity());
        arrPeople.removeAll(arrPeople);
        arrAmount.removeAll(arrAmount);
        arrSynced.removeAll(arrSynced);
        arrColors.removeAll(arrColors);
        try {
            TripBean trip = localDb.retrieveTripDetails(lngTripId);
            if (trip == null) {
                getActivity().finish();
                return;
            }
            lngTripId = trip.getId();
            strTripName = trip.getName();
            lngAdminId = trip.getAdminId();
            strDate = trip.getCreationDate();
            List<Long> lstPeopleIdTemp = Global.longToList(trip.getUserIds());
            UserBean user;

            for (long userIdTemp : lstPeopleIdTemp) {
                arrPeople.add(localDb.retrievePrefferedName(userIdTemp));
                arrPeopleIds.add(userIdTemp);
                arrAmount.add("0");
                user = localDb.retrieveUser(userIdTemp);
                if (user.getSynced().equals(Constants.STR_SYNCHED)) {
                    arrSynced.add(true);
                } else {
                    arrSynced.add(false);
                }
            }
            arrExpenses = localDb.retrieveExpenses(lngTripId);

            String amount = "0";
            int position = 0;
            boolean synced = false;
            for (ExpenseBean expense : arrExpenses) {
                if (!Constants.STR_DELETED.equals(expense.getSyncStatus()) && !Constants.STR_ERROR_STATUS.equals(expense.getSyncStatus())) {
                    position = arrPeopleIds.indexOf(expense.getUserId());
                    if (position >= 0) {
                        amount = arrAmount.get(position);
                        arrAmount.set(position, add(expense.getAmount(), amount));
                    }
                }
            }
            if (arrPeople.size() != 0) {
                if (listPeople.getVisibility() == View.INVISIBLE) {
                    listPeople.setVisibility(View.VISIBLE);
                    txtNoPeople.setVisibility(View.GONE);
                }
            }
            arrColors.addAll(Global.generateColor(arrPeople.size()));
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_people_fragment_action, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LocalDB localDB=new LocalDB(getActivity());
		switch (item.getItemId()) {
		case R.id.action_add_people:
			if(localDB.isTripSynced(lngTripId)){
				showAddPeopleFragment();
			} else{
				showMessage("You have to sync the expense group before adding people to it!!");
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}*/

    private void pickPeopleFromContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }


    private void showAddPeopleFragment() {
        //		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, AddPeopleFragment.newInstance(lngTripId), Constants.STR_ADD_PEOPLE_TAG).addToBackStack(null).commit();
        Intent intent = new Intent(getActivity(), AllDetailsActivity.class);
        intent.putExtra(Constants.STR_TRIP_NAME, strTripName);
        intent.putExtra(Constants.STR_TRIP_ID, lngTripId);
        intent.putExtra(Constants.STR_ADMIN_ID, lngAdminId);
        intent.putExtra(Constants.STR_DATE, strDate);

        intent.putExtra(Constants.STR_OPCODE, Constants.I_OPCODE_ADD_TRIP_QR);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.up_n_show, R.anim.no_anim);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showExpenseByPerson(arrPeopleIds.get(position), position);
    }

    private void showExpenseByPerson(long userId, int position) {
        Intent intent = new Intent(getActivity(), AllDetailsActivity.class);
        intent.putExtra(Constants.STR_USER_ID, lngUserId);
        intent.putExtra(Constants.STR_EXP_USR_ID, userId);
        intent.putExtra(Constants.STR_POSITION, position);
        intent.putExtra(Constants.STR_TRIP_ID, lngTripId);
        intent.putExtra(Constants.STR_ADMIN_ID, lngAdminId);

        intent.putExtra(Constants.STR_OPCODE, Constants.I_OPCODE_SHOW_USER_EXPENSES);
        startActivityForResult(intent, REQUEST_CODE_SHOW_EXP);
        getActivity().overridePendingTransition(R.anim.up_n_show, R.anim.no_anim);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        if (arrExpenses.get(position).getUserId() == lngUserId) {
            menu.setHeaderTitle(arrPeople.get(position));
            String[] menuItems = getResources().getStringArray(R.array.menu_people_list);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
//		String[] menuItems = getResources().getStringArray(R.array.menu_expense_list);
//		String menuItemName = menuItems[menuItemIndex];
        long lngUserId = arrPeopleIds.get(info.position);
        if (menuItemIndex == 0) {
            showDeletePersonDialog(arrPeople.get(info.position), lngUserId, info.position, strTripName);
        }
        return true;
    }


    protected void showDeletePersonDialog(final String username, final long userId, final int position, String tripName) {
        ConfirmDialogListener listener = new ConfirmDialogListener() {

            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                deleteUser(userId);
                dialog.dismiss();
            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }
        };
        ConfirmationFragment.newInstance(username, "Are you sure you want to delete the person " + username + " from trip " + tripName + "?", null, R.layout.fragment_dialog_confirm, listener).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    protected void deleteUser(long userId) {
        // TODO Auto-generated method stub

    }

    private String add(String amount1, String amount2) {
        BigDecimal bg1, bg2, bg3 = new BigDecimal("0");
        try {

            bg1 = new BigDecimal(amount1);
            bg2 = new BigDecimal(amount2);

            // subtract bg1 with bg2 using mc and assign result to bg3
            bg3 = bg1.add(bg2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bg3.toString();
    }

    @Override
    public void onClick(View v) {
        if (arrPeople.size() >= 5 && !isPurchased()) {
            String strContent = "You should be a premium user to add more people to this Expense Group.\n" + getActivity().getResources().getString(R.string.upgrade_features);
            showUpgradeDialog(strContent);
        } else {
            pickPeopleFromContacts();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_CONTACT){
            if (resultCode == Activity.RESULT_OK) {

                Uri contactData = data.getData();
                Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                if (c.moveToFirst()) {

                    List<String> allNumbers = new ArrayList<>();
                    String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                    String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1")) {
                        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        if (cursor.moveToFirst()) {
                            do {
                                allNumbers.add(cursor.getString(phoneIdx));
                            } while (cursor.moveToNext());
                        }

                        final String contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        final Context context = getActivity();

                        if(allNumbers.size() > 1) {

                            final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Choose a number");
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    String selectedNumber = items[item].toString();
                                    selectedNumber = selectedNumber.replace("-", "");
                                    addPerson(contactName, selectedNumber, context);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {

                            addPerson(contactName, allNumbers.get(0), context);

                        }
                    }

                }
            }
        }
    }

    private void addPerson(String strName, String strNumber, Context context) {

        LocalDB localDb = new LocalDB(context);

        strNumber  = removeSpaces(strNumber);

        long lngNumber = 0L;

        if(!strNumber.startsWith("+")) {
            try {
                lngNumber = Long.parseLong(strNumber);
            } catch (NumberFormatException e) {
                showInfoMessage("The phone number doesn't appear to be proper. Please Check.");
                return;
            }

            strNumber = String.valueOf(lngNumber);

            strNumber = localDb.getCountryCode() + strNumber;
        } else {
            try {
                lngNumber = Long.parseLong(strNumber.substring(1));
            } catch (NumberFormatException e) {
                showInfoMessage("The phone number doesn't appear to be proper. Please Check.");
                return;
            }
        }

        localDb.insertPerson(lngNumber, strNumber, strName, Constants.STR_NOT_SYNCHED);
        TripBean trip = localDb.retrieveTripDetails(lngTripId);
        String tripUsers = trip.getUserIds();

        StringBuffer sbUsers = new StringBuffer(tripUsers);

        sbUsers.append(",").append(String.valueOf(lngNumber));

        localDb.updateTripUsers(lngTripId, sbUsers.toString(), Constants.STR_QR_ADDED);


        Intent serviceIntent = new Intent(context, SyncIntentService.class);
        context.startService(serviceIntent);

    }

    private String removeSpaces(String strNumber) {

        int length = strNumber.length();
        StringBuilder sbNumber = new StringBuilder(strNumber);

        for(int i = 0 ; i < length ; i++) {
            if(sbNumber.charAt(i) == ' ') {
                sbNumber.deleteCharAt(i);
                length--;
            }
        }

        return sbNumber.toString();
    }

}
