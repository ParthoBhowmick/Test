package com.example.sampleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends Activity {
	Button b1,b2,b3;
	String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        Intent intent = getIntent();
        value = intent.getStringExtra("username");
    }
    public void order(View view){
        Intent intent = new Intent(this,Order.class);
        intent.putExtra("username", value);
        startActivity(intent);
    }

   public void goTosite(View view) {
	   Intent intent = new Intent(this,Site.class);
	   intent.putExtra("username", value);
       startActivity(intent);
   }
    
    public void searchPlaces(View view){
        Intent intent = new Intent(this,SelectDivision.class);
        intent.putExtra("username", value);
        startActivity(intent);
    }
}
