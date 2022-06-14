package com.HannibalHsiang;

import java.util.ArrayList;

public class Year {
    int year;
    ArrayList YearBookList = new ArrayList();
    long wordCount = 0;
    long rewardPointCount = 0;

    public Year(int currentBookYear) {
        this.year = currentBookYear;
    }

    public void addBookToYear(Book book) {
        this.YearBookList.add(book);
        this.wordCount += book.wordCount;
        this.rewardPointCount += book.rewardPoint;
    }

    public int getYear(){
        return this.year;
    }

}
