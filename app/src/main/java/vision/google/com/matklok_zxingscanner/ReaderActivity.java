package vision.google.com.matklok_zxingscanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReaderActivity extends AppCompatActivity {

    private Button scan_btn;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
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








        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        IntentResult result= IntentIntegrator.parseActivityResult( requestCode, resultCode, intent);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this,"Du avbröt scannern", Toast.LENGTH_LONG).show();
            }
            else {
               // String scanContent = result.getContents();
                //String scanFormat = result.getFormatName();
                //formtTxt.setText("FORMAT: " + scanFormat);
                //contentTxt.setText("CONTENT: " + scanContent);
            }
        }
         else {

            super.onActivityResult(requestCode, resultCode, intent);
        }
     }

}
