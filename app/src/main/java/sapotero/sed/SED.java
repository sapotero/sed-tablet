package sapotero.sed;

import android.accounts.Account;

import java.util.Random;

public class SED {
  public static Account CURRENT_USER = null;
  public String getToken(){
    Random r = new Random();
    StringBuffer sb = new StringBuffer();
    while(sb.length() < 32){
      sb.append(Integer.toHexString(r.nextInt()));
    }
    return sb.toString().substring(0, 32);
  }
}
