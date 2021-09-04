package com.omar.bemyhart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.omar.bemyhart.DataBase.DataBaseAccess;
import com.omar.bemyhart.Models.City_Model;
import com.omar.bemyhart.Models.Country_Model;
import com.omar.bemyhart.Models.State_Model;

import java.util.ArrayList;

public class Setup_Profile_Activity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteCountry,autoCompleteState,autoCompleteCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        autoCompleteCountry = findViewById(R.id.inputCountry);
        autoCompleteState = findViewById(R.id.inputState);
        autoCompleteCity = findViewById(R.id.inputCity);

        getCountry();
    }

    private void getCountry(){
        DataBaseAccess access = DataBaseAccess.getInstance(this);
        access.open();
        ArrayList<Country_Model> list = access.getCountries();
        access.close();
        ArrayList<String> list_count = new ArrayList<String>();

        for (Country_Model list_all : list){
            list_count.add(list_all.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.option_item,list_count);
        autoCompleteCountry.setAdapter(arrayAdapter);
        autoCompleteCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getState(list.get(position).getId());
            }
        });
    }

    private void getState(String country_id){
        DataBaseAccess access = DataBaseAccess.getInstance(this);
        access.open();
        ArrayList<State_Model> list = access.getState(country_id);
        access.close();
        ArrayList<String> list_count = new ArrayList<String>();

        for (State_Model list_all : list){
            list_count.add(list_all.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.option_item,list_count);
        autoCompleteState.setAdapter(arrayAdapter);
        autoCompleteState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getCity(list.get(position).getId());
            }
        });
    }

    private void getCity(String state_id){
        DataBaseAccess access = DataBaseAccess.getInstance(this);
        access.open();
        ArrayList<City_Model> list = access.getCity(state_id);
        access.close();
        ArrayList<String> list_count = new ArrayList<String>();

        for (City_Model list_all : list){
            list_count.add(list_all.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.option_item,list_count);
        autoCompleteCity.setAdapter(arrayAdapter);
        autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}