package routines;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TalendDate {
    
    /**
     *  getDate : return the current datetime with the given display format
     *  format : (optional) string representing the wished format of the
     *  date. This string contains fixed strings and variables related
     *  to the date. By default, the format string is DD/MM/CCYY. Here
     *  is the list of date variables:
     *  
     *  {Category} Date
     *  {talendTypes} String
     *  
     *  {param} string("CCYY-MM-DD hh:mm:ss") pattern : date pattern
     *  
     *  + CC for century
     *  + YY for year
     *  + MM for month
     *  + DD for day
     *  + hh for hour
     *  + mm for minute
     *  + ss for second 
     */
     public static String getDate(String pattern) {
         StringBuffer result = new StringBuffer(); 
      
         pattern = pattern.replace("CC", "yy");
         pattern = pattern.replace("YY", "yy");
         pattern = pattern.replace("MM", "MM");
         pattern = pattern.replace("DD", "dd");
         pattern = pattern.replace("hh", "HH");

         // not needed
         // pattern.replace("mm", "mm");
         // pattern.replace("ss", "ss");
         
         SimpleDateFormat sdf = new SimpleDateFormat(pattern);
         sdf.format(Calendar.getInstance().getTime(), result, new FieldPosition(0));
         return result.toString();
     }
    
    /**
     *  getDate : return the current date
     *
     *  {Category} Date
     *  {talendTypes} Date
     *
     *  {example} getCurrentDate()
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
     
    /**
     *  return an ISO formatted random date
     *
     *  {Category} Date
     *  {talendTypes} Date
     *
     *  {param} string("2007-01-01") min : minimum date
     *  {param} string("2008-12-31") max : maximum date (superior to min)
     *
     *  {example} getRandomDate("1981-01-18", "2005-07-24")
     *  {example} getRandomDate("1980-12-08", "2007-02-26")
     */
     public static Date getRandomDate(String minDate, String maxDate) {
         int minYear = Integer.parseInt(minDate.substring(0,4));
         int minMonth = Integer.parseInt(minDate.substring(5,7));
         int minDay = Integer.parseInt(minDate.substring(8,10));
         
         int maxYear = Integer.parseInt(maxDate.substring(0,4));
         int maxMonth = Integer.parseInt(maxDate.substring(5,7));
         int maxDay = Integer.parseInt(maxDate.substring(8,10));
         
         int yy = minYear + (int) ( (maxYear - minYear + 1) * Math.random());
         int mm = minMonth + (int) ( (maxMonth - minMonth + 1) * Math.random());
         int dd = minDay + (int) ( (maxDay - minDay + 1) * Math.random());
    
         Calendar cal = Calendar.getInstance();
         cal.set(Calendar.YEAR, yy);
         cal.set(Calendar.MONTH, mm-1);
         cal.set(Calendar.DAY_OF_MONTH, dd);
         return cal.getTime();
     }
}
