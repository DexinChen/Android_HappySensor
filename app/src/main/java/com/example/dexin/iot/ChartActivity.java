package com.example.dexin.iot;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

class Pair {
    String time;
    String predict;
    public Pair(String time, String predict) {
        this.time = time;
        this.predict = predict;
    }
}

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");

        AsyncHttpClient client = new AsyncHttpClient();
        String apiUrl = "https://l631qoa4b4.execute-api.us-east-1.amazonaws.com/newstate";
        String requestUrl = apiUrl+"?username="+username;

        client.get(requestUrl, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String pre = new String(response);
                try {
                    JSONObject json = new JSONObject(pre);
                    JSONArray msg = (JSONArray) json.get("Items");
                    Pair[] info = new Pair[msg.length()];
                    Log.e("place1",String.valueOf(info.length));
                    for (int i = 0; i < msg.length(); i++) {
                        JSONObject one = msg.getJSONObject(i);
                        String time = (String) one.get("userInfo");
                        String predict = (String) one.get("Happiness");
                        if(predict.contentEquals("Yes"))
                            predict = "1";
                        else if(predict.contentEquals("Medium"))
                            predict = "0";
                        else
                            predict = "-1";
                        info[i] = new Pair(time,predict);
                        Log.e("place2","hh");
                    }
                    Arrays.sort(info, new Comparator<Pair>() {
                        public int compare(Pair a, Pair b) {
                            return a.time.compareTo(b.time);
                        }
                    });
                    for(int i=0;i<info.length;i++) {
                        Log.e("------------------",String.valueOf(i)+": "+info[i].predict);
                    }
                    Log.e("place3","hh");
                    for(int i=0;i<10;i++) {
                        Log.e("------------------",info[i].predict);
                    }
                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    int num = info.length;
                    DataPoint[] points = new DataPoint[num];
                    for(int i=0;i<num;i++) {
                        points[i] = new DataPoint(i,Integer.parseInt(info[i].predict));
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-1);
                    graph.getViewport().setMaxY(1);
                    series.setColor(Color.BLACK);

                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setGridColor(Color.GRAY);
                    graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
                    //graph.setBackgroundColor(Color.BLACK);
                    graph.setTitleColor(Color.BLACK);

                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    String[] xLables = new String[num];
                    for(int i=0;i<num;i++) {
                        String t = info[i].time;
                        String m_d = String.valueOf(i);
                        xLables[i] = m_d;
                    }

                    //staticLabelsFormatter.setHorizontalLabels(xLables);
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                }
                catch (Exception e){
                    Log.e("-------------------",pre);
                }
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
}
