package com.example.zhangchf.mytestapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangchf on 12/18/15.
 */

public class GsonObject extends BaseObject {
    int a;
    String b;
    Map<Object, Object> objMap = new HashMap<>();
    InnerObject obj = new InnerObject();

    public class InnerObject {
        int inA;
        String inB;

        public void init() {
            inA = 20;
            inB = "inner string";
        }
    }

    public void init() {
        super.init();

        a = 10;
        b = "outer string";

        obj = new InnerObject();
        obj.init();

        objMap.put("test", "aaa");
        objMap.put("key2", obj);
    }

}
