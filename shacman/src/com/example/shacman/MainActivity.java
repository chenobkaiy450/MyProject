package com.example.shacman;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText edit_spn;
	private EditText edit_fmi;
	private Button button_search;
	private TextView textview_context;
	public SQLiteDatabase db;
	public DBManager dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        
        

        db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

//		db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
//		db.execSQL("create table if not exists usertb (_id integer primary key autoincrement, spn interger not null , fmi integer not null , kind text not null,context text not null )");


		init();
		
		//dbHelper.closeDatabase();
	}

	private void init() {
		// TODO Auto-generated method stub
		edit_spn = (EditText) findViewById(R.id.edit_spn);
		edit_fmi = (EditText) findViewById(R.id.edit_fmi);
		button_search = (Button) findViewById(R.id.button_search);
		textview_context = (TextView) findViewById(R.id.textView_context);
		
		button_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String spn = edit_spn.getText().toString();
				String fmi = edit_fmi.getText().toString();
				String contentlist = "";
				String result_spn = "";
				String result_fmi = "";
				String result_kind = "";
				String result_context = "";
				Cursor c = db.rawQuery("select * from usertb where spn=? and fmi=?",new String[]{spn,fmi});
			 	if (c!=null) {
			 		while (c.moveToNext()) {
						//Log.i("info", "_id:"+c.getInt(c.getColumnIndex("_id")));
			 		result_spn = c.getString(c.getColumnIndex("spn"));
			 		result_fmi = c.getString(c.getColumnIndex("fmi"));
			 		result_kind = c.getString(c.getColumnIndex("kind"));
			 		result_context = c.getString(c.getColumnIndex("context"));
			 		contentlist = contentlist +"\n" +"spn:"+result_spn + "\n"+"fmi:"+result_fmi+ "\n" +"种类:"+result_kind+ "\n" +"故障描述:"+result_context+"\n";
			 		}
			 	if(c.getCount()==0){
			 		contentlist = "您查询的数据未录入,请与技术办取得联系.";
			 	}	
			 		
			 		textview_context.setMovementMethod(new ScrollingMovementMethod());
			 		textview_context.setText(contentlist);
			 		
						
			 		c.close();
			 	}
			 	
			}
			
		});

	}
	
}
