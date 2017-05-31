package com.sketch.makemysketch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.sketch.makemysketch.R.id.fab;

public class ItemDisplay extends AppCompatActivity {

    private static final String MAP_KEY = "map";
    HashMap map = null;
    Integer price = 0;
    Integer newPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        ImageView imageView = (ImageView) findViewById(R.id.header_image);
        TextView textView = (TextView) findViewById(R.id.header_text);
        TextView itemDetailsView = (TextView) findViewById(R.id.item_details);
        TextView itemPriceView = (TextView) findViewById(R.id.item_price);
        TextView itemDescriptionView = (TextView) findViewById(R.id.item_description);
        ImageButton orderButton = (ImageButton) findViewById(fab);
        final Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            map = (HashMap) bd.get(MAP_KEY);
            Picasso.with(imageView.getContext()).load((String)map.get("imageLocation")).into(imageView);

            String name = map.get("name").toString();
            String description = map.get("description").toString();
            String size = map.get("size").toString();
            String quality = map.get("quality").toString();
            price = (Integer) map.get("price");
            newPrice = price + (Integer) map.get("framingPrice");
            String colors = map.get("colors").toString();
            String type = map.get("type").toString();
            Integer quantity = (Integer) map.get("quantity");

            if (quantity < 1)
                orderButton.setVisibility(View.INVISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append("Size : ").append(size).append("\n");
            sb.append("Quality : ").append(quality).append("\n");
            sb.append("Colors : ").append(colors).append("\n");
            sb.append("Type : ").append(type).append("\n");

            textView.setText(name);
            itemPriceView.setText("Price : INR "+ price.toString());
            itemDetailsView.setText(sb.toString());
            itemDescriptionView.setText(description);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateFraming()) {
                        Intent intent1 = new Intent(ItemDisplay.this, Checkout.class);
                        RadioButton rBtn = (RadioButton) findViewById(R.id.framed);
                        if (rBtn.isChecked())
                            map.put("price", newPrice);
                        else
                            map.put("price", price);
                        intent1.putExtra(MAP_KEY, map);
                        startActivity(intent1);
                    }
                }
            });

        }
    }
    public void onFrameButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        TextView itemPriceView = (TextView) findViewById(R.id.item_price);
        switch(view.getId()) {
            case R.id.unframed:
                if (checked) {
                    itemPriceView.setText("Price : INR " + price.toString());
                    break;
                }
            case R.id.framed:
                if (checked) {
                    newPrice = price + ((Integer) map.get("framingPrice"));
                    itemPriceView.setText("Price : INR " + newPrice.toString());
                    break;
                }
        }
    }

    private boolean validateFraming() {
        RadioButton rBtn = (RadioButton) findViewById(R.id.unframed);
        RadioButton rBtn2 = (RadioButton) findViewById(R.id.framed);
        if (!rBtn.isChecked() && !rBtn2.isChecked()) {
            Toast.makeText(ItemDisplay.this, "Please Select Framing Option", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
