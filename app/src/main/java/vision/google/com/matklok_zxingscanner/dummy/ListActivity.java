package vision.google.com.matklok_zxingscanner.dummy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import vision.google.com.matklok_zxingscanner.DatabaseHelper;
import vision.google.com.matklok_zxingscanner.R;
import vision.google.com.matklok_zxingscanner.ReaderActivity;


public class ListActivity extends AppCompatActivity  {

    float x1, x2, y1, y2;

    DatabaseHelper myDB;

    private Button btnHome;



    String selectedName;
    int selectedID;

    ListView list_result;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Intent intent = new Intent(ListActivity.this,ListActivity.class);
        //startActivity(intent);

        //Ovanstående kod kraschar appen!!!!!!!

        btnHome = (Button) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        ListView listView = (ListView) findViewById(R.id.list_result);
        myDB = new DatabaseHelper(this);

         ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();




        if (data.getCount() == 0) {
            Toast.makeText(ListActivity.this, "The database was empty", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {

                theList.add(data.getString(1));

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);





                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                        String name = adapterView.getItemAtPosition(i).toString();

                        Cursor data = myDB.getItemID(name);

                        int itemID=1;
                        while (data.moveToNext()){
                            itemID=data.getInt(0);
                        }

                        return true;
                    }

                });


                //Intent receivedIntent = getIntent();

                //selectedID = receivedIntent.getIntExtra("ID", -1);

                //selectedName = receivedIntent.getStringExtra("name");

                //list_result = (ListView) findViewById(R.id.list_result);
                //listView.setLongClickable(true);


                    //list_result.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                       // @Override
                       // public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                         //   myDB.deleteItem(selectedID, selectedName);


                          //  Toast.makeText(ListActivity.this, "removed from database", Toast.LENGTH_LONG).show();

                         //   return true;
                      //  }
             //   });

            }


            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            // configurebtnHome();
        }
    }
    //private void configurebtnHome(){
     //   Button btnHome = (Button) findViewById(R.id.btnHome);
      //  btnHome.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View view) {
          //      finish();
         //   }
       // });
   // }
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







