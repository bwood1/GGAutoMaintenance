/**
 * MaintItemsArrayAdapter is used to build a listView item which contains two lines.
 */

package com.ggcautomaintenance;

import android.content.Context;

public class ListItemsArrayAdapter extends TwoLineArrayAdapter<ListItems> {
	
	
    public ListItemsArrayAdapter(Context context, ListItems[] items) {
        super(context, items);
    }

    /**
     * lineOneText method
     * @return first line of text for the listView item
     */
    @Override
    public String lineOneText(ListItems mi) {
        return mi.getMaintDescription();
    }

    /**
     * lineTwoText method
     * @return second line of text for the listView item
     */
    @Override
    public String lineTwoText(ListItems mi) {
    	return "Due in " + mi.getMileageNextDue() + " miles or on " + mi.getDateNextDue();
    }
}