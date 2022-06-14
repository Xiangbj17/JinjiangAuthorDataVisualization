package com.HannibalHsiang;

import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ProjectGUI extends JFrame{

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextField pathUrlField;
    private JCheckBox 若传入为文件路径请勾选CheckBox;
    private JButton 确认Button;
    private JLabel promptText;
    private JPanel SecondStepPanel;
    private JButton mixChart;
    private JButton RewardPieButton;
    private JButton WordPieButton;
    private String finalAuthorName;
    private ArrayList finalYearList;

    public ProjectGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        SecondStepPanel.setVisible(false);

        确认Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList bookList = new ArrayList();
                String authorName = " ";
                String pathOrUrl = pathUrlField.getText();
                Boolean ifPath = 若传入为文件路径请勾选CheckBox.isSelected();

                if (pathOrUrl.isEmpty()){
                    promptText.setText("请输入文件路径或网页地址!");
                    return;
                }
                if (ifPath){
                    if (!(pathOrUrl.endsWith(".html"))){
                        promptText.setText("请输入正确的html文件路径!");
                        return;
                    }
                }
                else{
                    if (!(pathOrUrl.contains("www.jjwxc.net/oneauthor.php?authorid="))) {
                        promptText.setText("请输入正确的晋江作者页面地址");
                        return;
                    }
                }

                try {
                    bookList = WebScrape.getBookList(pathOrUrl, ifPath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                try {
                    authorName = WebScrape.getAuthorName(pathOrUrl, ifPath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }


                DataOperation dataOperation = new DataOperation(bookList, authorName);
                ArrayList yearList = dataOperation.generateYearListAndShow(false);
                promptText.setText("解析成功!作者 <" + authorName + "> 共计写作" + bookList.size() + "本");
                SecondStepPanel.setVisible(true);
                finalAuthorName = authorName;
                finalYearList = yearList;
            }
        });

        mixChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MixedChart bar = new MixedChart(finalYearList, "作者" + finalAuthorName + "逐年写作情况");
                bar.pack();
                RefineryUtilities.centerFrameOnScreen(bar);
                bar.setVisible(true);
            }
        });

        RewardPieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PieChart pieChart1 = new PieChart();
                try {
                    pieChart1.pieChart(finalYearList, finalAuthorName, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        WordPieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PieChart pieChart1 = new PieChart();
                try {
                    pieChart1.pieChart(finalYearList, finalAuthorName, true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}



