/*****************************************************************************
 * HistoryAdapter.java
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
import org.videolan.vlc.Util;
import org.videolan.vlc.VLCApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.videolan.vlc.gui.net.Item;

public class ChannelAdapter extends BaseAdapter {
    public final static String TAG = "VLC/NetTvAdapter";

    private LayoutInflater mInflater;
    Channel mChannel;
    
    public ChannelAdapter(Channel channel) {
    	mInflater = LayoutInflater.from(VLCApplication.getAppContext());

		mChannel = channel;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e(TAG,String.valueOf(mChannel.getChannelItemNum()));
		return mChannel.getChannelItemNum();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mChannel.getChannelItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        View v = convertView;
        
        if (v == null) {
            v = mInflater.inflate(R.layout.video_list_item, parent, false);
            holder = new ViewHolder();
            holder.layout = v.findViewById(R.id.layout_item);
            holder.thumbnail = (ImageView) v.findViewById(R.id.ml_item_thumbnail);
            holder.title = (TextView) v.findViewById(R.id.ml_item_title);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        Item item = (Item)getItem(position);
        Util.setItemBackground(holder.layout, position);
        holder.title.setText(item.getItemTitle());

        Bitmap thumbnail;
        if (item.getItemPicture() != null) {
            thumbnail = item.getItemPicture();
            holder.thumbnail.setImageBitmap(thumbnail);
        } else {
            // set default thumbnail
            thumbnail = BitmapFactory.decodeResource(v.getResources(), R.drawable.thumbnail);
            holder.thumbnail.setImageBitmap(thumbnail);
        }

        return v;
	}
    
	static class ViewHolder {
        View layout;
        ImageView thumbnail;
        TextView title;
    }
}
