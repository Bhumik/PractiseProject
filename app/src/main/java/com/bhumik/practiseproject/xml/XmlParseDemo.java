package com.bhumik.practiseproject.xml;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.xml.bean.Channel;
import com.bhumik.practiseproject.xml.bean.ItemData;
import com.bhumik.practiseproject.xml.parser.SAXDefaultHandler;
import com.bhumik.practiseproject.xml.parser.XMLDOMParser;
import com.bhumik.practiseproject.xml.parser.XmlPullParserHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParseDemo extends AppCompatActivity {


    private static final String TAG = "XmlParseDemo";
    Button btnxmlAndroidXml, btnxmlOrgSaxXml, btnxmlPullparser, btnxmlDOMparser;
    TextView txtxmldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlparse);

        btnxmlAndroidXml = (Button) findViewById(R.id.btnxmlSaxAndroidXml);
        btnxmlOrgSaxXml = (Button) findViewById(R.id.btnxmlSaxOrgSaxXml);
        btnxmlPullparser = (Button) findViewById(R.id.btnxmlPullparser);
        btnxmlDOMparser = (Button) findViewById(R.id.btnxmlDOMparser);
        txtxmldata = (TextView) findViewById(R.id.txtxmldata);

        btnxmlAndroidXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidSAXParse();
            }
        });
        btnxmlOrgSaxXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgXmlSAXParse();
            }
        });
        btnxmlPullparser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlPullparser();
            }
        });
        btnxmlDOMparser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlDOMparser();
            }
        });
    }

    public void AndroidSAXParse() {
        //    android.sax
        try {
            InputStream is = getAssets().open("xmldemo2.xml");
            SAXDefaultHandler saxDefaultHandler = new SAXDefaultHandler();
            Channel channel = saxDefaultHandler.androidSaxParse(is);
            if (channel != null) {
                Log.d(TAG, "SAXParse - channel --> \n" + channel.toString());
                txtxmldata.setText("SAXParse (android.sax) Parse -> \n\n" + channel.toString());
            } else {
                txtxmldata.setText("SAXParse (android.sax) Parse -> \n\n Error - channel element is null");

            }
            is.close();
        } catch (Exception e) {
            txtxmldata.setText("SAXParse (android.sax) Parse -> \n\n Exception \n " + e.getMessage());
            Log.d(TAG, "= SAXParse (android.sax) EXCEPTION : \n" + e.getMessage() + "=");
            e.printStackTrace();
        }
    }

    public void OrgXmlSAXParse() {
        //org.sax.xml
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            InputStream is = getAssets().open("xmldemo2.xml");
            SAXDefaultHandler saxDefaultHandler = new SAXDefaultHandler();
            saxParser.parse(is, saxDefaultHandler);

            Channel channel = saxDefaultHandler.getChannel();

            if (channel != null) {
                Log.d(TAG, "SAXParse - channel --> \n" + channel.toString());
                txtxmldata.setText("SAXParse (org.sax.xml) Parse -> \n\n" + channel.toString());
            } else {
                txtxmldata.setText("SAXParse (org.sax.xml) Parse -> \n\n Error - channel element is null");

            }
            is.close();
        } catch (Exception e) {
            txtxmldata.setText("SAXParse (org.sax.xml) Parse -> \n\n Exception \n " + e.getMessage());
            Log.d(TAG, "= OrgXmlSAXParse EXCEPTION : \n" + e.getMessage() + "=");
            e.printStackTrace();
        }
    }

    public void XmlPullparser() {

        try {
            XmlPullParserHandler parser = new XmlPullParserHandler();
            InputStream is = getAssets().open("xmldemo.xml");
            ArrayList<ItemData> itemDatas = parser.parse(is);

            if (itemDatas != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Items\n");
                for (ItemData itemData : itemDatas) {
                    stringBuilder.append("ItemData\n");
                    stringBuilder.append("- ItemNumber : " + itemData.getItemNumber() + "\n");
                    stringBuilder.append("- Description : " + itemData.getDescription() + "\n");
                    stringBuilder.append("- Price : " + itemData.getPrice() + "\n");
                    stringBuilder.append("- Weight : " + itemData.getWeight() + "\n");
                }
                txtxmldata.setText("XmlPullparser Parse -> \n\n " + stringBuilder.toString());
            } else {
                txtxmldata.setText("XmlPullparser Parse -> \n\n Error - itemDatas list is null");
            }

            is.close();
        } catch (Exception e) {
            Log.d(TAG, "= XmlPullparser Exception : \n" + e.getMessage() + "=");
            txtxmldata.setText("XmlPullparser Parse -> \n\n Exception \n " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void XmlDOMparser_1() {

        /*
        <?xml version="1.0" encoding="utf-8"?>
        <employee>
            <name>Name </name>
            <salary>18000.0</salary>
            <designation>Developer</designation>
        </employee>
         */
        final String NODE_EMP = "employee";
        final String NODE_NAME = "name";
        final String NODE_SALARY = "salary";
        final String NODE_DESIGNATION = "designation";
        XMLDOMParser parser = new XMLDOMParser();
        try {
            InputStream is = getAssets().open("xmldemo3.xml");
            Document doc = parser.getDocument(is);
            NodeList nodeList = doc.getElementsByTagName(NODE_EMP);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                stringBuilder.append("employee\n");
                stringBuilder.append("- name : " + parser.getValue(e, NODE_NAME) + "\n");
                stringBuilder.append("- salary : " + parser.getValue(e, NODE_SALARY) + "\n");
                stringBuilder.append("- designation : " + parser.getValue(e, NODE_DESIGNATION) + "\n");
            }
            txtxmldata.setText("XmlDOMparser Parse -> \n\n " + stringBuilder.toString());
        } catch (Exception e) {
            txtxmldata.setText("XmlDOMparser Parse -> \n\n Exception \n " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void XmlDOMparser() {

        final String NODE_PARENT = "ItemData";
        final String NODE_ITEMNUMBER = "ItemNumber";
        final String NODE_DESC = "Description";
        final String NODE_PRICE = "Price";
        final String NODE_WEIGHT = "Weight";

        XMLDOMParser parser = new XMLDOMParser();

        try {
            InputStream is = getAssets().open("xmldemo.xml");

            Document doc = parser.getDocument(is);

            // Get elements by name ItemData
            NodeList nodeList = doc.getElementsByTagName(NODE_PARENT);

            /*
             * for each <ItemData> element get text of ItemNumber, Description,Price and Weight
             */
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                stringBuilder.append("ItemData\n");
                stringBuilder.append("- ItemNumber : " + parser.getValue(e, NODE_ITEMNUMBER) + "\n");
                stringBuilder.append("- Description : " + parser.getValue(e, NODE_DESC) + "\n");
                stringBuilder.append("- Price : " + parser.getValue(e, NODE_PRICE) + "\n");
                stringBuilder.append("- Weight : " + parser.getValue(e, NODE_WEIGHT) + "\n");
            }
            txtxmldata.setText("XmlDOMparser Parse -> \n\n " + stringBuilder.toString());
        } catch (Exception e) {
            txtxmldata.setText("XmlDOMparser Parse -> \n\n Exception \n " + e.getMessage());
            e.printStackTrace();
        }
    }

}
