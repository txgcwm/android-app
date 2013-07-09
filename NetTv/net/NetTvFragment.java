/*****************************************************************************
 * HistoryFragment.java
 *****************************************************************************
 * Copyright © 2012 VLC authors and VideoLAN
 * Copyright © 2012 Edward Wang
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package org.videolan.vlc.gui.net;

import org.videolan.vlc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class NetTvFragment extends SherlockListFragment {
    public final static String TAG = "NetTvFragment";

    private NetTvAdapter mNetTvAdapter;

    /* All subclasses of Fragment must include a public empty constructor. */
    public NetTvFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetTvAdapter = new NetTvAdapter();
        Log.d(TAG, "HistoryFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.video_list, container, false);
        setListAdapter(mNetTvAdapter);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Fragment current = getFragmentManager().findFragmentById(R.id.fragment_placeholder);
    	Fragment nextFragment = new ChannelFragment((Channel)mNetTvAdapter.getItem(position));

        if(current == null) { /* Already selected */
            return;
        }

        /*
         * Clear any backstack before switching tabs. This avoids
         * activating an old backstack, when a user hits the back button
         * to quit
         */
        for(int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
            getFragmentManager().popBackStack();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(current);
        ft.replace(R.id.fragment_placeholder,nextFragment);
        //ft.attach(nextFragment);
        ft.addToBackStack(null);
        ft.commit();
        super.onListItemClick(l, v, position, id);
    }
}
