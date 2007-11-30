// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thash.io;

import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple line chart using data from an {@link XYDataset}.
 */
public class PersistentDiskBenchsChart extends ApplicationFrame {

    private String chartTitle;

    private String xAxisLabel;

    private String yAxisLabel;

    private String filePath;

    private int imageWidth;

    private int imageHeight;

    /**
     * Creates a new demo.
     * 
     * @param title the frame title.
     */
    public PersistentDiskBenchsChart(String title) {

        super(title);

        createDemoPanel();

    }

    /**
     * Creates a new demo.
     * 
     * @param title the frame title.
     */
    public PersistentDiskBenchsChart(String title, DefaultXYDataset dataset, String chartTitle, String xAxisLabel,
            String yAxisLabel, String filePath, int imageWidth, int imageHeight) {

        super(title);

        this.chartTitle = chartTitle;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.filePath = filePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        createDemoPanel(dataset);

    }

    private JFreeChart createChart(XYDataset dataset) {
        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle != null ? chartTitle
                : "Persistent Hash Benchs for 1 file 2 Go, 100 pointers by file", // chart title
                xAxisLabel != null ? xAxisLabel : "Max random value for cursor position", // x axis label
                yAxisLabel != null ? yAxisLabel : "Items/s", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
                );

        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setLegendLine(new Rectangle2D.Double(0.0, 0.0, 8.0, 6.0));
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public void createDemoPanel() {
        DefaultXYDataset dataset = new DefaultXYDataset();

        // double[] a1 = new double[] {
        //
        //
        // };
        //
        // // double[] x1 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        // // double[] y1 = new double[] {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        // double[] x1 = new double[a1.length / 2];
        // double[] y1 = new double[a1.length / 2];
        //
        // for (int i = 0; i < a1.length / 2; i += 1) {
        // x1[i] = a1[i * 2];
        // y1[i] = a1[i * 2 + 1];
        // }
        //
        // double[][] data1 = new double[][] { x1, y1 };
        // dataset.addSeries("Test", data1);

        double[] x2 = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0 };
        double[] y2 = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0 };
        double[][] data2 = new double[][] { x2, y2 };
        dataset.addSeries("60M", data2);

        JFreeChart chart = createChart(dataset);
        JPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public void createDemoPanel(DefaultXYDataset dataset) {
        JFreeChart chart = createChart(dataset);
        if (filePath != null) {
            try {
                org.jfree.chart.ChartUtilities.saveChartAsJPEG(new java.io.File(filePath), chart, imageWidth,
                        imageHeight);
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        JPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);

    }

    /**
     * Starting point for the demonstration application.
     * 
     * @param args ignored.
     */
    public static void main(String[] args) {
        PersistentDiskBenchsChart demo = new PersistentDiskBenchsChart("Persistent Hash Benchs");

        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
