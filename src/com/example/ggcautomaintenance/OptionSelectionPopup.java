/**
 * 
 */
package com.example.ggcautomaintenance;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;


/**
 * @author Nicholas
 *
 */
public class OptionSelectionPopup extends PopupWindow {
	
	
	Context		m_context;

    public OptionSelectionPopup(Context context)
    {
        super(context);

        m_context = context;

        setContentView(LayoutInflater.from(context).
             inflate(R.layout.option_selection, null));

        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void show(View anchor)
    {
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
        setTouchable(true);    
		setFocusable(true);    
		setOutsideTouchable(false);
    }
}