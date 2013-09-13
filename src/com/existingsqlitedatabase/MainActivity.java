package com.existingsqlitedatabase;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
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
        
        listview.setOnItemClickListener(mOnGalleryClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private OnItemClickListener mOnGalleryClick = new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        	Toast toast = Toast.makeText(getBaseContext(), "hello "+position, Toast.LENGTH_SHORT);
        	toast.show();
        }       
    };
    
    public void LoadParts(View v) {
    	TestAdapter mDbHelper = new TestAdapter(this);
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	Cursor testdata = mDbHelper.getTestData(); 

    	//String name = Utility.GetColumnValue(testdata, "Name");
    	//String email = Utility.GetColumnValue(testdata, "Email");
    	do{
	    	String part = mDbHelper.GetColumnValue(testdata, "partName");
	    	String title = mDbHelper.GetColumnValue(testdata, "partTitle");
	    	String cnt = mDbHelper.GetColumnValue(testdata, "cnt");
	    	
	    	HashMap<String,String> temp = new HashMap<String,String>();
	    	temp.put("part",part);
	    	temp.put("description", title+" ("+cnt+")");
	    	list.add(temp);
	    	
	    	//list.add(part + "\n"+ title +" ("+ cnt+")");
    	}while (testdata.moveToNext());
    	mDbHelper.close();
    }
    
}
