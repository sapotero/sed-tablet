package sapotero.sed.Account;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Parcel;

@SuppressLint("ParcelCreator")
public class EsdAccount extends Account {

  public static final String TYPE = "sapotero.sed";
  public static final String TOKEN_FULL_ACCESS = "sapotero.sed.TOKEN_FULL_ACCESS";
  public static final String KEY_PASSWORD = "sapotero.sed.KEY_PASSWORD";

  public EsdAccount(Parcel in) {
    super(in);
  }

  public EsdAccount(String name) {
    super(name, TYPE);
  }

}
