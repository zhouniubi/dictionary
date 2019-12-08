package com.example.dictionary;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class research2 {


    public String get(String code) throws IOException, JSONException {
        String s = this
                .zxc("https://fanyi.youdao.com/openapi.do?keyfrom=youdanfanyi123&key=1357452033&type=data&doctype=json&version=1.1&q="
                        + code);
        /*	System.out.println(s);*/
        JSONObject json = JSONObject.parseObject(s);
        //特定名词处理部分
        JSONObject basic = json.getJSONObject("basic");
        JSONArray explain = basic.getJSONArray("explains");
        //网络释义部分
        JSONArray web = json.getJSONArray("web");
        JSONObject Target = web.getJSONObject(0);
        JSONObject Target2 = web.getJSONObject(1);
        JSONObject Target3 = web.getJSONObject(2);
        JSONArray value= Target.getJSONArray("value");
        JSONArray value2 = Target2.getJSONArray("value");
        JSONArray value3 = Target3.getJSONArray("value");
        final String Value = this.change(value.toString());
        final String Value2 = this.change(value2.toString());
        final String Value3 = this.change(value3.toString());
        final String Value4 =this.change(explain.toString());
        String answer = "翻译1是："+Value+"\n"+"翻译2是："+Value2+"\n"+"翻译3是："+Value3+"\n"+"翻译4是："+Value4;
        return answer;

    }

    public  String change(String input){
        String inputfix,inputfixx;
        inputfix =  input.replace('[', ' ');
        inputfix = inputfix.replace(']',' ');
        inputfix = inputfix.replace('"',' ');
        inputfix = inputfix.replace("美俚","(美俚):");
        return inputfix;
    }
    public  String zxc(String uri) throws IOException {
        URL url = new URL(uri);
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        is.close();
        return sb.toString();
    }
}
