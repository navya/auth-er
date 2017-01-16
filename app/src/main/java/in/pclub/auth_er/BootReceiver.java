package in.pclub.auth_er;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by saksham on 1/16/17.
 */

public class BootReceiver extends BroadcastReceiver {

    Alarm alarm = new Alarm();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("Danger", intent.getAction());
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            alarm.setAlarm(context);
        }
    }

}
