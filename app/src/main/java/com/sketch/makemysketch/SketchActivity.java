package com.sketch.makemysketch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class SketchActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final String MAP_KEY = "map";
    private HashMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);
        TextView itemDescriptionView = (TextView) findViewById(R.id.item_details);
        String details = "A4 size handmade pencil sketch of the uploaded facial image will be delivered \n\n"
                + "Free Framing";
        itemDescriptionView.setText(details);

        map = new HashMap();
        ImageView imageView = (ImageView) findViewById(R.id.header_image);
        map.put("type", "sketch");
        map.put("price", new Double(500));
    }

    public void uploadImage(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(SketchActivity.this,
                Manifest.permission.WRITE_CALENDAR);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SketchActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        final Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void onOrderBtnClick(View v) {
        Context context = v.getContext();
        Intent intent1 = new Intent(context, Checkout.class);
        intent1.putExtra(MAP_KEY, map);
        context.startActivity(intent1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.header_image);
                imageView.setImageBitmap(bitmap);
                map.put("imageUri", uri);
            } catch (IOException ex) {
                Toast.makeText(SketchActivity.this, "Unable to upload image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
