package com.example.sandy.loancollection.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by sandy on 30/1/18.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
