package bitonic;

import bitonic.helpers.HelperFunctions;
import bitonic.sorters.BitonicSorterForArbitraryN;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerfTest extends Application {

    private final static int STARTSIZE = 1000;

    private List<Measurement> measurements;
    private double maxTime = 0;
    private double minTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        measurements = new ArrayList<Measurement>();

        /*int count = STARTSIZE;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 3; j++) {
                this.sort(count);
            }
            count += 1000;
        }
        System.out.println("Done");*/

        Gson gson = new Gson();

        /*String json = gson.toJson(this.measurements);
        Path file = Paths.get("data.json");
        System.out.println(file);

        PrintWriter writer = new PrintWriter(String.valueOf(file), "UTF-8");
        writer.print(json);
        writer.close();*/

        Path file = Paths.get("data.json");
        String content = new Scanner(file).useDelimiter("\\Z").next();

        Type type = new TypeToken<List<Measurement>>() {}.getType();
        this.measurements = gson.fromJson(content, type);

        for (int i = 0; i < this.measurements.size(); i++) {
            double time = this.measurements.get(i).getTime();
            if(time > this.maxTime) this.maxTime = time;
        }

        //Defining X axis
        NumberAxis xAxis = new NumberAxis(STARTSIZE, (15*1000), 100);
        xAxis.setLabel("Size");

        //Defining y axis
        NumberAxis yAxis = new NumberAxis(0, this.maxTime, 1);
        yAxis.setLabel("Time(Sec)");

        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.setMaxSize(1504, 801);

        XYChart.Series series = new XYChart.Series();

        for (Measurement m : this.measurements) {
            series.getData().addAll(new XYChart.Data(m.getSize(), m.getTime()));
        }

        linechart.getData().add(series);

        Group root = new Group();
        root.getChildren().add(linechart);

        primaryStage.setTitle("Bitonic sort");
        primaryStage.setMaximized(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void sort(int n) {
        int[] array;
        array = new int[n];
        for(int i = 0; i < n; i++) {
            array[i] = i;
        }
        HelperFunctions.shuffleArray(array);

        double start = System.nanoTime();
        BitonicSorterForArbitraryN sorter = new BitonicSorterForArbitraryN(null, array, 0, 0, true);
        Thread t = new Thread(sorter);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double end = System.nanoTime();

        double total = (end - start)/1000000000;

        if(total > this.maxTime) {
            this.maxTime = total;
        } else if (total < minTime) {
            this.minTime = total;
        }

        Measurement m = new Measurement(total, n);
        this.measurements.add(m);
    }
}
