package sapotero.sed.Account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EsdAuthenticatorService extends Service {

  private EsdAuthenticator mAuthenticator;

  @Override
  public void onCreate() {
    super.onCreate();
    mAuthenticator = new EsdAuthenticator(getApplicationContext());
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mAuthenticator.getIBinder();
  }

}
