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
import android.widget.ScrollView;
import android.widget.Toast;
import android.graphics.Color;
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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//https://corona.lmao.ninja/v2/all
public class Corona_update extends Fragment {
    @Nullable
    private  int positionCountry;
    private RequestQueue mrequestqueucorona;
    Button searchbtn ;
    private String city_name_corona ;
   TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =  inflater.inflate(R.layout.corona_update,container,false);
            ScrollView scrollView;

            searchbtn = v.findViewById(R.id.searchbtn);
            tvCases = v.findViewById(R.id.tvCases);
            tvRecovered = v.findViewById(R.id.tvRecovered);
            tvCritical = v.findViewById(R.id.tvCritical);
            tvActive = v.findViewById(R.id.tvActive);
            tvTodayCases = v.findViewById(R.id.tvTodayCases);
            tvTotalDeaths = v.findViewById(R.id.tvDeaths);
            tvTodayDeaths = v.findViewById(R.id.tvTodayDeaths);
            tvAffectedCountries = v.findViewById(R.id.tvAffectedCountries);

            mrequestqueucorona = Volley.newRequestQueue(getActivity());

            jsoncoronadata();



            searchbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertboxon();
                    jsoncoronadata();
                }
            });

            return v ;

        }


        private void jsoncoronadata(){
            String url ,hrl  ;
            if(city_name_corona == null ){
                hrl =   url = "https://corona.lmao.ninja/v2/all";
            }
            else {
                hrl = url = "https://disease.sh/v2/countries/"+city_name_corona+"?yesterday=false&strict=true";
            }
            url = hrl ;

//
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());

                                tvCases.setText(jsonObject.getString("cases"));
                                tvRecovered.setText(jsonObject.getString("recovered"));
                                tvCritical.setText(jsonObject.getString("critical"));
                                tvActive.setText(jsonObject.getString("active"));
                                tvTodayCases.setText(jsonObject.getString("todayCases"));
                                tvTotalDeaths.setText(jsonObject.getString("deaths"));
                                tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                                tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));



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

            mrequestqueucorona.add(request);

        }

        private void alertboxon(){
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
                    city_name_corona = input.getText().toString();
                    jsoncoronadata();
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



