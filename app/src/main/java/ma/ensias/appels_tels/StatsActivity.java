package ma.ensias.appels_tels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


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
import com.anychart.palettes.RangeColors;


import java.util.ArrayList;
import java.util.List;

import ma.ensias.appels_tels.basedonnes.DataSource;

public class StatsActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        dbGest = new DataSource(getApplicationContext());
        ArrayList<String> f= dbGest.getAllDates();

        Cartesian vertical = AnyChart.vertical();
        vertical.animation(true)
                .title("statistiques des durées de communications réalisées par mois");

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Jan", dbGest.getDurationByMounth("01"),0));
        data.add(new CustomDataEntry("Feb",  dbGest.getDurationByMounth("02"), 0));
        data.add(new CustomDataEntry("Mar",  dbGest.getDurationByMounth("03"), 0));
        data.add(new CustomDataEntry("Apr",  dbGest.getDurationByMounth("04"), 0));
        data.add(new CustomDataEntry("May",  dbGest.getDurationByMounth("05"), 0));
        data.add(new CustomDataEntry("Jun",  dbGest.getDurationByMounth("06"), 0));
        data.add(new CustomDataEntry("Jul",  dbGest.getDurationByMounth("07"), 0));
        data.add(new CustomDataEntry("Aug",  dbGest.getDurationByMounth("08"), 0));
        data.add(new CustomDataEntry("Sep",  dbGest.getDurationByMounth("09"), 0));
        data.add(new CustomDataEntry("Oct",  dbGest.getDurationByMounth("10"), 0));
        data.add(new CustomDataEntry("Nov",  dbGest.getDurationByMounth("11"), 0));
        data.add(new CustomDataEntry("Dec",  dbGest.getDurationByMounth("12"), 0));

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