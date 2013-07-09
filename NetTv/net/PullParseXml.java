package org.videolan.vlc.gui.net;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

public class PullParseXml {
	public List<Channel> getChannels(InputStream inputStream) {
		List<Channel> channels = null;
		Channel channel = null;
		Item item = null;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "utf-8");
			int event = parser.getEventType();

			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					channels = new ArrayList<Channel>();
					break;
				case XmlPullParser.START_TAG:
					
					if ("channel".equals(parser.getName())) {
						channel = new Channel();
						int count = parser.getAttributeCount();
						for (int i = 0; i < count; i++) {
							if ("name".equals(parser.getAttributeName(i))) {
								channel.setChannelName(parser
										.getAttributeValue(i));
							} else if ("thumbnail".equals(parser
									.getAttributeName(i))) {
								channel.setChannelThumbnail(parser
										.getAttributeValue(i));
								channel.setChannelPicture(parser.getAttributeValue(i));
							}
						}
					}
					if (channel != null) {
						if ("item".equals(parser.getName())) {
							item = new Item();						
						}else if (item != null) {
							if ("title".equals(parser.getName())) {
								item.setItemTitle(parser.nextText());
							} else if ("link".equals(parser.getName())) {
								item.setItemLink(parser.nextText());
							} else if ("thumbnail".equals(parser.getName())) {
								String bmpurl = parser.nextText();
								item.setItemThumbnail(bmpurl);
								item.setItemPicture(bmpurl);
							}
						}
					}
					break;

				case XmlPullParser.END_TAG:

					if ("channel".equals(parser.getName())) {
						channels.add(channel);
						channel = null;
					} else if ("item".equals(parser.getName())) {
						if (channel != null) {
							channel.addChannelItem(item);
						}
						item = null;
					}else {				
					}

					break;

				default:
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return channels;
	}
}
