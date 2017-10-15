/*
 * @author: Illya Balakin
 * Created on 10/10/2017
 * CS4322
 * HW 2
 */

package com.illyabalakin.hw2;

public class SearchItem {
    protected String searchQuery, tag;

    public SearchItem( String tag, String searchQuery) {
        this.tag = tag;
        this.searchQuery = searchQuery;
    }


    public String getSearchQuery() {
        return searchQuery;
    }


    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }


    public String getTag() {
        return tag;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }
}
