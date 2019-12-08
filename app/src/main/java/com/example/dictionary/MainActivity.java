package com.example.dictionary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    String code;
    MyDatabaseHelper mDbHelper = new MyDatabaseHelper(this, "words.db", null, 2);
    private research2 re = new research2();
    private String str;

    //menu部分
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help://显示帮助
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("长按查询即可选择是否保存");
                builder.setTitle("HELP");
                builder.show();
                break;
            case R.id.dancibook://跳转到单词本界面
                Intent intent = new Intent(MainActivity.this, SaveBook.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //查询部分
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ok = findViewById(R.id.buttonSearch);
        registerForContextMenu(ok);//为查询按钮注册
        Button clear = findViewById(R.id.buttonClear);
        editText = findViewById(R.id.search_edit);
        textView = findViewById(R.id.showText);
        ok.setOnClickListener(new View.OnClickListener() {
            public static final int UPDATE_TEXT = 1;
            public void onClick(View v) {
                code = editText.getText().toString();
                @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == UPDATE_TEXT) {
                            textView.setText(str);
                        }
                    }
                };
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            str = re.get(code);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                });
                thread.start();
            }
        });
        //清除部分
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                editText.setText("");
            }
        });


    }

    //上下文菜单部分（）
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SaveWord:
                //单词保存操作
                insert(code, str);
                /*delete(code);*/
                break;
        }
        return super.onContextItemSelected(item);
    }

    //数据库插入
    public void insert(String strWord, String strMeaning) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", strWord);
        values.put("meaning", strMeaning);
        values.put("example","待输入");
        db.insert("book", null, values);
        values.clear();
        Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
    }

    //单词更新（未完成）
    public void update(String strMeaning) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("meaning", "test");
    }

    //单词删除
    public void delete(String strWord) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("book", "word = ?", new String[]{strWord});
    }

    public void ResearchWord() {

    }

}

