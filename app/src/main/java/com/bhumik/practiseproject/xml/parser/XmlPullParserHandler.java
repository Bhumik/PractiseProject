package com.bhumik.practiseproject.xml.parser;

import android.util.Xml;

import com.bhumik.practiseproject.xml.bean.ItemData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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


    /**
     * Xml to write data to a file .
     *
     * @param out To the output stream data to be written in the Xml file // equivalent OutputStream os = new FileOutputStream ( "a.xml");
     */
    public void writePersons(List<ItemData> itemDataList, OutputStream out) throws Exception {

        //1.Get XmlSerializer (Xml serialization tool ) ( obtained by Android Tools category Xml)
        XmlSerializer serializer = Xml.newSerializer();
        //2.Set the output stream ( clear To write data to the xml file )
        serializer.setOutput(out, "UTF-8");
        //3.The writing starting document
        serializer.startDocument("UTF-8", true);
        //4.Start tag
        serializer.startTag(null, "bookstore");
        //5.Loop through
        for (ItemData p : itemDataList) {
            //6.Start tag
            serializer.startTag(null, "book");
            //7.The label attribute to set
            serializer.attribute(null, "id", p.getItemNumber());
            //8.Sub-tab
            serializer.startTag(null, "name");
            //9.Setting the content sub-tab
            serializer.text(p.getDescription());
            //10.Sub-tab end
            serializer.endTag(null, "name");
            //11.End tag
            serializer.endTag(null, "person");
        }
        //12.Root tag end

        serializer.endTag(null, "persons");
        //13.End of document
        serializer.endDocument();


    }

}
