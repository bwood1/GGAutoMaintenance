package com.ggcautomaintenance2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class ChangeDefaultPopup extends PopupWindow {

/**
 * @author Nicholas
 * Class for Change Default Prompt creation
 */		
	
	Context		m_context;
	
	// Constructor
    public ChangeDefaultPopup(Context context)
    {
        super(context);

        m_context = context;

        setContentView(LayoutInflater.from(context).
             inflate(R.layout.default_change_popup, null));

        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    }
    
    /**
     * Anchors the popup screen
     * @param anchor
     */
    public void show(View anchor)
    {
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
        setTouchable(true);    
		setFocusable(true);    
		setOutsideTouchable(false);
    }
}	

