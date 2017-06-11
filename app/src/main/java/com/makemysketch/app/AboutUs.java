package com.makemysketch.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        String content = "<h3>About Us</h3><br/>"
                + "MakeMySketch mobile app gives user flexibility to order handmade painting or a sketch by uploading picture within few clicks.<br/><br/>"
                + "We have exclusive tie up with Pallavi Artistry for delivering high precision sketch and paintings.<br/><br/>"
                + "If you have any suggestions/complaints, please send your query by clicking contact us menu button.<br/><br/>";
        String content2 = "<br/><b>Best Wishes,<br/>"
                + "Prateek Mehta<br/>"
                + "<i>(Founder, MakeMySketch)</i></b>"
                + "<br/><small><a href=\"https://www.facebook.com/prateek.haridwar\">https://www.facebook.com/prateek.haridwar</a></small>";
        TextView textView = (TextView) findViewById(R.id.about_us);
        textView.setText(Html.fromHtml(content));
        TextView textView2 = (TextView) findViewById(R.id.about_us2);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setText(Html.fromHtml(content2));
    }
}
