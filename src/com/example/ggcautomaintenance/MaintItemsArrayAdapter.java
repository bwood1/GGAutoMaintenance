/**
 * MaintItemsArrayAdapter is used to build a listView item which contains two lines.
 */

package com.example.ggcautomaintenance;

import android.content.Context;

public class MaintItemsArrayAdapter extends TwoLineArrayAdapter<MaintItems> {
	
	private int mileageDue;
	
    public MaintItemsArrayAdapter(Context context, MaintItems[] items) {
        super(context, items);
    }

    /**
     * lineOneText method
     * @return first line of text for the listView item
     */
    @Override
    public String lineOneText(MaintItems mi) {
        return mi.getMaintDescription();
    }

    /**
     * lineTwoText method
     * @return second line of text for the listView item
     */
    @Override
    public String lineTwoText(MaintItems mi) {
        return "Due in " + getMileageDue() + " miles or " + mi.getTimeInterval() + " weeks";
    }
    
    /**
	 * getMileageDue method returns the number of miles the maintenance item is due for repair
	 * @return mileageDue - number of miles till repair.
	 */
	public float getMileageDue() {
		MPG mpg = new MPG();
		int currentMileage = mpg.getCurrentOdometer();
		int lastMaintMileage = currentMileage - 1000;		// *******  1000 is hardcoded for test. Need to pull mileage of last repair for maintItem
		mileageDue = currentMileage - lastMaintMileage;
		return mileageDue;
	}
}