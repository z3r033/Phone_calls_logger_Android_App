package ma.ensias.appels_tels;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.JumpLine;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;


import android.text.format.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


import ma.ensias.appels_tels.basedonnes.DataSource;

public class StatsWeek extends AppCompatActivity {
    DataSource dbGest;
    public String duration(int duration){
        String duree="";

        int seconde=(duration%3600)%60;
        int minute=(duration%3600)/60;
        int houre=duration/3600;

        if(houre!=0)
        {
            duree=houre+" h"+" "+minute+" m"+" "+seconde+" s";
        }
        else if (minute!=0)
        {
            duree=minute+" m"+" "+seconde+" s";
        }else{
            duree=seconde+" s";
        }

        return duree;
    }
    private ArrayList<String> sortDates(ArrayList<String> dates) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Map<Date, String> dateFormatMap = new TreeMap<>();
        for (String date: dates)
            dateFormatMap.put(f.parse(date), date);
        return new ArrayList<>(dateFormatMap.values());
    }
    private String getNomJour(String date){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String joursemaine = null;
        try {
            joursemaine = (String) DateFormat.format("EEEE", f.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return joursemaine;
    }
    private String addJours(String date,int nombrejour){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, nombrejour);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String output = sdf1.format(c.getTime());
        return  output;
    }
    private Boolean compareTwoDate(String date1,String date2)  {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sdformat.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdformat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("The date 1 is: " + sdformat.format(d1));
        System.out.println("The date 2 is: " + sdformat.format(d2));
        if(d1.compareTo(d2) > 0) {
            System.out.println("Date 1 occurs after Date 2");
            return false;
        } else if(d1.compareTo(d2) < 0) {
            System.out.println("Date 1 occurs before Date 2");
            return true;
        } else if(d1.compareTo(d2) == 0) {

            System.out.println("Both dates are equal");
            return true;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        dbGest = new DataSource(getApplicationContext());
        ArrayList<String> f= null;
        try {
            f = sortDates(dbGest.getAllDates());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Cartesian vertical = AnyChart.vertical();
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            vertical.animation(true)
                    .title("statistiques des durées de communications réalisées par semaine");
        String nomjour= null;
        try {
            nomjour = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(ff.parse(f.get(0)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.text.DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};
      String  todaynomjour= new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        int nombrejour=0;
       if(nomjour.equals("Monday")){
           nombrejour=1;
       }else if(nomjour.equals("Tuesday")){
           nombrejour=2;
       }else if(nomjour.equals("Wednesday")){
           nombrejour=3;
       }else if(nomjour.equals("Thursday")){
           nombrejour=4;
       }else if(nomjour.equals("Friday")){
           nombrejour=5;
       }else if(nomjour.equals("Saturday")){
           nombrejour=6;
       }else if(nomjour.equals("Sunday")){
           nombrejour=7;
       }
      //  dbGest.getDurationByWeek()
String todaydate =dateformatter.format(date)  ;
        List<DataEntry> data = new ArrayList<>();
       String initialdate = f.get(0);
       int i=1;
        while(compareTwoDate(initialdate,todaydate)){

            try {
                data.add(new CustomDataEntry("Semaine "+i+" (" + initialdate + "-" + addJours(initialdate, 7 - nombrejour), dbGest.getDurationByWeek(initialdate,addJours(initialdate, 7 - nombrejour)), 0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            initialdate = addJours(initialdate,(7 - nombrejour)+1);
            nombrejour=1;
            i++;
        }



        Set set = Set.instantiate();
        set.data(data);
        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping jumpLineData = set.mapAs("{ x: 'x', value: 'jumpLine' }");

        Bar bar = vertical.bar(barData);
        bar.color("#00cc99");

        bar.labels().format("{%Value} s");

        JumpLine jumpLine = vertical.jumpLine(jumpLineData);
        jumpLine.stroke("2 #60727B");
        jumpLine.labels().enabled(false);

        vertical.yScale().minimum(0d);

        vertical.labels(true);

        vertical.tooltip()
                .displayMode(TooltipDisplayMode.UNION)
                .positionMode(TooltipPositionMode.POINT)
                .unionFormat(
                        "function() {\n" +
                                "      return '  ' +\n" +
                                "        '\\n' + ' ' + this.points[0].value + ' s';\n" +
                                "    }");

        vertical.interactivity().hoverMode(HoverMode.BY_X);

        vertical.xAxis(true);
        vertical.yAxis(true);
        vertical.yAxis(0).labels().format("{%Value} s");

        anyChartView.setChart(vertical);
    }

    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number jumpLine) {
            super(x, value);
            setValue("jumpLine", jumpLine);
        }
    }


}
