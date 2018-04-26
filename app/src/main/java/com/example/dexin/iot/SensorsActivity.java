package com.example.dexin.iot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;



public class SensorsActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mProximity;
    private Sensor mLight;
    private Sensor mMegnetic;
    private Sensor mGravity;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent myIntent = getIntent();
        final String username = myIntent.getStringExtra("username");
        final DynamoDBMapper dynamoDBMapper;
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:db12e203-9a39-43fa-8dab-2309d4309d39", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .build();
        final Button upload = (Button) findViewById(R.id.button4);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millis = System.currentTimeMillis();
                UserInfo info = new UserInfo();
                info.setUserInfo(username+String.valueOf(millis));
                info.setUsername(username);
                TextView pressure = (TextView) findViewById(R.id.textView2);
                info.setPressure(pressure.getText().toString());
                TextView proximity = (TextView) findViewById(R.id.textView4);
                info.setProximity(proximity.getText().toString());
                TextView light = (TextView) findViewById(R.id.textView6);
                info.setLight(light.getText().toString());
                TextView magnetic = (TextView) findViewById(R.id.textView8);
                info.setMagnetic(magnetic.getText().toString());
                TextView gravity = (TextView) findViewById(R.id.textView10);
                info.setGravity(gravity.getText().toString());
                TextView accelerometer = (TextView) findViewById(R.id.textView12);
                info.setAccelerometer(accelerometer.getText().toString());
                TextView gyroscope = (TextView) findViewById(R.id.textView14);
                info.setGyroscope(gyroscope.getText().toString());
                Spinner happiness = (Spinner) findViewById(R.id.spinner);
                info.setHappiness(happiness.getSelectedItem().toString());
                dynamoDBMapper.save(info);
                Intent newIntent = new Intent(SensorsActivity.this, RecordedActivity.class);
                newIntent.putExtra("username",username);
                startActivity(newIntent);
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] answers = new String[]{
                "Yes",
                "No",
                "Medium",
        };
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,answers
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMegnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        TextView textView;
        int sensorType = event.sensor.getType();
        float data = event.values[0];
        int dataInt = (int)(data * 100);
        data = (float) dataInt/100;
        switch (sensorType) {
            case Sensor.TYPE_PRESSURE:
                textView = (TextView)findViewById(R.id.textView2);
                textView.setText(String.valueOf(data));
                break;
            // Event came from the light sensor
            case Sensor.TYPE_PROXIMITY:
                textView = (TextView)findViewById(R.id.textView4);
                textView.setText(String.valueOf(data));
                break;
            case Sensor.TYPE_LIGHT:
                textView = (TextView)findViewById(R.id.textView6);
                textView.setText(String.valueOf(data));
                // Handle light sensor
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                textView = (TextView)findViewById(R.id.textView8);
                textView.setText(String.valueOf(data));
                break;
            case Sensor.TYPE_GRAVITY:
                textView = (TextView)findViewById(R.id.textView10);
                textView.setText(String.valueOf(data));
                break;
            case Sensor.TYPE_ACCELEROMETER:
                textView = (TextView)findViewById(R.id.textView12);;
                textView.setText(String.valueOf(data));
                break;
            case Sensor.TYPE_GYROSCOPE:
                textView = (TextView)findViewById(R.id.textView14);;
                textView.setText(String.valueOf(data));
                break;
            default:
                // do nothing
        }

        // Do something with this sensor data.
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMegnetic, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}