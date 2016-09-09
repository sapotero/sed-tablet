package sapotero.sed.Account;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sapotero.sed.R;

public class AuthTokenLoader extends AsyncTaskLoader<String> {

  private final String mTokenUrl;
  private final String mLogin;
  private final String mPassword;

  private String mAuthToken;

  public AuthTokenLoader(Context context, String login, String password) {
    super(context);
    mTokenUrl = context.getString(R.string.get_token_url, login, password);
    mLogin = login;
    mPassword = password;
  }

  public static String signIn(Context context, String login, String password) {
    try {
      return new AuthTokenLoader(context, login, password).signIn();
    } catch (IOException e) {
      Log.e(AuthTokenLoader.class.getSimpleName(), e.getMessage(), e);
    }
    return null;
  }

  @Override
  protected void onStartLoading() {
    if (TextUtils.isEmpty(mAuthToken)) {
      forceLoad();
    } else {
      deliverResult(mAuthToken);
    }
  }

  @Override
  public void deliverResult(String data) {
    mAuthToken = data;
    super.deliverResult(data);
  }

  @Override
  public String loadInBackground() {
    try {
      return signIn();
    } catch (IOException e) {
      Log.e(AuthTokenLoader.class.getSimpleName(), e.getMessage(), e);
    }
    return null;
  }

  private String signIn() throws IOException {
    final HttpURLConnection cn = (HttpURLConnection) new URL(mTokenUrl).openConnection();
    cn.setRequestMethod("PUT");
    cn.addRequestProperty("Accept", "application/json");
    return readToken(cn);
  }

  private String readToken(HttpURLConnection cn) throws IOException {
    final InputStream in = new BufferedInputStream(cn.getInputStream());
    try {
      final JSONObject json = new JSONObject(IOUtils.toString(in));
      if (json.has("auth_token")) {
        return json.getString("auth_token");
      }
    } catch (JSONException e) {
      Log.e(AuthTokenLoader.class.getSimpleName(), e.getMessage(), e);
    } finally {
      IOUtils.closeQuietly(in);
    }
    return null;
  }

}