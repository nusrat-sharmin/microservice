package com.murtaja.microservice.video_stream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.murtaja.microservice.R;
import com.murtaja.microservice.RemortDataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VideoStreaming extends AppCompatActivity {


    VideoModel[] videos = new VideoModel[10];
    ListView listView;
    String server_base="http://192.168.1.54/";
    String server_url=server_base+"VideoSTStream.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);

        String interest=getIntent().getExtras().getString("interest");
        final String[] interests=interest.split(",");
        listView = findViewById(R.id.videoListViewID);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(VideoStreaming.this, "Network Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                int i=0;
                for (;i<interests.length;i++){
                    params.put("interest"+i,interests[i]);
                }

                if(i<4){

                }

                return params;
            }

        };
        DBHelper.getInstance(VideoStreaming.this).addToRequestQueue(stringRequest);




    }

    public void goChangeInterest(View view) {
    }
}
