package ma.ensias.appels_tels;

import androidx.appcompat.app.AppCompatActivity;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HighLowDataEntry;
import com.anychart.charts.Stock;
import com.anychart.core.stock.Plot;
import com.anychart.core.stock.series.Hilo;
import com.anychart.data.Table;
import com.anychart.data.TableMapping;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Bundle;

import ma.ensias.appels_tels.basedonnes.DataSource;

public class StatsDayActivity extends AppCompatActivity {
DataSource dbgest;
    ArrayList<String> allDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        dbgest = new DataSource(getApplicationContext());
        allDates= dbgest.getAllDates();
        Stock stock = AnyChart.stock();

        Plot plot = stock.plot(0);

        plot.yGrid(true)
                .yMinorGrid(true);

        Table table = Table.instantiate("x");
        try {
            table.addData(getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TableMapping mapping = table.mapAs("{'high': 'high', 'low': 'low'}");

        Hilo hilo = plot.hilo(mapping);

        hilo.name("statistiques des durées par jour");
hilo.stroke("#00cc99");
        hilo.tooltip().format(" {%High}<br/>seconde");
        stock.tooltip().useHtml(true);

        anyChartView.setChart(stock);
    }

    public Long getTimeStamps(String date) throws ParseException {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date d = (Date) formatter.parse(date);
    return d.getTime();
}
    private List<DataEntry> getData() throws ParseException {
        List<DataEntry> data = new ArrayList<>();

        for (String date : allDates){
            data.add(new HighLowDataEntry(getTimeStamps(date),dbgest.getDurationyDay(date),0));
        }




        return data;
    }

}