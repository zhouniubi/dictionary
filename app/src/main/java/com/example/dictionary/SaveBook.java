package com.example.dictionary;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SaveBook extends AppCompatActivity {
    EditText editText, editText1, editText2, editText3;
    MyDatabaseHelper mDbHelper = new MyDatabaseHelper(this, "words.db", null, 2);
    ListView listView;
    /*Button buttonOk;*/
    List list, list1, list2, listSer;
    String word, mean, example;
    String textword1, textword2, textword3;
    MainActivity mainActivity;

    //上下文菜单部分
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content2, menu);
    }

    //单词删除
    public void delete(String strWord) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("book", "word = ?", new String[]{strWord});
    }

    //更新操作
    public void update(String strWord, String strMean, String strExample) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("book", "word = '" + strWord + "'", null);
        ContentValues cv = new ContentValues();
        cv.put("word", strWord);
        cv.put("meaning", strMean);
        cv.put("example", strExample);
        db.insert("book", null, cv);
    }

    //刷新界面
    public void refresh() {
        finish();
        Intent intent = new Intent(this, SaveBook.class);
        startActivity(intent);
    }

 /*   public void flush(View view) {
        finish();

    }*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savebook);
        /*buttonOk = findViewById(R.id.buttonSearch2);
        editText = findViewById(R.id.edit_search);*/
        listView = findViewById(R.id.list_view);


/*        registerForContextMenu(listView);*/
        /*mDbHelper = new MyDatabaseHelper(this, "words.db", null, 2);*/
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        list = new ArrayList();
        list1 = new ArrayList();
        list2 = new ArrayList();
        Cursor cursor1 = db.query("book", null, null, null, null, null, null);
        if (cursor1.moveToFirst()) {
            do {
                word = cursor1.getString(cursor1.getColumnIndex("word"));
                list.add(word);
            }
            while (cursor1.moveToNext());
        }
        cursor1.close();
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                example = cursor.getString(cursor.getColumnIndex("example"));

                list1.add(example);

            }
            while (cursor.moveToNext());
        }
        cursor.close();

        final ArrayAdapter adapter = new ArrayAdapter(SaveBook.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List listmean = new ArrayList();
                List listexample = new ArrayList();
                String exampledata = list1.get(position).toString();
                String worddata = list.get(position).toString();
                Log.i("test_", worddata);
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveBook.this);
                LayoutInflater inflater1 = getLayoutInflater();
                View dialog_layout = inflater1.inflate(R.layout.login_dialog, null);
                editText1 = dialog_layout.findViewById(R.id.edit_text_word);
                editText2 = dialog_layout.findViewById(R.id.edit_text_mean);
                editText3 = dialog_layout.findViewById(R.id.edit_text_example);
                builder.setView(dialog_layout).setTitle("Word");
                editText1.setText(worddata);
                editText3.setText(exampledata);
                Cursor cursor2 = db.query("book", new String[]{"meaning"}, "word=?", new String[]{worddata}, null, null, null);
                if (cursor2.moveToFirst()) {
                    do {
                        mean = cursor2.getString(cursor2.getColumnIndex("meaning"));
                        listmean.add(mean);
                    }
                    while (cursor2.moveToNext());

                }
                cursor2.close();
                editText2.setText(listmean.toString().replace("[", "").replace("]", ""));
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editword, editmean, editexample;
                        editword = editText1.getText().toString();
                        editmean = editText2.getText().toString();
                        editexample = editText3.getText().toString();
                        update(editword, editmean, editexample);
                        refresh();
                    }
                });


                builder.setNegativeButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* mainActivity.delete(editText1.getText().toString());*/
                        String editword;
                        editword = editText1.getText().toString();
                        delete(editword);
                        refresh();
                    }
                });
                builder.show();

            }
        });


/*        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textword1 = editText.getText().toString();
                List listSer = new ArrayList();
                Log.d("textword1",textword1);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor cursorSer = db.query("book", new String[]{"word"}, "word=?", new String[]{textword1}, null, null, null);
                if (cursorSer.moveToFirst()) {
                    do {
                        String searchword = cursorSer.getString(cursorSer.getColumnIndex("word"));
                        listSer.add(searchword);
                    }
                    while (cursorSer.moveToNext());

                }
                cursorSer.close();
                textword2 = listSer.toString();
                Log.d("textword2",textword2);
                if (textword1.equals(textword2)) {
                    final ArrayAdapter adapterSer = new ArrayAdapter(SaveBook.this, android.R.layout.simple_list_item_1, listSer);
                    listView.setAdapter(adapterSer);
                    Toast.makeText(SaveBook.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(SaveBook.this, "查询失败！", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
