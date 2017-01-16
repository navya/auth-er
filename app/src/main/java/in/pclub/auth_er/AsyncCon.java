package in.pclub.auth_er;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by pallav on 1/13/17.
 */

public class AsyncCon extends AsyncTask<Void, Void, Integer> {
    private View v;
    private Context c;

    public AsyncCon(Context c, View v) {
        this.v = v;
        this.c = c;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        SharedPreferences sharedPref = c.getSharedPreferences("data", 0);
        String username = sharedPref.getString("username", "username");
        String password = sharedPref.getString("password", "password");
        return Connection.attempt(username, password);
    }

    @Override
    protected void onPostExecute(Integer result) {
        Snackbar.make(v, result + "", Snackbar.LENGTH_LONG).setAction("Action!!", null).show();
    }
}
