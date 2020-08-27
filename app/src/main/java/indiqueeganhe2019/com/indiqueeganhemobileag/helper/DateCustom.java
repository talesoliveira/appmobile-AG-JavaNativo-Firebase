package indiqueeganhe2019.com.indiqueeganhemobileag.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){
      Long data =  System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        String dataString = simpleDateFormat.format(data);


        return dataString;
    }



}
