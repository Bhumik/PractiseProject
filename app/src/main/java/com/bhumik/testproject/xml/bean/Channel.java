package com.bhumik.testproject.xml.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bhumik on 16/5/16.
 */
public class Channel implements Serializable {

    private ArrayList<Item> items;
    private String title;
    private String link;
    private String description;
    private String lastBuildDate;
    private String docs;
    private String language;

    public Channel() {
        setItems(null);
        setTitle(null);
        setLink(null);
        setDescription(null);
        setLastBuildDate(null);
        setDocs(null);
        setLanguage(null);
        items = new ArrayList<Item>();
        // set every field to null in the constructor
    }

    public void addItems(Item item) {
        this.items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Channel \n");
        stringBuilder.append("- title : " + title + "\n");
        stringBuilder.append("- link : " + link + "\n");
        stringBuilder.append("- description : " + description + "\n");
        stringBuilder.append("- lastBuildDate : " + lastBuildDate + "\n");
        stringBuilder.append("- docs : " + docs + "\n");
        stringBuilder.append("- language : " + language + "\n");
        stringBuilder.append("- items size() : " + items.size() + "\n");
        stringBuilder.append("\n");

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append("-- item[" + i + "] -> " + items.get(i).toString() + "\n");
        }
        return stringBuilder.toString();
    }

    /*    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ Channel ");
        stringBuilder.append("{ title : "+title+" }");
        stringBuilder.append("{ link : "+link+" }");
        stringBuilder.append("{ description : "+description+" }");
        stringBuilder.append("{ lastBuildDate : "+lastBuildDate+" }");
        stringBuilder.append("{ docs : "+docs+" }");
        stringBuilder.append("{ language : "+language+" }");
        stringBuilder.append("{ items size() : "+items.size()+" }");

        for(int i=0;i<items.size();i++){
            stringBuilder.append("{ item["+i+"] -> "+items.get(i).toString()+" }");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
*/


}