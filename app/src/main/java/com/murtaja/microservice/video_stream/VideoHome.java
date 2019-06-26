package com.murtaja.microservice.video_stream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class VideoHome extends AppCompatActivity {


    EditText emailEt,passwordET;
    String server_url="http://192.168.1.54/VideoSTLogIn.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_home);

        emailEt = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);






    }


    public void goRegistration(View view) {
        startActivity(new Intent(VideoHome.this,VideoRegistration.class));
        finish();
    }

    public void goToLogin(View view) {

        final String email= emailEt.getText().toString();
        final String password= passwordET.getText().toString();

        if(email.isEmpty()){
            emailEt.setError("Enter Email");
        }
        if (password.isEmpty()){
            passwordET.setError("Enter Password");
        }
        Toast.makeText(this, email+""+password, Toast.LENGTH_SHORT).show();
        if(email.isEmpty() && password.isEmpty()){
            
        }

        else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        Toast.makeText(VideoHome.this, response+"", Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code") ;

                        String interest = jsonObject.getString("interest");

                        if(code.equals("failed")){
                            Toast.makeText(VideoHome.this, "User doesn't exist.", Toast.LENGTH_SHORT).show();
                            emailEt.setError("User doesn't exist.");
                        }
                        else if (!password.equals(jsonObject.getString("password"))){
                            passwordET.setError("Password Error.");
                        }
                        else {
                            startActivity(new Intent(VideoHome.this,VideoStreaming.class).putExtra("interest",interest));
                            finish();
                        }




                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(VideoHome.this, "Network Failed", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email",email);

                    return params;
                }

            };
            DBHelper.getInstance(VideoHome.this).addToRequestQueue(stringRequest);
        }



    }
}
