package com.example.guesswhoiam;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.View.OnClickListener;
import android.view.GestureDetector;

public class HomePageActivity extends Activity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
	
	private EditText signinGestureText;
	private EditText signinCoordinate = null;
	private Button btnSignin;
	private Button btnRegister;
	private GestureDetectorCompat gDetector;
	public static SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		db = SQLiteDatabase.openOrCreateDatabase(HomePageActivity.this.getFilesDir().toString()+ "/test.dbs", null);
		
		this.gDetector = new GestureDetectorCompat(this, this);
		gDetector.setOnDoubleTapListener(this);
		
		signinGestureText = (EditText) findViewById(R.id.signinGesture);
		signinCoordinate = (EditText) findViewById(R.id.signinCoordinate);
		btnSignin = (Button) findViewById(R.id.signin);
		btnRegister = (Button) findViewById(R.id.register);
		
		//redirect to register activity
		btnRegister.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HomePageActivity.this, Register.class);
				startActivity(intent);
			}
		});
		
		btnSignin.setOnClickListener(new LoginListener());
	}

	class LoginListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String gesture = signinGestureText.getText().toString();
			if(gesture.equals("")){
				new AlertDialog.Builder(HomePageActivity.this)
				.setTitle("Error").setMessage("Invalid input!")
				.setPositiveButton("OK", null).show();
			}
			else isUserInfo(gesture);
		}
	}	
	//check if the gesture is already in the database.
	public Boolean isUserInfo(String gesture){
		try{
			String str="select * from tb_user where gesture=?";
			Cursor cursor = db.rawQuery(str, new String []{ gesture });
		
			if(cursor.getCount()<=0){
				new AlertDialog.Builder(HomePageActivity.this).setTitle("Error")
				.setMessage("Unknown user, please register!").setPositiveButton("OK", null)
				.show();
				return false;
			}
			else{
				//String str1 ="select username from tb_user where gesture=?";
				new AlertDialog.Builder(HomePageActivity.this).setTitle("Welcome")
				.setMessage(" Welcome back!").setPositiveButton("OK", null)
				.show();
				return true;
			}
		}catch(SQLiteException e){
			createDb();
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//click the button Register, then call the Register activity 
    public void gotoRegister(View view) {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, Register.class);
        startActivity(intent);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	db.close();
    }
    
    //create database and user table
    public void createDb() {
    	db.execSQL("create table tb_user( username varchar(20), gesture varchar(20), coor varchar(20))");
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.gDetector.onTouchEvent(event);
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent event) {
		signinGestureText.setText("onDown");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);;
		return true;
	}
	
	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		signinGestureText.setText("onFling");
		String str1 = String.valueOf(event1.getX());
		String str2 = String.valueOf(event1.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent event) {
		signinGestureText.setText("onLongPress");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
	}
	
	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
		signinGestureText.setText("onScroll");
		String str1 = String.valueOf(event1.getX());
		String str2 = String.valueOf(event1.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		signinGestureText.setText("onSingleTapConfirmed");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent event) {
		signinGestureText.setText("onDoubleTap");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		signinGestureText.setText("onDoubleTapEvent");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public void onShowPress(MotionEvent event) {
		signinGestureText.setText("onShowPress");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		signinGestureText.setText("onSingleTapUp");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		signinCoordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
}
