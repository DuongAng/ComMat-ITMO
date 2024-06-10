package angg;

import angg.interfaces.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Draw_Graphic {

    private String file_name;
    private Function apf;
    private double[][] function_Table;

    public Draw_Graphic(){
    }

    public void init(String file_name, double[][] function_Table, Function apf){
        this.file_name = file_name;
        this.function_Table = function_Table;
        this.apf = apf;
    }

    public void show(){
        XYSeries series1 = new XYSeries("Upgradable function");
        for(int i=0;i< function_Table.length;i++){
            series1.add(function_Table[i][0], function_Table[i][1]);
        }

        XYSeries series2 = new XYSeries(file_name);
        for(double i = function_Table[0][0]; i < function_Table[function_Table.length-1][0]+2; i+=0.1){
            series2.add(i, apf.calculate(i));
        }

        XYSeriesCollection xy_Dataset = new XYSeriesCollection();
        xy_Dataset.addSeries(series1);
        xy_Dataset.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart(file_name, "x", "y",
                        xy_Dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible (0, false);
        renderer.setSeriesLinesVisible (1, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Draw_Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }

}
