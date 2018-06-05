package br.com.exemplo.syncapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "br.com.exemplo.syncapp.syncadapter.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "br.com.inovamobil.android";
    // The account name
    public static final String ACCOUNT = "Reader";
    // Instance fields
    Account mAccount;

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the dummy account
        mAccount = CreateSyncAccount(this);

        Button btnExecutarSyncAdapter = findViewById(R.id.btnExecutarSync);

        btnExecutarSyncAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutarSyncAdapter();
            }
        });

    }

    public void ExecutarSyncAdapter() {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);

    }

}
