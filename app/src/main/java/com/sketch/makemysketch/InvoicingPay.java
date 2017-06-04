package com.sketch.makemysketch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;
import com.payu.india.Extras.PayUChecksum;
import com.sketch.makemysketch.model.OrderDetail;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class InvoicingPay extends AppCompatActivity {

    private OrderDetail orderDetail = null;
    private PayUmoneySdkInitilizer.PaymentParam paymentParam;
    private Spinner environmentSpinner;

    // Used when generating hash from SDK
    private PayUChecksum checksum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicing_pay);

        final Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            orderDetail = (OrderDetail) bd.get("orderDetail");
        }
        navigateToBaseActivity(orderDetail);
    }

    /**
     * This method prepares all the payments params to be sent to PayuBaseActivity.java
     */
    public void navigateToBaseActivity(OrderDetail orderDetail) {

        String merchantKey = "b8x5ys";
        String merchantId = "5167228";
        String sUrl = "https://test.payumoney.com/mobileapp/payumoney/success.php";
        String fUrl = "https://test.payumoney.com/mobileapp/payumoney/failure.php";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";

        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();


        builder.setAmount(new Double(orderDetail.getPaidAmt()))
                .setTnxId(orderDetail.getOrderId())
                .setPhone(orderDetail.getPhone())
                .setProductName(orderDetail.getImageName())
                .setFirstName(orderDetail.getName())
                .setEmail(orderDetail.getEmail())
                .setsUrl(sUrl)
                .setfUrl(fUrl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(false)
                .setKey(merchantKey)
                .setMerchantId(merchantId);

        paymentParam = builder.build();
        generateHashFromServer();
    }

    /**
     * This method generates hash from server.
     */
    public void generateHashFromServer() {
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(orderDetail);
    }

    /**
     * This AsyncTask generates hash from server.
     */
    private class GetHashesFromServerTask extends AsyncTask<OrderDetail, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InvoicingPay.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(OrderDetail... orderDetail) {
            try {
                String url_upload_details = "https://makemysketch-prateekmehta.rhcloud.com/rest/upload_order_details";
                String url_get_hash = "https://makemysketch-prateekmehta.rhcloud.com/rest/get_hash";
                RestTemplate restTemplate = new RestTemplate();
                MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();
                httpHeaders.add("Content-Type", "application/json");

                Gson gson = new Gson();
                HttpEntity<String> httpEntity = new HttpEntity<String>(gson.toJson(orderDetail[0]), httpHeaders);
                ResponseEntity<Boolean> response = restTemplate.exchange(new URI(url_upload_details), HttpMethod.POST, httpEntity, Boolean.class);
                if (response.getBody()) {
                    ResponseEntity<String> responseHash = restTemplate.exchange(new URI(url_get_hash), HttpMethod.POST, httpEntity, String.class);
                    return responseHash.getBody();
                } else {
                    return "";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";

        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            progressDialog.dismiss();
            paymentParam.setMerchantHash(str);
            PayUmoneySdkInitilizer.startPaymentActivityForResult(InvoicingPay.this, paymentParam);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
                showDialogMessage("Payment Success Id : " + paymentId);
            } else if (resultCode == RESULT_CANCELED) {
                showDialogMessage("cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {
                        showDialogMessage("failure");
                    }
                }
                //Write your code if there's no result
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                showDialogMessage("User returned without login");
            }
        }
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }
}