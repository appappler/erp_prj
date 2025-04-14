package sh.util;

import java.sql.Date;

/**
 * 
 */
public class Parser {

    public static int toInt(String value) throws NumberFormatException {
        return Integer.parseInt(value.trim());
    }

    public static Date toDate(String value) throws IllegalArgumentException {
        return Date.valueOf(value.trim());
    }
    
}//class