package za.co.android.nihapp.Utils;

import java.math.BigDecimal;

public class Utils {

    public static String formatMoney(String input) {
        try {
            BigDecimal temp = new BigDecimal(input);
            String returnValue = temp.toString();
            int pos = returnValue.indexOf(".");
            if ( pos == -1 )
                returnValue = returnValue.concat(".00");
            else if ( pos == temp.toString().length() - 2 )
                returnValue = returnValue.concat("0");
            return "R" + returnValue;
        }
        catch (Exception exception) {
        }
        return "";
    }
}
