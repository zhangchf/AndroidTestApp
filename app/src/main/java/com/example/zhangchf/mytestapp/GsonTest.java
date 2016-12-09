package com.example.zhangchf.mytestapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Created by zhangchf on 12/18/15.
 */
public class GsonTest {


    public static void testGson() {

        GsonObject obj = new GsonObject();
        obj.init();


        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(obj);

        Log.i("zcf", s);

        GeneralJsonObject generalJsonObject = gson.fromJson(s, GeneralJsonObject.class);

        String s1 =  "{\"b\":\"outer string\",\"obj\":{\"inA\":20,\"inB\":\"inner string\"},\"baseInt\":100,\"baseStr\":\"base String\"}";
        GsonObject objAfter = gson.fromJson(s1, GsonObject.class);
        BaseObject baseObjAfter = gson.fromJson(s1, BaseObject.class);

    }

    public static class GeneralJsonObject {
        Map<Object, Object> objMap;
    }

}
