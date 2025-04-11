package sh.util;

/**
 * 
 */
public class Validator {

    public static boolean isEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) return true;
        }
        return false;
    }
    
}//class