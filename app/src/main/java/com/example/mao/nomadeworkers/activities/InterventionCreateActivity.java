package com.example.mao.nomadeworkers.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InterventionCreateActivity extends AppCompatActivity {
    private EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_create2);
        edittext = (EditText)findViewById(R.id.setDateInt);
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(InterventionCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }


//    private ArrayAdapter<Client> clientsAdapter(Client clientsArray[]) {
//
//        ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(this, android.R.layout.simple_list_item_1, clientsArray) {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                // setting the ID and text for every items in the list
//                Client item = getItem(position);
//
//
//                // visual settings for the list item
//                TextView listItem = new TextView(InterventionCreateActivity.this);
//
//                listItem.setText(item.getNom()+" "+item.getPrenom());
//                listItem.setTextSize(22);
//                listItem.setPadding(10, 10, 10, 10);
////                listItem.setTextColor(Color.WHITE);
//
//                return listItem;
//            }
//        };
//
//        return adapter;
//    }
}
