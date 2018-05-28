package vision.google.com.matklok_zxingscanner.dummy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;

import vision.google.com.matklok_zxingscanner.DatabaseHelper;
import vision.google.com.matklok_zxingscanner.R;
import vision.google.com.matklok_zxingscanner.ReaderActivity;


public class ListActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    DatabaseHelper myDB;

    private Button btnHome;

    long ROW_ID;


    private static final String TAG = "ListActivity";


    String selectedName;
    int selectedID;

    private ListView list_result;

    private ArrayList<String> theList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myDB = new DatabaseHelper(this);

        populateListView();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


        //Intent receivedIntent = getIntent();

        //selectedID = receivedIntent.getIntExtra("_id",-1);

        //selectedName = receivedIntent.getStringExtra("name");



        btnHome = (Button) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });



    }

    private void populateListView() {
        //Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        final Cursor data = myDB.getData();


        if (data.getCount() == 0) {
            Toast.makeText(ListActivity.this, "Inga varor i listan", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList
                theList.add(data.getString(1));
            }


            final ListView listView = (ListView) findViewById(R.id.list_result);


            //final ArrayList<String> theList = new ArrayList<>();

            final ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);

            listView.setAdapter(Adapter);

            //Cursor data = myDB.getData();


            //if (data.getCount() == 0) {
            //   Toast.makeText(ListActivity.this, "Inga varor i listan", Toast.LENGTH_LONG).show();
            //} else {
            //    while (data.moveToNext()) {

            //       theList.add(data.getString(1));

            //       Adapter.notifyDataSetChanged();


            //    }

            //}


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long I) {


                    String ITEM1 = parent.getItemAtPosition(position).toString();
                    Log.d(TAG, "onItemClick: You Clicked on " + ITEM1);

                    Cursor data = myDB.getItemID(ITEM1); //get the id associated with that name
                    int itemID = -1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                    if (itemID > -1) {
                        Log.d(TAG, "onItemClick: The ID is: " + itemID);

                        //myDB.deleteItem(itemID, ITEM1);
                        myDB.deleterowid(itemID);

                        theList.remove(position);

                        Adapter.notifyDataSetChanged();

                        //myDB.deleteItem(ITEM1);
                    } else {
                        Toast.makeText(ListActivity.this, "Hittade inget ID", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

            });




        }

        // });

    }





    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x2< x1){
                    Intent i = new Intent(ListActivity.this, ReaderActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


}







