package com.example.dexin.iot;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainPageActivity extends AppCompatActivity {
    public static String globalUri = "";
    public static String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Intent myIntent = getIntent();
        final String username = myIntent.getStringExtra("username");
        final Button detect = (Button) findViewById(R.id.Button3);
        final Button predict = (Button) findViewById(R.id.Button4);
        final Button chart = (Button) findViewById(R.id.Button5);
        final Button upload = (Button) findViewById(R.id.Upload);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainPageActivity.this, SensorsActivity.class);
                newIntent.putExtra("username",username);
                MainPageActivity.this.startActivity(newIntent);
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainPageActivity.this, PredictActivity.class);
                newIntent.putExtra("username",username);
                MainPageActivity.this.startActivity(newIntent);
            }
        });
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainPageActivity.this, ChartActivity.class);
                newIntent.putExtra("username",username);
                MainPageActivity.this.startActivity(newIntent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });
    }
    private void uploadPhoto() {
        Intent myIntent = getIntent();
        username = myIntent.getStringExtra("username");
        Uri myUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        globalUri = myUri.toString();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, myUri);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainPageActivity.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage("Your photo has been uploaded successfully");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        }
}

