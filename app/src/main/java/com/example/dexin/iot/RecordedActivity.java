package com.example.dexin.iot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded);
        Intent myIntent = getIntent();
        final String username = myIntent.getStringExtra("username");
        final Button upload = (Button) findViewById(R.id.button4);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(RecordedActivity.this, MainPageActivity.class);
                newIntent.putExtra("username",username);
                startActivity(newIntent);
            }
        });
    }
}
