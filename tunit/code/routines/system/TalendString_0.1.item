package routines;

import java.util.Random;

public class TalendString {

    /**
     * return Replace the special character(e.g. <,>,& etc) within a string for XML file.
     
     * {Category} String
     * {talendTypes} String
     * 
     * {param} string("") input: The string with the special character(s) need to be replaced.
     * 
     * {example} replaceSpecialCharForXML("<title>Empire <>Burlesque</title>") # <title>Empire &lt;&gt;Burlesque</title>
     */
    public static String replaceSpecialCharForXML(String input) {
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("'", "&apos;");
        input = input.replaceAll("\"", "&quot;");
        return input;
    }
    
    /**
     * getAsciiRandomString : Return a randomly generated String
     * 
     * {Category} String
     * {talendTypes} String
     * 
     * {param} int(6) length: length of the String tio return
     * 
     * {example} getAsciiRandomString(6) # Art34Z
     */
    public static String getAsciiRandomString(int length) {
        Random random = new Random();
        int cnt=0;
        StringBuffer buffer = new StringBuffer();
        char ch;
        int end = 'z' + 1;
        int start = ' ';
        while (cnt<length) {
            ch = (char) (random.nextInt(end-start) + start);
            if (Character.isLetterOrDigit(ch)) {
                buffer.append(ch);
                cnt++;
            }
        }
        return buffer.toString();
    }
}
