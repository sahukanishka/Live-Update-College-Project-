package com.example.attendenceapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class live_weather extends Fragment {
    @Nullable
    private RequestQueue mrequestqueu;
    private FloatingActionButton btn;
    private TextView yourcity_text;
    private TextView yourstate_text;
    private TextView yourcountry_text;
    private EditText find_city;
    private TextView temp_text;
    private TextView temp_min_text ;
    private  TextView temp_max_text ;
    private TextView city_text;
    private TextView description_text;
    private TextView country_text;
    private TextView weather_type_text;
    private TextView humidity_text;
    private TextView preasure_text;
    private String city_name;
    private TextView showLocationTxt;
    private Button alertbox_btn;
    private TextView vstup;
    private Bundle savedState = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_layout, container, false);




        btn = v.findViewById(R.id.floatingActionButton2);
        temp_text = v.findViewById(R.id.temp_text);
        city_text = v.findViewById(R.id.city_text);
        description_text = v.findViewById(R.id.description_text);
        country_text = v.findViewById(R.id.country_text);
        weather_type_text = v.findViewById(R.id.weather_type_text);
        preasure_text = v.findViewById(R.id.pressure_text);
        humidity_text = v.findViewById(R.id.humidity_text);
//        find_city = findViewById(R.id.find_city);
        showLocationTxt = v.findViewById(R.id.locations_text);
        yourcity_text = v.findViewById(R.id.yourcity_text);
        yourstate_text = v.findViewById(R.id.yourstate_text);
        yourcountry_text = v.findViewById(R.id.yourcountry_text);
        temp_max_text = v.findViewById(R.id.temp_max_text);
        temp_min_text = v.findViewById(R.id.temp_min_text);


        mrequestqueu = Volley.newRequestQueue(getActivity());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,
//                        "Button pressed", Toast.LENGTH_LONG).show();
                callalertbox();
                jsonParse();

            }
        });
        jsonParse();
        return  v ;

    }



    private void jsonParse() {
        String url ,hrl  ;
        if(city_name == null ){
             hrl = "http://api.openweathermap.org/data/2.5/weather?q="+"delhi"+"&appid=b7d6a69e341e91ca31cefbd99139d193";
        }
        else {
            hrl = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=b7d6a69e341e91ca31cefbd99139d193";
        }
        url = hrl ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Toast.makeText(MainActivity.this,
//                                    "try block", Toast.LENGTH_LONG).show();
                            JSONArray weatherarray = response.getJSONArray("weather");
                            JSONObject weatherobject = weatherarray.getJSONObject(0);
                            JSONObject mainobject = response.getJSONObject("main");
                            JSONObject sysobject = response.getJSONObject("sys");

                            double temp_cell = (mainobject.getDouble("temp") );
                            temp_cell = temp_cell - 273 ;
                            temp_cell =  Math.floor(temp_cell * 100) / 100;
                            String temp_view = String.valueOf(temp_cell);

                            double temp_cell_min = (mainobject.getDouble("temp_min") );
                            temp_cell_min = temp_cell_min - 273 ;
                            temp_cell_min =  Math.floor(temp_cell_min * 100) / 100;
                            String temp_view_min = String.valueOf(temp_cell_min);

                            double temp_cell_max = (mainobject.getDouble("temp_max") );
                            temp_cell_max = temp_cell_max - 273 ;
                            temp_cell_max =  Math.floor(temp_cell_max * 100) / 100;
                            String temp_view_max = String.valueOf(temp_cell_max);


                            String pressure_view = String.valueOf(mainobject.getDouble("pressure"));
                            String humidity_view = String.valueOf(mainobject.getDouble("humidity"));
                            String weather_view = weatherobject.getString("main");
                            String description_view = weatherobject.getString("description");
//
                            String country_view = sysobject.getString("country");
                            String city_view = response.getString("name");
//

//
//                            Toast.makeText(MainActivity.this,
//                                    "printing data", Toast.LENGTH_LONG).show();
                            temp_max_text.setText("Max Temperature : "+temp_view_max+"°C");
                            temp_min_text.setText("Min Temperature : "+temp_view_min+"°C");
                            temp_text.setText(temp_view +"°C");
                            city_text.setText("City "+city_view);
                            country_text.setText("Country : "+country_view);
                            description_text.setText("Type of weather : "+description_view);
                            weather_type_text.setText("Maybe : "+weather_view);
                            humidity_text.setText("Humidity : "+humidity_view);
                            preasure_text.setText("Air Pressure : "+pressure_view);
//                            icon_text.setText(icon_view);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mrequestqueu.add(request);
    }



    private void callalertbox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Your City");

// Set up the input
        final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city_name = input.getText().toString();
                jsonParse();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }










}