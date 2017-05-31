package com.sketch.makemysketch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sketch.makemysketch.model.OrderDetail;
import com.sketch.makemysketch.utils.OrderDetailsUpload;

import org.springframework.util.StringUtils;

import java.util.HashMap;

public class Checkout extends AppCompatActivity {

    private static final String MAP_KEY = "map";
    private HashMap map = null;
    private OrderDetail orderDetail = null;
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolBar = (Toolbar) findViewById(R.id.app_bar);
        toolBar.setTitle("MakeMySketch Checkout");
        toolBar.setTitleTextColor(Color.DKGRAY);
        setSupportActionBar(toolBar);

        orderDetail = new OrderDetail();
        progressDialog = new ProgressDialog(Checkout.this);
        progressDialog.setMessage("Uploading Details");

        final Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            map = (HashMap) bd.get(MAP_KEY);
            if (map.get("type").equals("sketch")) {
                RadioButton radioButton2 = (RadioButton) findViewById(R.id.cod);
                radioButton2.setVisibility(View.INVISIBLE);
            }

            Integer price = ((Double) map.get("price")).intValue();
            TextView amount = (TextView) findViewById(R.id.editTextAmount);
            amount.setText(price.toString());

        }
    }

    public void onClickBtnPayNow(View v) {
        if (validateInput()) {
            orderDetail.setOrderId("" + new java.util.Date().getTime());
            final Context context = v.getContext();
            final Intent intent = new Intent(Checkout.this, InvoicingPay.class);
            intent.putExtra("orderDetail", orderDetail);

            if (map.get("type").equals("sketch")) {
                if (map.get("imageUri") != null) {
                    String[] temp = ((Uri) map.get("imageUri")).getPath().split("/");
                    orderDetail.setOrderType("sketch");
                    orderDetail.setImageName(temp[temp.length - 1]);
                    orderDetail.setImageUri(((Uri) map.get("imageUri")).getPath());

                    progressDialog.show();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            if (OrderDetailsUpload.ftpUpload(orderDetail)) {
                                progressDialog.dismiss();
                            }
                            return null;
                        }

                        @Override
                        public void onPostExecute(Void result) {
                            context.startActivity(intent);
                        }
                    }.execute();
                }
            } else {
                orderDetail.setOrderType("painting");
                orderDetail.setImageName(map.get("name").toString());
                context.startActivity(intent);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        TextView onlinePayAmt = (TextView) findViewById(R.id.onlinePayAmt);
        TextView initialAmt = (TextView) findViewById(R.id.editTextAmount);
        LinearLayout layout = (LinearLayout) findViewById(R.id.deliveryPayment);
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.pay_online:
                if (checked) {
                    assert initialAmt != null;
                    onlinePayAmt.setText("INR " + initialAmt.getText());
                    orderDetail.setDeliveryType("online");
                    orderDetail.setPaidAmt(initialAmt.getText().toString());
                    layout.setVisibility(View.INVISIBLE);
                    break;
                }
            case R.id.cod:
                if (checked) {
                    assert initialAmt != null;
                    Integer cod_online_price = Integer.valueOf(initialAmt.getText().toString());
                    Integer cod_online_updated_price = ((Double) (.20 * cod_online_price)).intValue();
                    Integer deliveryPrice = cod_online_price - cod_online_updated_price + 300;
                    onlinePayAmt.setText("INR " + cod_online_updated_price.toString());
                    TextView deliveryPayAmt = (TextView) findViewById(R.id.deliveryPayAmt);
                    deliveryPayAmt.setText("INR " + deliveryPrice.toString());
                    orderDetail.setDeliveryType("cod");
                    orderDetail.setPaidAmt(cod_online_updated_price.toString());
                    orderDetail.setDeliveryAmt(deliveryPrice.toString());
                    layout.setVisibility(View.VISIBLE);
                    break;
                }
        }
    }

    private boolean validateInput() {
        EditText name = (EditText) findViewById(R.id.editTextName);
        EditText phone = (EditText) findViewById(R.id.editTextPhone);
        EditText email = (EditText) findViewById(R.id.editTextEmail);
        EditText address = (EditText) findViewById(R.id.editTextAddressLine1);
        EditText address2 = (EditText) findViewById(R.id.editTextAddressLine2);
        EditText pin = (EditText) findViewById(R.id.pincode);
        RadioButton radioButton = (RadioButton) findViewById(R.id.pay_online);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.cod);

        if (StringUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(Checkout.this, "Full Name Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(Checkout.this, "Phone Number Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isEmpty(email.getText().toString())) {
            boolean check;
            check = android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
            if (!check) {
                Toast.makeText(Checkout.this, "Email Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (StringUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(Checkout.this, "Delivery Address Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isEmpty(pin.getText().toString())) {
            Toast.makeText(Checkout.this, "Pin Code Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!radioButton.isChecked() && !radioButton2.isChecked()) {
            Toast.makeText(Checkout.this, "Payment Option Not Selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        orderDetail.setName(name.getText().toString());
        orderDetail.setPhone(phone.getText().toString());
        orderDetail.setEmail(email.getText().toString());
        orderDetail.setAddress(address.getText().toString() + (address2.getText() != null ? address2.getText().toString() : "") + " " + pin.getText().toString());
        return true;
    }
}
