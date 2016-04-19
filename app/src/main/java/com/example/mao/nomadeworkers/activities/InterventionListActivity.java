package com.example.mao.nomadeworkers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;
import com.example.mao.nomadeworkers.model.Client;
import com.example.mao.nomadeworkers.model.Intervention;
import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InterventionListActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        EXEMPLE UTILISATION ORM :

//        Client swan = new Client("mougnoz", "swan", "M", "bla bla bla", "0123456789");
//        swan.save();
//
//        Calendar tps = Calendar.getInstance();
//        tps.set(2016, 12, 1);
//        Intervention int1 = new Intervention("1ere intervention", tps, "pas fait", swan);
//        Intervention int2 = new Intervention("2eme intervention", tps, "pas fait", swan);
//        int1.save();
//        int2.save();
//
//        List<Intervention> interventionList = swan.getInterventions();

        List<Intervention> interventionList = Intervention.listAll(Intervention.class);


        tableLayout = (TableLayout)findViewById(R.id.listInterventionLayout);

        for(Intervention i : interventionList)
        {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.list_intervention_row, null, false);
            TextView dateInter  = (TextView) tableRow.findViewById(R.id.dateIntervention);
            TextView descInter  = (TextView) tableRow.findViewById(R.id.descriptionIntervention);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(i.getDate().getTime());
            dateInter.setText(formattedDate);
            if(i.getDescription().length() < 45)
                descInter.setText(i.getDescription()+"[...]");
            else
                descInter.setText(i.getDescription().substring(0, 45) + "[...]");

            tableLayout.addView(tableRow);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Client swan = new Client("mougnoz", "swan", "M", "bla bla bla", "0123456789");
//                swan.save();

//                Calendar tps = Calendar.getInstance();
//                tps.set(2016, 12, 1);
//                Intervention int1 = new Intervention("1ere intervention magueule", tps, "pas fait", swan);
//                Intervention int2 = new Intervention("2eme intervention wesh", tps, "pas fait", swan);
//                int1.save();
//                int2.save();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(InterventionListActivity.this,InterventionCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intervention_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
