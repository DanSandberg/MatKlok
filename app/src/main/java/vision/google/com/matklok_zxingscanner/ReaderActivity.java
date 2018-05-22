package vision.google.com.matklok_zxingscanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;



public class ReaderActivity extends AppCompatActivity {



    private Button scan_btn;
    private Button btnAdd;
    DatabaseHelper myDB;
    ListView mResult;
    EditText editText;
















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        //addListenerOnCheckOutButton();

        myDB = new DatabaseHelper(this);


        //mResult = findViewById(R.id.result);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        editText = (EditText) findViewById(R.id.editText);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0){
                    AddData(newEntry);

                    editText.setText("");
                }else{
                    Toast.makeText(ReaderActivity.this,"You must input something", Toast.LENGTH_LONG).show();
                }
            }
        });


        scan_btn = findViewById(R.id.scan_btn);
        final Activity activity= this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Skanna vara");
                integrator.setCameraId(1);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }


        });




    }


    //public void setScan_btn(View view){

        //setContentView(R.layout.activity_reader);



    //}

    public void AddData(String newEntry){
        boolean insertData = myDB.addData(newEntry);

        if (insertData==true){
            Toast.makeText(ReaderActivity.this, "Success", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ReaderActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }













        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {





        //IntentResult result= IntentIntegrator.parseActivityResult(  requestCode, resultCode,  data);
            super.onActivityResult(requestCode, resultCode, data);
        //if(result !=null){
            if(requestCode == 0){

            if (resultCode == RESULT_OK){
                String contents = data.getStringExtra("SCAN_RESULT");
               // mResult.setText(contents);
            }
            if(resultCode == RESULT_CANCELED){

            }
        }
            //if(result.getContents()==null){
                //Toast.makeText(this,"Du avbröt scannern", Toast.LENGTH_LONG).show();
           // }
            //else {



                //Intent intent = new Intent(ReaderActivity.this, ListActivity.class);
                //startActivity(intent);
               //result.getContents().toString();
               //Toast.makeText(this,"Varan skannad", Toast.LENGTH_LONG).show();
           // }
       // }
         //else {

           // super.onActivityResult(requestCode, resultCode, intent);
        //}
     }

}
