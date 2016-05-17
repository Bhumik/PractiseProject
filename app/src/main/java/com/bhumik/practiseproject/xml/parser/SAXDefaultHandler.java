package com.bhumik.practiseproject.xml.parser;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

import com.bhumik.practiseproject.xml.bean.Channel;
import com.bhumik.practiseproject.xml.bean.Item;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by bhumik on 16/5/16.
 */
public class SAXDefaultHandler extends DefaultHandler {

    private Channel channel;
    private ArrayList<Item> items;
    private Item item;
    private boolean inItem = false;
    private StringBuilder content;

    public SAXDefaultHandler() {
        items = new ArrayList<Item>();
        content = new StringBuilder();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content = new StringBuilder();
        if (localName.equalsIgnoreCase("channel")) {
            channel = new Channel();
        } else if (localName.equalsIgnoreCase("item")) {
            inItem = true;
            item = new Item();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("title")) {
            if (inItem) {
                item.setTitle(content.toString());
            } else {
                channel.setTitle(content.toString());
            }
        } else if (localName.equalsIgnoreCase("link")) {
            if (inItem) {
                item.setLink(content.toString());
            } else {
                channel.setLink(content.toString());
            }
        } else if (localName.equalsIgnoreCase("description")) {
            if (inItem) {
                item.setDescription(content.toString());
            } else {
                channel.setDescription(content.toString());
            }
        } else if (localName.equalsIgnoreCase("lastBuildDate")) {
            channel.setLastBuildDate(content.toString());
        } else if (localName.equalsIgnoreCase("docs")) {
            channel.setDocs(content.toString());
        } else if (localName.equalsIgnoreCase("language")) {
            channel.setLanguage(content.toString());
        } else if (localName.equalsIgnoreCase("item")) {
            inItem = false;
            items.add(item);
        } else if (localName.equalsIgnoreCase("channel")) {
            channel.setItems(items);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content.append(ch, start, length);
    }

    public void endDocument() throws SAXException {
        // you can do something here for example send
        // the Channel object somewhere or whatever.
    }

    public Channel getChannel() {
        return channel;
    }


    //Another rmethod for parse
    public Channel androidSaxParse(InputStream is) {
        RootElement root = new RootElement("rss");
        Element chanElement = root.getChild("channel");
        Element chanTitle = chanElement.getChild("title");
        Element chanLink = chanElement.getChild("link");
        Element chanDescription = chanElement.getChild("description");
        Element chanLastBuildDate = chanElement.getChild("lastBuildDate");
        Element chanDocs = chanElement.getChild("docs");
        Element chanLanguage = chanElement.getChild("language");

        Element chanItem = chanElement.getChild("item");
        Element itemTitle = chanItem.getChild("title");
        Element itemDescription = chanItem.getChild("description");
        Element itemLink = chanItem.getChild("link");

        chanElement.setStartElementListener(new StartElementListener() {
            public void start(Attributes attributes) {
                channel = new Channel();
            }
        });
        chanElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                if (items != null)
                    channel.setItems(items);
            }
        });

        // Listen for the end of a text element and set the text as our
        // channel's title.
        chanTitle.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setTitle(body);
            }
        });
        chanLink.setEndTextElementListener(new EndTextElementListener() {
            public void end(String link) {
                channel.setLink(link);
            }
        });
        chanDescription.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setDescription(body);
            }
        });
        chanLastBuildDate.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setLastBuildDate(body);
            }
        });
        chanDocs.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setDocs(body);
            }
        });
        chanLanguage.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setLanguage(body);
            }
        });

        // Same thing happens for the other elements of channel ex.

        // On every <item> tag occurrence we create a new Item object.
        chanItem.setStartElementListener(new StartElementListener() {
            public void start(Attributes attributes) {
                item = new Item();
            }
        });

        // On every </item> tag occurrence we add the current Item object
        // to the Items container.
        chanItem.setEndElementListener(new EndElementListener() {
            public void end() {
                items.add(item);
            }
        });

        itemTitle.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setTitle(body);
            }
        });
        itemDescription.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setDescription(body);
            }
        });
        itemLink.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setLink(body);
            }
        });

        // and so on

        // here we actually parse the InputStream and return the resulting
        // Channel object.
        try {
            Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());
            return channel;
        } catch (SAXException e) {
            // handle the exception
        } catch (IOException e) {
            // handle the exception
        }

        return null;
    }
}
