package com.HannibalHsiang;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MixedChart extends ApplicationFrame {

    ArrayList<Year> yearArrayList;
    String title;

    @Override
    public void windowClosing(WindowEvent event) {
        if (event.getWindow() == this) {
            this.dispose();
        }
    }

    public MixedChart(ArrayList<Year> yearArrayList, String title) {
        super(title);
        this.title = title;
        this.yearArrayList = yearArrayList;
        setContentPane(new ChartPanel(createChart()));
        this.setPreferredSize(new Dimension(800, 600));
    }

    private JFreeChart createChart() {
        // 初始化一个基础渲染规则为3D模式的柱状统计图效果的Chart图表。
        JFreeChart chart = ChartFactory.createStackedBarChart(this.title, "年份", "Value", null, PlotOrientation.VERTICAL, true, true, false);
        {

            // 获取绘图区对象
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            // 设置轴1--数据匹配
            NumberAxis axis0 = new NumberAxis("写作本数");
            plot.setRangeAxis(0, axis0);
            plot.setDataset(0, createBarDataset());
            plot.mapDatasetToRangeAxis(0, 0);

            // 重新生成一个图表渲染的对象（折线图渲染对象）。
            LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
            {

                // 显示折点数据。
                lineandshaperenderer.setBaseItemLabelsVisible(true);
                lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

                {
                    // 设置拐点是否可见/是否显示拐点
                    lineandshaperenderer.setBaseShapesVisible(true);

                    // 设置线条是否被显示填充颜色
                    lineandshaperenderer.setUseFillPaint(true);

                    {
                        // 设置第一条折线的拐点颜色
                        lineandshaperenderer.setSeriesFillPaint(0, new Color(0,255,127));
                        // 设置第二条折线的拐点颜色
                        lineandshaperenderer.setSeriesFillPaint(1, new Color(0,191,255));
                    }
                    {
                        //设置折线颜色(第一条折线数据线)
                        lineandshaperenderer.setSeriesPaint(0, new Color(	127,255,170));
                        //设置折线颜色(第二条折折线据线)
                        lineandshaperenderer.setSeriesPaint(1, new Color(135,206,235));
                    }
                    {
                        // 设置第一条折线的广度（粗细度）
                        lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3.8F));

                        // 设置第二条折线的广度（粗细度）
                        lineandshaperenderer.setSeriesStroke(1, new BasicStroke(3.8F));
                    }
                    {
                        //设置拐点数值颜色，默认黑色
                        lineandshaperenderer.setBaseItemLabelsVisible(true); // 默认就是true，这里可以不用刻意声明。
                        lineandshaperenderer.setBaseItemLabelPaint(Color.BLUE);
                    }
                    {
                        // 解决最高柱体或折点提示内容被遮盖的问题。
                        lineandshaperenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
                        lineandshaperenderer.setItemLabelAnchorOffset(2); // 设置柱形图上的文字偏离值
                    }
                }
            }

            // 重构第二个数据对象的渲染方式，由现在默认的Bar（柱状统计图）重构为刚刚初始化的Line（折线统计图）的渲染模式
            plot.setRenderer(1, lineandshaperenderer);
            // 设置轴2--数据匹配
            NumberAxis axis1 = new NumberAxis("字数/积分变化");
            plot.setRangeAxis(1, axis1);
            plot.setDataset(1, createLineDataset());
            plot.mapDatasetToRangeAxis(1, 1);
            plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

            /** ---------------------- 中文乱码问题处理 Start ------------------------------- */
            CategoryAxis domainAxis = plot.getDomainAxis();     //水平底部列表
            domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));     //水平底部标题
            domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); //垂直标题

            ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
            rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
            chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
            chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
            /** ---------------------- 中文乱码问题处理 End ------------------------------- */

            {

                rangeAxis.setAutoRange(true);
            }
        }

        // 设置图表控件的背景颜色。
        chart.setBackgroundPaint(Color.WHITE);
        return chart;
    }
    private DefaultCategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String legend = "书籍本数";
        for (Year yearTemp : this.yearArrayList){
            String year = String.valueOf(yearTemp.year) + "年";
            dataset.setValue(yearTemp.YearBookList.size(), legend, year);
        }

        return dataset;
    }

    private DefaultCategoryDataset createLineDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String legend1 = "总字数";
        String legend2 = "总积分数/1,000";

        for (Year yearTemp : this.yearArrayList){
            String year = String.valueOf(yearTemp.year) + "年";
            dataset.setValue(yearTemp.wordCount, legend1, year);
            dataset.setValue(yearTemp.rewardPointCount/1000, legend2, year);
        }

        return dataset;
    }
}