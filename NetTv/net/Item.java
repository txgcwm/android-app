package org.videolan.vlc.gui.net;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Item {
	private String title;
	private String link;
	private String thumbnail;
	private String fanart;
	private Bitmap mPicture;

	public Item() {		
	}
	
	public void setItemTitle(String title) {
		this.title = title;
	}

	public void setItemLink(String link) {
		this.link = link;
	}

	public void setItemThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setItemFanart(String fanart) {
		this.fanart = fanart;
	}
	
	public void setItemPicture(String thumbnail) {
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
	
	public String getItemTitle() {
		return this.title;
	}

	public String getItemLink() {
		return this.link;
	}

	public String getItemThumbnail() {
		return this.thumbnail;
	}

	public String getItemFanart() {
		return this.fanart;
	}
	
	public Bitmap getItemPicture() {
        return this.mPicture;
    }
}
