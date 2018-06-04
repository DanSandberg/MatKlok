package vision.google.com.matklok_zxingscanner.dummy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

import vision.google.com.matklok_zxingscanner.DatabaseHelper;
import vision.google.com.matklok_zxingscanner.R;
import vision.google.com.matklok_zxingscanner.ReaderActivity;


public class ListActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    DatabaseHelper myDB;




    private static final String TAG = "ListActivity";


    String selectedName;
    int selectedID;

    private ListView list_result;

    private TextView TextViewDate;

    private ArrayList<String> theList = new ArrayList<>();





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myDB = new DatabaseHelper(this);

        populateListView();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);




        String date_n = new SimpleDateFormat("dd MMM yyyy",
                Locale.getDefault()).format(new Date());

        TextView date = (TextView) findViewById(R.id.textViewDate);
        date.setText(date_n);






    }

    private void populateListView() {

        final Cursor data = myDB.getData();


        if (data.getCount() == 0) {
            Toast.makeText(ListActivity.this, "Inga varor i listan", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {

                theList.add(data.getString(1));
            }


            final ListView listView = (ListView) findViewById(R.id.list_result);




            final ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);

            listView.setAdapter(Adapter);



            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long I) {


                    String ITEM1 = parent.getItemAtPosition(position).toString();
                    Log.d(TAG, "onItemClick: You Clicked on " + ITEM1);

                    Cursor data = myDB.getItemID(ITEM1);
                    int itemID = -1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                    if (itemID > -1) {

                        Log.d(TAG, "onItemClick: The ID is: " + itemID);


                        myDB.deleterowid(itemID);


                        theList.remove(position);

                        Toast.makeText(ListActivity.this, "Varan borttagen", Toast.LENGTH_LONG).show();

                        Adapter.notifyDataSetChanged();



                    } else {
                        Toast.makeText(ListActivity.this, "Hittade inget ID", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

            });





        }




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







