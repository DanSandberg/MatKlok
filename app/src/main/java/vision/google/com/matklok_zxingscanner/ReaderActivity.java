package vision.google.com.matklok_zxingscanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;

import vision.google.com.matklok_zxingscanner.dummy.RecipesActivity;


public class ReaderActivity extends AppCompatActivity {

    float x1, x2, y1, y2;


    private Button scan_btn;

    DatabaseHelper myDB;
    ListView mResult;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);


        myDB = new DatabaseHelper(this);


        mResult = findViewById(R.id.list_result);











        scan_btn = findViewById(R.id.scan_btn);
        final Activity activity = this;
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











    public void AddData(String newEntry){

        boolean insertData = myDB.addData(newEntry);

        if (insertData==true){

            Toast.makeText(ReaderActivity.this, "Varan tillagd", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ReaderActivity.this, "Varan kunde inte läggas till", Toast.LENGTH_LONG).show();
        }

    }




    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        IntentResult result= IntentIntegrator.parseActivityResult(  requestCode, resultCode,  intent);




        if(result !=null ){

            String scancontent= result.getContents();


            AddData(scancontent);



        }else{
            Toast.makeText(ReaderActivity.this, "Kunde inte läggas till", Toast.LENGTH_LONG).show();
        }

        }





     public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                if(x1 < x2){
                    Intent i = new Intent(ReaderActivity.this, vision.google.com.matklok_zxingscanner.dummy.ListActivity.class);
                    startActivity(i);
                }
                if(x2 < x1){
                    Intent i = new Intent(ReaderActivity.this, vision.google.com.matklok_zxingscanner.dummy.RecipesActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
     }

}
