package com.terapanth.abtmm;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.terapanth.abtmm.home.HomeFragment;
import com.terapanth.abtmm.network.NetworkConnectivityCheckReceiver;
import com.terapanth.abtmm.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private NetworkConnectivityCheckReceiver networkConnectivityCheckReceiver;
    private boolean displayAlertForNetworkFailure = true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        bar.setTitle(getString(R.string.app_name));

        fragmentManager = getFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.addToBackStack(HomeFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.navigation_news:
                Toast.makeText(getApplicationContext(), "News menu page will display", Toast.LENGTH_SHORT).show();
                //add the function to perform here
//                fragmentManager = getFragmentManager();
//                FirstFragment firstFragment = new FirstFragment();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, firstFragment);
//                fragmentTransaction.addToBackStack(FirstFragment.class.getSimpleName());
//                fragmentTransaction.commit();
                return(true);
            case R.id.navigation_narilok:
                Toast.makeText(getApplicationContext(), "Narilok menu page will display", Toast.LENGTH_SHORT).show();
                //add the function to perform here
//                fragmentManager = getFragmentManager();
//                SecondFragment secondFragment = new SecondFragment();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, secondFragment);
//                fragmentTransaction.addToBackStack(FirstFragment.class.getSimpleName());
//                fragmentTransaction.commit();
                return(true);

            case R.id.navigation_courses:
                Toast.makeText(getApplicationContext(), "Courses menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_hobby_zone:
                Toast.makeText(getApplicationContext(), "Hobby Zone menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_events_calendar:
                Toast.makeText(getApplicationContext(), "Events Calendar menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_gallery:
                Toast.makeText(getApplicationContext(), "Gallery menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_business_women_network:
                Toast.makeText(getApplicationContext(), "BWN menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_healing_counselling:
                Toast.makeText(getApplicationContext(), "Healing & Counselling menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_about_us:
                Toast.makeText(getApplicationContext(), "About Us menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_contact_us:
                Toast.makeText(getApplicationContext(), "Contact Us menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_user_profile:
                Toast.makeText(getApplicationContext(), "User Profile menu page will display", Toast.LENGTH_SHORT).show();
                return(true);

            case R.id.navigation_share_app:
                Toast.makeText(getApplicationContext(), "Share App menu page will display", Toast.LENGTH_SHORT).show();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkConnectivityCheckReceiver = new NetworkConnectivityCheckReceiver(this, displayAlertForNetworkFailure);
        registerReceiver(networkConnectivityCheckReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        progressDialog = new ProgressDialog(this);

        if(NetworkUtils.getConnectivityStatus(getApplicationContext())) {

        } else {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(null != networkConnectivityCheckReceiver) {
            unregisterReceiver(networkConnectivityCheckReceiver);
        }
    }

    @Override
    public void onBackPressed() {

        final FragmentManager fragmentManager = getFragmentManager();

        int currentBackStackIndex = fragmentManager.getBackStackEntryCount() - 1;
        String currentTag = fragmentManager.getBackStackEntryAt(currentBackStackIndex).getName();

        if (currentTag.equalsIgnoreCase(HomeFragment.class.getSimpleName())) {
            finish();
        }

        // If no Fragment found in stack, finish app.
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Function to show progress dialog.
     */
    public void showProgressDialog(boolean toShow, String message) {
        if (toShow) {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();

            return;
        }

        progressDialog.dismiss();
    }

    public void showAlertForNetworkFailure(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(title);
        builder.setMessage(msg);

        if(msg.equalsIgnoreCase(getString(R.string.alert_network_failure_message))) {

            displayAlertForNetworkFailure = false;

            builder.setPositiveButton(R.string.alert_network_failure_positive_button, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);

                    displayAlertForNetworkFailure =true;
                }
            });

            builder.setNegativeButton(R.string.alert_network_failure_negative_button, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    displayAlertForNetworkFailure =true;
                }
            });

        } else {
            builder.setPositiveButton(R.string.alert_failure_positive_button, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
        }

        AlertDialog alert = builder.create();
        alert.show();
    }

}
