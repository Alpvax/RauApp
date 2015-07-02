package alpvax.rau.messaging;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import alpvax.rau.R;
import alpvax.rau.messaging.MessageHandler.Message;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class MessageHandler extends AsyncTask<Message, Void, String>
{
	public int msgID = 0;
	public static class Message
	{
		private final Context sendingContext;
		private final String text;
		
		private Message(Context c, String m)
		{
			sendingContext = c;
			text = m;
		}
	}

	@Override
    protected String doInBackground(Message... m)
	{
        String msg = "";
        try
        {
            Bundle data = new Bundle();
                data.putString("message", m[0].text);
                data.putString("action", "com.google.android.gcm.demo.app.ECHO_NOW");
                GoogleCloudMessaging.getInstance(m[0].sendingContext).send(R.string.gcm_defaultSenderId + "@gcm.googleapis.com", Integer.toString(msgID++), data);
                msg = "Sent message";
        }
        catch(IOException e)
        {
            msg = "Error :" + e.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg)
    {
        //TODO: message sent. notfication.mDisplay.append(msg + "\n");
    }
}
