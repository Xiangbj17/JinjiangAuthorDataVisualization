package com.HannibalHsiang;

public class Book {

    String name;
    String type;
    String style;
    String process;
    long wordCount;
    long rewardPoint;
    String establishTime;

    public void initName(String name){
        this.name = name;
    }

    public void initType(String type){
        this.type = type;
    }

    public void initStyle(String style){
        this.style = style;
    }

    public void initProcess(String process){
        this.process = process;
    }

    public void initWordCount(long wordCount){
        this.wordCount = wordCount;
    }

    public void initRewardPoint(long rewardPoint){
        this.rewardPoint = rewardPoint;
    }

    public void initEstablishTime(String establishTime){
        this.establishTime = establishTime;
    }

    public void printBookInfo(){
        System.out.println("************ " + this.name + " ************");
        System.out.println("总字数:  " + this.wordCount);
        System.out.println("总积分:  " + this.rewardPoint);
        System.out.println("发表时间: " + this.establishTime);
        System.out.println("\n");
    }
}

