package routines;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TalendDate {
    
    /**
     *  getDate : return the current datetime with the given display format
     *  format : (optional) string representing the wished format of the
     *  date. This string contains fixed strings and variables related
     *  to the date. By default, the format string is DD/MM/CCYY. Here
     *  is the list of date variables:
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
      
         pattern.replace("CC", "yy");
         pattern.replace("YY", "yy");
         pattern.replace("MM", "MM");
         pattern.replace("DD", "dd");
         pattern.replace("hh", "HH");

         // not needed
         // pattern.replace("mm", "mm");
         // pattern.replace("ss", "ss");
         
         SimpleDateFormat sdf = new SimpleDateFormat(pattern);
         sdf.format(Calendar.getInstance().getTime(), result, new FieldPosition(0));
         return result.toString();
     }
     
    /**
     *  return an ISO formatted random date
     *
     *  {talendTypes} Day
     *
     *  {param} string('2007-01-01') min : minimum date
     *  {param} string('2008-12-31') max : maximum date (superior to min)
     *
     *  {example} getRandomDate('1981-01-18', '2005-07-24')
     *  {example} getRandomDate('1980-12-08', '2007-02-26')
     */
     public static Date getRandomDate(String minDate, String maxDate) {
         // PTODO MHIRT
         return Calendar.getInstance().getTime();
         
     }
}
