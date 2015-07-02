package alpvax.rau.util.tasks;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import alpvax.rau.R;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class GAPIRegistrationService extends IntentService
{
	private static final String TAG = "RAU: Google API ID Registration";
	
	public GAPIRegistrationService()
	{
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
        try
        {
        	/* In the (unlikely) event that multiple refresh operations occur simultaneously,
        	 * ensure that they are processed sequentially.
        	 */
	        synchronized(TAG)
	        {
	            /* [START register_for_gcm]
	             * Initially this call goes out to the network to retrieve the token, subsequent calls
	             * are local.
	             * [START get_token]
	             */
	            InstanceID instanceID = InstanceID.getInstance(this);
	            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
	        }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Failed to complete token refresh", e);
        }
	}

}
