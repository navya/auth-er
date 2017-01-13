package in.pclub.auth_er;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pallav on 1/13/17.
 */

public class Connection extends AsyncTask<Void, Void, String> {
    private View v;

    public Connection(View context) {
        this.v = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        String str = null;
        int code = 0;
        try {
            URL url = new URL("http://www.example.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            code = urlConnection.getResponseCode();
        } catch (Exception e) {
            str = e.toString();
        } finally {
            urlConnection.disconnect();
        }
        return code + " " + str;
    }

    @Override
    protected void onPostExecute(String result) {
        Snackbar.make(v, result, Snackbar.LENGTH_LONG).setAction("Action!!", null).show();
    }
}
