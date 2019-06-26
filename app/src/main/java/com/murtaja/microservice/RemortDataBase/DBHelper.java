package com.murtaja.microservice.RemortDataBase;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class DBHelper {
    private static DBHelper dbHelper;
    private RequestQueue requestQueue;
    private static Context context;

    private DBHelper(Context mContext){
        context=mContext;
        requestQueue=getRequestQueue();

    }

    public static synchronized DBHelper getInstance(Context mContext){
        if(dbHelper==null){
            dbHelper= new DBHelper(mContext);
        }
        return dbHelper;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T>void addToRequestQueue(Request<T> request){


        requestQueue.add(request);
    }


}
