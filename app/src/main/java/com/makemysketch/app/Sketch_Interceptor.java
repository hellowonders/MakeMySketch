package com.makemysketch.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Sketch_Interceptor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_interceptor);

        ImageView imageView = (ImageView) findViewById(R.id.sketch_intercept_sample);
        Picasso.with(Sketch_Interceptor.this).load("http://makemysketch.web44.net/extras/sample.jpg").into(imageView);

        new AsyncTask<Object, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Object[] params) {
                Integer orderBuffer = 0;
                try {
                    final String url = "http://makemysketch-prateekmehta.rhcloud.com/rest/getBufferStatus";
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                    orderBuffer = restTemplate.getForObject(url, Integer.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return orderBuffer;
            }

            @Override
            protected void onPostExecute(Integer orderBuffer) {
                try {
                    if (orderBuffer > 0) {
                        Intent intent = new Intent(Sketch_Interceptor.this, SketchActivity.class);
                        startActivity(intent);
                    } else {
                        String str = "<h2><font color=\"#ff3300\">Order Limit Exceeded !</font></h2><br/>"
                                + "<h3>We have received maximum orders that we can process in a day, please try again tomorrow.<br/><br/>"
                                + "Our artists gives dedicated time of 2-4 hours to draw every sketch with perfection.<br/><br/>"
                                + "We do not compromise on quality by taking excessive orders.</h3>";
                        TextView textView = (TextView) findViewById(R.id.sketch_intercept_text);
                        textView.setText(Html.fromHtml(str));

                        ImageView imageView = (ImageView) findViewById(R.id.sketch_intercept_sample);
                        imageView.setVisibility(View.INVISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
