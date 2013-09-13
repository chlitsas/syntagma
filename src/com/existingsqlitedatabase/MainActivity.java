package com.existingsqlitedatabase;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
public class MainActivity extends Activity {
    //private ArrayList<String> list;
    
    private ArrayList<HashMap<String,String>> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        list = new ArrayList<HashMap<String,String>>();
        
        LoadParts(getCurrentFocus());

        final ListView listview = (ListView) findViewById(R.id.listview);
        
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item,
            		new String[] {"part","description"}, new int[] {R.id.firstLine, R.id.secondLine});
        listview.setAdapter(adapter);
                
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onItemClick(SimpleAdapter parent, View view,
            int position, long id) {
    	/*
    	Intent i = new Intent();
        i.setClass(ListOfHotels.this, SampleActivity.class);
        // parameters
        i.putExtra("position", String.valueOf(position + 1));

        // start the sample activity
        startActivity(i);*/
    }
    
    public void LoadParts(View v) {
    	TestAdapter mDbHelper = new TestAdapter(this);
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	Cursor testdata = mDbHelper.getTestData(); 

    	//String name = Utility.GetColumnValue(testdata, "Name");
    	//String email = Utility.GetColumnValue(testdata, "Email");
    	do{
	    	String part = Utility.GetColumnValue(testdata, "partName");
	    	String title = Utility.GetColumnValue(testdata, "partTitle");
	    	String cnt = Utility.GetColumnValue(testdata, "cnt");
	    	
	    	HashMap<String,String> temp = new HashMap<String,String>();
	    	temp.put("part",part);
	    	temp.put("description", title+" ("+cnt+")");
	    	list.add(temp);
	    	
	    	//list.add(part + "\n"+ title +" ("+ cnt+")");
    	}while (testdata.moveToNext());
    	mDbHelper.close();
    }
    
}
