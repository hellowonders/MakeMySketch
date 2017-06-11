package com.makemysketch.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void onClickSubmitQuery(View view) {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("name", ((EditText) findViewById(R.id.contact_name)).getText().toString());
        map.put("phone", ((EditText) findViewById(R.id.contact_phone)).getText().toString());
        map.put("orderID", ((EditText) findViewById(R.id.contact_orderid)).getText().toString());
        map.put("query", ((EditText) findViewById(R.id.contact_query)).getText().toString());

        new AsyncTask<String, String, String>() {
            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(Contact.this);
                progressDialog.setMessage("Sending...");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {
                String url_submit_query = "https://makemysketch-prateekmehta.rhcloud.com/rest/submitQuery";
                RestTemplate restTemplate = new RestTemplate();
                MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();
                httpHeaders.add("Content-Type", "application/json");

                try {
                    Gson gson = new Gson();
                    HttpEntity<String> httpEntity = new HttpEntity<String>(gson.toJson(map), httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(new URI(url_submit_query), HttpMethod.POST, httpEntity, String.class);
                    return response.getBody();
                } catch (URISyntaxException ex) {}
                return "";
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);
                progressDialog.dismiss();
                Toast.makeText(Contact.this, str, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
