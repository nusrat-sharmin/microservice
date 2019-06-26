package com.murtaja.microservice.video_stream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class VideoRegistration extends AppCompatActivity {

    EditText emailET,passET,nameET;
    CheckBox songCB,entertainmentCB,religionCB,educationCB;
    Button regButton;
    String server_url="http://192.168.1.54/VideoSTReg.php";
    String email = "some";
    String password;
    String name;
    String interest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_registration);

        emailET = findViewById(R.id.videoRegEmailET);
        passET = findViewById(R.id.videoRegPassET);
        nameET = findViewById(R.id.videoRegNameET);

        songCB = findViewById(R.id.songCheckBox);
        entertainmentCB = findViewById(R.id.entertainmentCheckBox);
        religionCB = findViewById(R.id.religionCheckBox);
        educationCB = findViewById(R.id.educationCheckBox);




    }

    public void goRegister(View view) {


        final String email = emailET.getText().toString();
        final String password = passET.getText().toString();
        final String name=nameET.getText().toString();
        String editedInterest="";


        if(songCB.isChecked()){
            editedInterest = editedInterest+songCB.getText().toString();
        }
        if(entertainmentCB.isChecked()){
            editedInterest = editedInterest+","+entertainmentCB.getText().toString();
        }
        if(religionCB.isChecked()){
            editedInterest = editedInterest+","+religionCB.getText().toString();
        }
        if(educationCB.isChecked()){
            editedInterest = editedInterest+","+educationCB.getText().toString();
        }

        final String interest =editedInterest;


        if(emailET.getText().toString().isEmpty()){
            emailET.setError("Enter Email");
        }
        else if(nameET.getText().toString().isEmpty()){
            nameET.setError("Enter Name");
        }
        else if(passET.getText().toString().isEmpty()){
            passET.setError("Enter Password");
        }


        else if(interest.isEmpty()){
            Toast.makeText(this, "Select Interest.", Toast.LENGTH_SHORT).show();
        }


        else {



            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code") ;
                        String message = jsonObject.getString("message");
                        startActivity(new Intent(VideoRegistration.this,VideoHome.class));
                        finish();

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(VideoRegistration.this, "Network Failed", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email",email);
                    params.put("password",password);
                    params.put("name",name);
                    params.put("interest", interest);
                    return params;
                }

            };
            DBHelper.getInstance(VideoRegistration.this).addToRequestQueue(stringRequest);
        }



    }
}
