package com.makemysketch.app;

import android.os.AsyncTask;

import com.makemysketch.app.api.ItemInterface;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

public class RetrieveImage extends AsyncTask<URL, Integer, List<Object>> {
    private ItemInterface itemInterface;

    RetrieveImage(ItemInterface itemInterface) {
        this.itemInterface = itemInterface;
    }
    @Override
    protected List<Object> doInBackground(URL... params) {

        try {
            final String url = "http://makemysketch-prateekmehta.rhcloud.com/rest/getAllPaintings";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            List<Object> paintingLst = restTemplate.getForObject(url, List.class);
            return paintingLst;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> paintingDetailList) {
        itemInterface.paintingDetails(paintingDetailList);
    }
}
