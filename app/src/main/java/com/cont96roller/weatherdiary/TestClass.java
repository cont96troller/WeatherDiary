package com.cont96roller.weatherdiary;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cont96roller.weatherdiary.interfaces.AbstractTest;
import com.cont96roller.weatherdiary.interfaces.TestInterface;


public class TestClass extends AbstractTest implements TestInterface , View.OnClickListener  {

    public static void dong() {
        Log.d(MainActivity.TAG, "dong()");

    }

    public void myoung() {
        Log.d(MainActivity.TAG, "myoung()");
    }


    public void toast(Context context) {

        Toast.makeText(context, "명길 추워", Toast.LENGTH_SHORT).show();
    }

    public String kang() {

        return "myoung kill";
    }

    @Override
    public void testMkMk() {
        testAbstractMkMk();
    }

    @Override
    public void onClick(View view) {
        
    }
}
