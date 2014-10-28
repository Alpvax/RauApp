package alpvax.rau.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;

public class RauKeyboard extends Keyboard
{
	private Key enterKey;
    private Key spaceKey;
    
    public RauKeyboard(Context context, int xmlLayoutResId)
    {
        super(context, xmlLayoutResId);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser)
    {
        Key key = new AukKey(res, parent, x, y, parser);
        if(key.codes[0] == 10)
        {
            enterKey = key;
        }
        else if(key.codes[0] == ' ')
        {
            spaceKey = key;
        }
        return key;
    }
    
    /**
     * This looks at the ime options given by the current editor, to set the
     * appropriate label on the keyboard's enter key (if it has one).
     */
    public void setImeOptions(Resources res, int options)
    {
        if(enterKey == null)
        {
            return;
        }
        
        /*TODO:switch (options&(EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION))
        {
            case EditorInfo.IME_ACTION_GO:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText(R.string.label_go_key);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText(R.string.label_next_key);
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                enterKey.icon = res.getDrawable(R.drawable.sym_keyboard_search);
                enterKey.label = null;
                break;
            case EditorInfo.IME_ACTION_SEND:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText(R.string.label_send_key);
                break;
            default:
                enterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
                enterKey.label = null;
                break;
        }*/
    }

    void setSpaceIcon(final Drawable icon)
    {
        if(spaceKey != null)
        {
            spaceKey.icon = icon;
        }
    }

    static class AukKey extends Keyboard.Key
    {
        public AukKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser)
        {
            super(res, parent, x, y, parser);
            //TODO: label = 
        }
        
        /**
         * Overriding this method so that we can reduce the target area for the key that
         * closes the keyboard. 
         */
        @Override
        public boolean isInside(int x, int y)
        {
            return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
        }
    }
}
