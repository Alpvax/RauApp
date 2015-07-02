package alpvax.rau.messaging;

import com.google.android.gms.gcm.GcmListenerService;

import alpvax.rau.notify.NotificationHelper;
import android.os.Bundle;
import android.util.Log;

public class RauGcmListenerService extends GcmListenerService
{
	private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "Action: " + data.getString("action"));

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        NotificationHelper.notifyMessage(this, message);
    }
}
