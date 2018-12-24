package com.example.sampleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectDivision extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_division);
    }

    public void rangpur(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void rajshahi(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void barisal(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void chittagong(View view) {
        Intent intent = new Intent(this,ViewDiv.class);
        intent.putExtra("Div", "Chittagong");
        startActivity(intent);
    }

    public void khulna(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void sylhet(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void mymensingh(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }

    public void dhaka(View view) {
    	Intent intent = new Intent(this,ViewDiv.class);
        startActivity(intent);
    }
}
