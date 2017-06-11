package com.makemysketch.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        String text = "<h3>Terms & Conditions</h3><br/>"
                + "<b><u>Paintings</u></b> <br/><br/>"
                + "1. For cash on delivery orders, there is additional Rs.300 courier fee, consumer has to pay 30% advance in form of online payment, rest 70% shall be paid at time of delivery.<br/><br/>"
                + "2. Consumer can raise a return request from menu options within 3 days of receiving painting in case he is not satisfied with product, painting has to be couriered back to us on address mentioned on the receipt. Refund will be made post quality checks deducting courier and framing charges if applicable. <br/><br/>"
                + "<b><u>Handmade Sketch</u></b> <br/><br/>"
                + "1. Handmade pencil sketch will be delivered based on the image uploaded.<br/><br/>"
                + "2. Full amount has to paid online for ordering sketch. No return request will be entertained until the picture on sketch is largely deviated from the original image uploaded.";

        TextView textView = (TextView) findViewById(R.id.terms_and_conditions);
        textView.setText(Html.fromHtml(text));
    }
}
