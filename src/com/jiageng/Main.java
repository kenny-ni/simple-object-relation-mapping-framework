package com.jiageng;

import com.jiageng.sorm.core.Query;
import com.jiageng.sorm.core.TableContext;


public class Main {

    public static void main(String[] args) {
        try {
            TableContext.generatePo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
