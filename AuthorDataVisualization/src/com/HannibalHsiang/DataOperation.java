package com.HannibalHsiang;

import java.util.ArrayList;
import java.util.Comparator;

public class DataOperation {

    ArrayList bookList;
    String author;
    boolean findInYearList;

    DataOperation(ArrayList bookList, String author){
        // 初始化
        this.bookList = bookList;
        this.author = author;
    }

    public int countNumBooks(){
        // 一共写了多少书
        return bookList.size();
    }


    public ArrayList generateYearListAndShow(boolean print){
        // 根据年份整理书籍并且展示比较
        ArrayList<Book> bookListTemp = this.bookList;
        ArrayList<Year> yearList = new ArrayList<>();

        for (Book bookTemp : bookListTemp){
            String currentBookTime = bookTemp.establishTime; // 当前书目的时间String
            if (currentBookTime.isEmpty()){
                // 如果还没开始写，就不算在里面
                continue;
            }
            int currentBookYear= Integer.parseInt(currentBookTime.substring(0, 4)); // 当前书目的年份

            // 如果当前YearArray还是空的，直接添加数据，然后continue
            if (yearList.size() == 0){
                Year yearTemp = new Year(currentBookYear);
                yearTemp.addBookToYear(bookTemp);
                yearList.add(yearTemp);
                continue;
            }

            // 如果YearArray非空，需要看当前的书年限是否已存在，若已存在则add，若不存在则创建Year
            this.findInYearList = false; // flag

            for (Year yearTemp : yearList){
                if (yearTemp.year == currentBookYear){
                    // 如果找到这一年，直接把书加进去就好了，然后continue
                    yearTemp.addBookToYear(bookTemp);
                    this.findInYearList = true; // 更改flag
                    continue;
                }
            }

            // 如果上一个循环完毕，还没找到，说明需要添加新的Year
            if (!(this.findInYearList)) {
                Year yearTemp = new Year(currentBookYear);
                yearTemp.addBookToYear(bookTemp);
                yearList.add(yearTemp);
            }
        }

        yearList.sort(Comparator.comparing(Year::getYear)); // 按年份升序排列
        // yearList.sort(Comparator.comparing(Year::getYear).reversed()); // 按年份降序排列

        if (print) {
            for (Year year : yearList) {
                System.out.println("年份: " + year.year);
                System.out.println("出了多少书 " + year.YearBookList.size());
                System.out.println("总字数: " + year.wordCount);
                System.out.println("总积分: " + year.rewardPointCount);
                System.out.println("\n");
            }
        }

        return yearList;
    }

}
