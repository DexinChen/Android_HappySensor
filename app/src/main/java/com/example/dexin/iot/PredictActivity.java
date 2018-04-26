package com.example.dexin.iot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class PredictActivity extends Activity implements SensorEventListener {
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
        setContentView(R.layout.activity_predict);

        Intent myIntent = getIntent();
        final String username = myIntent.getStringExtra("username");
        final Button predict = (Button) findViewById(R.id.button4);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username;
                TextView pressure = (TextView) findViewById(R.id.textView2);
                String Pressure = pressure.getText().toString();
                TextView proximity = (TextView) findViewById(R.id.textView4);
                String Proximity = proximity.getText().toString();
                TextView light = (TextView) findViewById(R.id.textView6);
                String Light = light.getText().toString();
                TextView magnetic = (TextView) findViewById(R.id.textView8);
                String Magnetic = magnetic.getText().toString();
                TextView gravity = (TextView) findViewById(R.id.textView10);
                String Gravity = gravity.getText().toString();
                TextView accelerometer = (TextView) findViewById(R.id.textView12);
                String Accelerometer = accelerometer.getText().toString();
                TextView gyroscope = (TextView) findViewById(R.id.textView14);
                String Gyroscope = gyroscope.getText().toString();
                AsyncHttpClient client = new AsyncHttpClient();
                String apiUrl = "https://lgke3gutkc.execute-api.us-east-1.amazonaws.com/PredictHappiness";
                String requestUrl = apiUrl+"?Username="+Username+"&Pressure="+Pressure+"&Proximity="+Proximity+"&Light="+Light+"&MagneticField="+Magnetic+"&Gravity="+Gravity+"&Accelerometer="+Accelerometer+"&Gyroscope="+Gyroscope;
                client.get(requestUrl, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        String pre = new String(response);
                        AlertDialog alertDialog = new AlertDialog.Builder(PredictActivity.this).create();
                        alertDialog.setTitle("Predict Result");
                        alertDialog.setMessage("Your happiness level may be: "+pre);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        // called when response HTTP status is "200 OK"
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
            }
        });
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
