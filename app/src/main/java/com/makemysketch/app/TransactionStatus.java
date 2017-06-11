package com.makemysketch.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.payUMoney.sdk.PayUmoneySdkInitilizer;

import static com.payUMoney.sdk.PayUmoneySdkInitilizer.RESULT_FAILED;

public class TransactionStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);

        TextView textView = (TextView) findViewById(R.id.order_status_text);
        ImageView order_img = (ImageView) findViewById(R.id.order_status_icon);
        int resultCode = Integer.MIN_VALUE;
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            resultCode = (int) bd.get("resultCode");
        }
        order_img.setImageResource(R.drawable.order_failure);

        if (resultCode == RESULT_OK) {
            order_img.setImageResource(R.drawable.order_success);
            textView.setText("Order Placed !");
        } else if (resultCode == RESULT_CANCELED) {
            textView.setText("Transaction Cancelled");
        } else if (resultCode == RESULT_FAILED) {
            textView.setText("Trasaction Failed");
        } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
            textView.setText("User returned without login");
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
