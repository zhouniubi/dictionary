package com.example.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    /*
    private final static String DATABASE_NAME = "wordsdb";//DataBase Name
    private final static int DATABASE_VERSION = 1;//版本号
    //建表SQL
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE " + Words.Word.TABLE_NAME + "(" +
            Words.Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Words.Word.COLUMN_NAME_WORD
            + " TEXT" + "," + Words.Word.COLUMN_NAME_MEANING + "TEXT" + ")";
    //删表SQL
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;

    public MyDatabaseHelper(Context context, String woedsdb, Object o, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //当数据库升级时被调用，首先删除旧表，然后调用OnCreate()创建新表
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }*/
    public static final String CREATE_BOOK = "create table book("
            +"id integer primary key autoincrement ,"
            +"word text ,"
            +"meaning text,"
            +"example text)";
    private Context mContext;
    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
        mContext = context;
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"创建成功",Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
