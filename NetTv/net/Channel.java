package org.videolan.vlc.gui.net;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Channel {
	private  String name;
	private  String thumbnail;
	private  String fanart;
	private  String info;
	private Bitmap mPicture;
	private  List<Item> items = new ArrayList<Item>();
	
	public Channel() {
	}
	
	public void addChannelItem(Item item) {
		this.items.add(item);
	}
	
	public void setChannelName(String name) {
		this.name = name;
	}

	public void setChannelThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setChannelFanart(String fanart) {
		this.fanart = fanart;
	}
	
	public void setChannelInfo(String info) {
		this.info = info;
	}
	
	public void setChannelPicture(String thumbnail) {
		try{
    		URL mUrl = new URL(thumbnail);	
    		URLConnection mConn = mUrl.openConnection();	
    		InputStream in = mConn.getInputStream();		
    		this.mPicture = BitmapFactory.decodeStream(in);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	public String getChannelName() {
		return this.name;
	}

	public String getChannelThumbnail() {
		return this.thumbnail;
	}

	public String getChannelFanart() {
		return this.fanart;
	}
	
	public String getChannelInfo() {
		return this.info;
	}	
	
	public int getChannelItemNum() {
		return this.items.size();
	}
	
	public Item getChannelItem(int index){
		return this.items.get(index);
	}
	
	public Bitmap getChannelPicture() {
        return this.mPicture;
    }
}
