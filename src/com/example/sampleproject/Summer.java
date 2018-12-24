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

import com.example.sampleproject.PersonalInterestHandicraft.BackgroundTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;



public class Summer extends Activity {

    private static final String[] TEXTS = { "Fotua", "Cap", "Sunglass","Umbrella","Sandal","Hawai Shirt"};
    private static final int[] IMAGES = {R.drawable.fotua, R.drawable.cap,R.drawable.sg, R.drawable.um,R.drawable.san,R.drawable.hawai};
    private static final String[] TEXT = {"1000 tk","200 tk","300 tk","250 tk","500 tk","850 tk" };
    private static final int[] PRICE = {1000,200,300,250,500,850};
    private int mPosition = 0;
    private TextSwitcher mTextSwitcher;
    private ImageSwitcher mImageSwitcher;
    private TextSwitcher mTextSwitcher1;
    int temp=0;
    EditText et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summer);
        et=(EditText) findViewById(R.id.editTextS);
        //String str = et.getText().toString();
        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcherS);
        mTextSwitcher1=(TextSwitcher) findViewById(R.id.textSwitcher1S);
        mTextSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(Summer.this);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });

        mTextSwitcher1.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(Summer.this);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        mTextSwitcher1.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher1.setOutAnimation(this, android.R.anim.fade_out);

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcherS);
        mImageSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(Summer.this);
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
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(Summer.this);
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
    	        	String name = "Partho";
    	        	String contact = "01625213711";
    	        	BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
					backgroundTask.execute(name,contact,product,quantity,price+"",currentDateandTime);
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
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(Summer.this);
    	builder1.setMessage("Want to Buy More?");
    	builder1.setCancelable(true);

    	builder1.setPositiveButton(
    	    "Yes",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	Intent intent = new Intent(getApplicationContext(), Order.class);
    	        	startActivity(intent);
    	        }
    	    });

    	builder1.setNegativeButton(
    	    "No",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	AlertDialog alertDialog = new AlertDialog.Builder(Summer.this).create();
    	        	alertDialog.setTitle("Thank You!");
    	        	alertDialog.setMessage("You will be Notified Soon!");
    	        	alertDialog.show();
    	        	if(temp>0) {
        	        	Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        	        	startActivity(intent);}
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
    		
    			String name = arg0[0];
    			String contact = arg0[1];
    			String productName = arg0[2];
    			String productQuantity = arg0[3];
    			String productPrice = arg0[4];
    			String time = arg0[5];
    			
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
    				String data = URLEncoder.encode("id", "UTF-8") + "="+ URLEncoder.encode(name,"UTF-8") + "&" + 
    						URLEncoder.encode("contact", "UTF-8") + "="+ URLEncoder.encode(contact,"UTF-8") + "&" + 
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
