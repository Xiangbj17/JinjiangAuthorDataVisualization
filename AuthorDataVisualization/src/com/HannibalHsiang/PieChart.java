package com.HannibalHsiang;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.ArrayList;

public class PieChart {

    public static void pieChart(ArrayList<Year> YearList, String authorName, boolean isWordCount)throws Exception{
        String title = new String();
        DefaultPieDataset dataset = new DefaultPieDataset();
        // isWordCount == true : 逐年字数分布饼图
        // isWordCount == false: 逐年积分分布饼图
        if (isWordCount){
            title = "作者" + authorName + "逐年字数分布饼图";
            for (Year yearTemp : YearList){
                String category = String.valueOf(yearTemp.year) + "年";
                dataset.setValue(category, yearTemp.wordCount);
            }
        }
        else{
            title = "作者" + authorName + "逐年积分分布饼图";
            for (Year yearTemp : YearList){
                String category = String.valueOf(yearTemp.year) + "年";
                dataset.setValue(category, yearTemp.rewardPointCount);
            }
        }
        //创建chart
        JFreeChart chart= ChartFactory.createPieChart(
                title, //标题
                dataset,
                true,//显示legend
                true,//生成tooltips
                false//不成生成URLs
        );
        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }

}