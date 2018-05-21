package vision.google.com.matklok_zxingscanner.dummy;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vision.google.com.matklok_zxingscanner.DatabaseHelper;
import vision.google.com.matklok_zxingscanner.R;

public class ListActivity extends AppCompatActivity {




        DatabaseHelper myDB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);

            ListView listView = (ListView) findViewById(R.id.listView);
            myDB = new DatabaseHelper(this);

            //populate an ArrayList<String> from the database and then view it
            ArrayList<String> theList = new ArrayList<>();
            Cursor data = myDB.getListContents();
            if(data.getCount() == 0){
                Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
            }else{
                while(data.moveToNext()){
                    theList.add(data.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                    listView.setAdapter(listAdapter);
                }
            }


        }
    }
