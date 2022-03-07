package com.slack.web.ui.enums;

public enum SearchKey {

    HAS_STAR("has:star"),
    IS_SAVED("is:saved");

    private String key;
    private SearchKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
