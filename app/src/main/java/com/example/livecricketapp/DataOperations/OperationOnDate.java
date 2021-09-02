package com.example.livecricketapp.DataOperations;

public class OperationOnDate {

    public static int number_of_days ( String startDate , String endDate )
    {
        String[] start = startDate.split("/");
        String[] end = endDate.split("/");

        int days = Integer.parseInt(end[0]) - Integer.parseInt(start[0]);
        int months = Integer.parseInt(end[1]) - Integer.parseInt(start[1]);
        int year = Integer.parseInt(end[2]) - Integer.parseInt(start[2]);
        return (days + (months*30) + (year * 365));
    }

    public static String date_increase( String date )
    {
        String[] str = date.split("/",3);
        if ( Integer.parseInt(str[0]) != 30 )
        {
            str[0] = String.valueOf(Integer.parseInt(str[0] ) +1);
            return str[0] + "/" +str[1] + "/" +str[2] ;
        }else if ( Integer.parseInt(str[1]) != 12 )
        {
            str[0] = "1";
            str[1] = String.valueOf(Integer.parseInt(str[1] ) +1);
            return str[0] + "/" +str[1] + "/" +str[2] ;
        }else  {
            str[0] = "1";
            str[1] = "1";
            str[2] = String.valueOf(Integer.parseInt(str[2] ) +1);
            return str[0] + "/" +str[1] + "/" +str[2] ;
        }
    }


}
