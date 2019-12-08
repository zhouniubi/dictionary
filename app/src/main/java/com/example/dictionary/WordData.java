package com.example.dictionary;

public class WordData {
    private String word;
    private String mean;
    private int inmageId;
    public WordData(String word,String mean,int inmageId){
        this.word = word;
        this.mean = mean;
        this.inmageId = inmageId;
    }

    public int getInmageId() {
        return inmageId;
    }

    public String getMean() {
        return mean;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
