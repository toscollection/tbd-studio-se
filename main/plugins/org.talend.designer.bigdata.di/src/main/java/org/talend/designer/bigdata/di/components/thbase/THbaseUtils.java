package org.talend.designer.bigdata.di.components.thbase;

public class THbaseUtils {

    public static String trimDuplicateQuotes(String inputString) {
        if (inputString.length() < 2) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(inputString);
        if (inputString.charAt(0) == '\"' && inputString.charAt(1) == '\"') {
            sb.deleteCharAt(0);
        }
        if (inputString.charAt(inputString.length() - 1) == '\"' && inputString.charAt(inputString.length() - 2) == '\"') {
            sb.deleteCharAt(inputString.length() - 1);
        }
        return sb.toString();
    }

    public static String trimQuotes(String string) {
        if (string.length() < 2) return string;
        if ((string.charAt(0) == '\"') && (string.charAt(string.length() - 1) == '\"')) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }
    public static String AddQuotesIfNotContainContext(String string){
        if(string.isEmpty()) return "\"\"";
        if(string.contains("context")) return string;
        return "\""+string+"\"";
    }
}
