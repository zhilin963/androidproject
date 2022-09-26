package com.example.guesswhoiam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.GestureDetector;

public class Register extends Activity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
	
	private EditText gestureText;
	private EditText userName;
	private EditText coordinate = null;
	private Button btnSubmit;
	private GestureDetectorCompat gDetector;
	SQLiteDatabase db;
	
	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	db.close();
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		gestureText = (EditText) findViewById(R.id.registerGesture);
		userName = (EditText) findViewById(R.id.username);
		coordinate = (EditText) findViewById(R.id.coordinate);
		this.gDetector = new GestureDetectorCompat(this, this);
		gDetector.setOnDoubleTapListener(this);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = userName.getText().toString();
				String gesture = gestureText.getText().toString();
				String coor = coordinate.getText().toString();
				if(!(username.equals("")||gesture.equals("")||coor.equals(""))){
					if(addUser(username,gesture,coor)){
						DialogInterface.OnClickListener nu = new DialogInterface.OnClickListener(){
							
							@Override
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								//redirect to home page activity
								Intent intent = new Intent();
								intent.setClass(Register.this, HomePageActivity.class);
								startActivity(intent);
								Register.this.onDestroy();
							}
						};
						new AlertDialog.Builder(Register.this)
							.setTitle("Welcome").setMessage(username+ ", You've successfully registered!")
							.setPositiveButton("OK", nu).show();
					}
					else{
						new AlertDialog.Builder(Register.this)
						.setTitle("Error").setMessage("Unknown error, please try again!")
						.setPositiveButton("OK", null).show();
					}
				}
				else{
					new AlertDialog.Builder(Register.this)
					.setTitle("Error").setMessage("Please entry your username and gesture!")
					.setPositiveButton("OK", null).show();
				}
			}
		});		
	}

	//add user
	public Boolean addUser(String username, String gesture,String coor){
		String str = "insert into tb_user values(?,?,?) ";
		HomePageActivity main = new HomePageActivity();
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+ "/test.dbs", null);
		main.db = db;
		try{
			db.execSQL(str, new String[] { username, gesture, coor }); 
			return true;
		} catch(Exception e){
			main.createDb();
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.gDetector.onTouchEvent(event);
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent event) {
		gestureText.setText("onDown");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		gestureText.setText("onFling");
		String str1 = String.valueOf(event1.getX());
		String str2 = String.valueOf(event1.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent event) {
		gestureText.setText("onLongPress");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
	}
	
	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
		gestureText.setText("onScroll");
		String str1 = String.valueOf(event1.getX());
		String str2 = String.valueOf(event1.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		gestureText.setText("onSingleTapConfirmed");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent event) {
		gestureText.setText("onDoubleTap");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		gestureText.setText("onDoubleTapEvent");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public void onShowPress(MotionEvent event) {
		gestureText.setText("onShowPress");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);		
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		gestureText.setText("onSingleTapUp");
		String str1 = String.valueOf(event.getX());
		String str2 = String.valueOf(event.getY());
		coordinate.setText("x="+str1+" y="+str2);
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
}
