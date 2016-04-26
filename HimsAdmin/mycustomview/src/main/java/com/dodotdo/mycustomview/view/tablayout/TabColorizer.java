package com.dodotdo.mycustomview.view.tablayout;

/**
 * Created by krcho on 2015-10-14.
 */
public interface TabColorizer {

    /**
     * @return return the color of the indicator used when {@code position} is selected.
     */
    int getIndicatorColor(int position);

    /**
     * @return return the color of the divider drawn to the right of {@code position}.
     */
    int getDividerColor(int position);

}

