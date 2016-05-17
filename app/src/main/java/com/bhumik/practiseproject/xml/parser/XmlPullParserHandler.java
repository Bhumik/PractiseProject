package com.bhumik.practiseproject.xml.parser;

import com.bhumik.practiseproject.xml.bean.ItemData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by bhumik on 16/5/16.
 */
public class XmlPullParserHandler {

    private ArrayList<ItemData> itemDatas = new ArrayList<ItemData>();
    private ItemData itemData;
    private String text;

    public ArrayList<ItemData> getItemDatas() {
        return itemDatas;
    }

    public ArrayList<ItemData> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("ItemData")) {
                            // create a new instance of employee
                            itemData = new ItemData();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("ItemData")) {
                            // add itemData object to list
                            itemDatas.add(itemData);
                        } else if (tagname.equalsIgnoreCase("ItemNumber")) {
                            itemData.setItemNumber(text);
                        } else if (tagname.equalsIgnoreCase("Description")) {
                            itemData.setDescription(text);
                        } else if (tagname.equalsIgnoreCase("Price")) {
                            itemData.setPrice(text);
                        } else if (tagname.equalsIgnoreCase("Weight")) {
                            itemData.setWeight(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemDatas;
    }

}
