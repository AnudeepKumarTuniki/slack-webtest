package com.slack.web.ui.utils;

import org.apache.commons.lang3.StringUtils;

public class StringFunctions {

    public static boolean isNullOrEmptyOrBlank(String str){

        if(StringUtils.isBlank(str) ||
                StringUtils.isEmpty(str) ||
                str == null){
            return true;
        }
        return false;
    }
}
