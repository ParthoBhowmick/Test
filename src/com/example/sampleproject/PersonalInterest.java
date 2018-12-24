package com.example.sampleproject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;



public class PersonalInterest extends Activity {

    private static final String[] TEXTS = { "Rajshahi Mango", "Dinajpur Lichi", "Comilla Rosomalai","Bogra Doi","Dry fish Chittagong","Kachagolla Natore","Hilsha Chadpur","Bakhorkhani Dhaka" };
    private static final int[] IMAGES = { R.drawable.mf1, R.drawable.mf2,R.drawable.mf3,R.drawable.mf4,R.drawable.mf6,R.drawable.mf7, R.drawable.mf8,R.drawable.mf9};
    private static final String[] TEXT = {"60 TK per kg","4 TK per pc","600 tk per kg","300 tk per kg","3000 tk per kg","400 tk per kg","1000 tk per pc","10 tk per pc" };
    private static final int[] PRICE = {60,4,600,300,3000,400,1000,10};
    int temp=0;
    
    private int mPosition = 0;
    private TextSwitcher mTextSwitcher;
    private ImageSwitcher mImageSwitcher;
    private TextSwitcher mTextSwitcher1;
    EditText et;
    
    String value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_interest);
        Intent intent = getIntent();
        value = intent.getStringExtra("username");
        et=(EditText) findViewById(R.id.editText1);
        //String str = et.getText().toString();
        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mTextSwitcher1=(TextSwitcher) findViewById(R.id.textSwitcher1);
        mTextSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(PersonalInterest.this);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });

        mTextSwitcher1.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(PersonalInterest.this);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        mTextSwitcher1.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher1.setOutAnimation(this, android.R.anim.fade_out);

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(PersonalInterest.this);
                return imageView;
            }
        });
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);

        nxt(null);
    }

    public void nxt(View view) {
        mTextSwitcher1.setText(TEXT[mPosition]);
        mTextSwitcher.setText(TEXTS[mPosition]);
        mImageSwitcher.setBackgroundResource(IMAGES[mPosition]);
        
        mPosition = (mPosition + 1) % TEXTS.length;
    }

    
    public void buy(View v) {
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonalInterest.this);
    	builder1.setMessage("Sure Want to Buy?");
    	builder1.setCancelable(true);

    	builder1.setPositiveButton(
    	    "Yes",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        	String currentDateandTime = sdf.format(new Date());
    	        	String quantity = et.getText().toString();
    	        	String product = TEXTS[mPosition-1];
    	        	int quant = Integer.parseInt(quantity);
    	        	int amount = PRICE[mPosition-1];
    	        	int price = amount*quant;
    	        	BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
					backgroundTask.execute(value,product,quantity,price+"",currentDateandTime);
    	        }
    	    });

    	builder1.setNegativeButton(
    	    "No",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	            dialog.cancel();
    	        }
    	    });

    	AlertDialog alert11 = builder1.create();
    	alert11.show();
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonalInterest.this);
    	builder1.setMessage("Want to Buy More?");
    	builder1.setCancelable(true);
    	
    	if(temp>0) {
        	Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        	startActivity(intent);}
    	
    	builder1.setPositiveButton(
    	    "Yes",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	Intent intent = new Intent(getApplicationContext(), Order.class);
    	        	intent.putExtra("username", value);
    	        	startActivity(intent);
    	        }
    	    });

    	builder1.setNegativeButton(
    	    "No",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	AlertDialog alertDialog = new AlertDialog.Builder(PersonalInterest.this).create();
    	        	alertDialog.setTitle("Thank You!");
    	        	alertDialog.setMessage("You will be Notified Soon!");
    	        	alertDialog.show();
    	        	temp++;
    	        }
    	    });

    	AlertDialog alert11 = builder1.create();
    	alert11.show();
    }
    
    public class BackgroundTask extends AsyncTask<String, Void, Void> {
    	
    	Context ctx;
    	public BackgroundTask(Context context) {
    		this.ctx = context;
    	}
    	@Override
    	protected Void doInBackground(String... arg0) {
    		// TODO Auto-generated method stub
    		String insert_post_url = "http://accsectiondemo.site11.com/insertRecord.php";
    		
    			String username = arg0[0];
    			String productName = arg0[1];
    			String productQuantity = arg0[2];
    			String productPrice = arg0[3];
    			String time = arg0[4];
    			
    			try {
    				URL url = new URL(insert_post_url);
    				//connect to URL
    				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    				//sending data using POST method
    				httpURLConnection.setRequestMethod("POST");
    				//for sending some data to URL 
    				httpURLConnection.setDoOutput(true);
    				//to write information in buffered writer an output stream object need to be initialized
    				OutputStream OS = httpURLConnection.getOutputStream();
    				//to write information to URL
    				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
    				String data = URLEncoder.encode("id", "UTF-8") + "="+ URLEncoder.encode(username,"UTF-8")  + "&" + 
    						URLEncoder.encode("product", "UTF-8") + "="+ URLEncoder.encode(productName,"UTF-8") + "&" +
    						URLEncoder.encode("price", "UTF-8") + "="+ URLEncoder.encode(productPrice,"UTF-8") + "&" +
    						URLEncoder.encode("quantity", "UTF-8") + "="+ URLEncoder.encode(productQuantity,"UTF-8")+ "&" +
    	    						URLEncoder.encode("time", "UTF-8") + "="+ URLEncoder.encode(time,"UTF-8");
    				 bufferedWriter.write(data);
    				 bufferedWriter.flush();
    				 bufferedWriter.close();
    				 OS.close();
    				 //get response from server
    				 InputStream is = httpURLConnection.getInputStream();
    				 is.close();
    				 
    			} catch (MalformedURLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			return null;
    		}
    }
}