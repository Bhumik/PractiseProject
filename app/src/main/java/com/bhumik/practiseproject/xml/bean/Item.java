package com.bhumik.practiseproject.xml.bean;

import java.io.Serializable;

/**
 * Created by bhumik on 16/5/16.
 */
public class Item implements Serializable {

    private String title;
    private String description;
    private String link;

    public Item() {
        setTitle(null);
        setDescription(null);
        setLink(null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item \n");
        stringBuilder.append("- title : " + title + "\n");
        stringBuilder.append("- link : " + link + "\n");
        stringBuilder.append("- description : " + description + "\n");
        return stringBuilder.toString();
    }
    /*
    *     @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ Item ");
        stringBuilder.append("{ title : "+title+" }");
        stringBuilder.append("{ link : "+link+" }");
        stringBuilder.append("{ description : "+description+" }");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
*/
}
