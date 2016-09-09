package sapotero.sed.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import sapotero.sed.Account.EsdAccount;
import sapotero.sed.R;
import sapotero.sed.SED;

public class AccountListActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    final AccountManager am = AccountManager.get(this);

    if (am.getAccountsByType(EsdAccount.TYPE).length == 0) {
      addNewAccount(am);
    }

    if (savedInstanceState == null) {
      Account[] accounts = am.getAccountsByType(EsdAccount.TYPE);
      for (int i = 0; i < accounts.length; i++) {
        SED.CURRENT_USER = accounts[i];
      }

      Intent intent = new Intent(this, DocumentListActivity.class);
      startActivity(intent);
    }
  }

  private void addNewAccount(AccountManager am) {
    am.addAccount(EsdAccount.TYPE, EsdAccount.TOKEN_FULL_ACCESS, null, null, this,
        new AccountManagerCallback<Bundle>() {
          @Override
          public void run(AccountManagerFuture<Bundle> future) {
            try {
              future.getResult();
            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
              AccountListActivity.this.finish();
            }
          }
        }, null
    );
  }

}
