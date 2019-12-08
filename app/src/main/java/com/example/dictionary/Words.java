package com.example.dictionary;

import android.provider.BaseColumns;

public class Words {
    public Words() {
    }

    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";//表名
        public static final String COLUMN_NAME_WORD = "word";//列：单词
        public static final String COLUMN_NAME_MEANING = "meaning";//列：单词含义
    }

}
