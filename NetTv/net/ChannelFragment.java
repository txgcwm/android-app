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
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class ChannelFragment extends SherlockListFragment {
    public final static String TAG = "ChannelFragment";

    private ChannelAdapter mChannelAdapter;

    public ChannelFragment(Channel channel) { 
    	mChannelAdapter = new ChannelAdapter(channel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ChannelFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.video_list, container, false);
        setListAdapter(mChannelAdapter);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        playVideo(position);
        super.onListItemClick(l, v, position, id);
    }

    protected void playVideo(int position) {
        Item item = (Item) getListAdapter().getItem(position);
        VideoPlayerActivity.start(getActivity(), item.getItemLink());
    }
}
