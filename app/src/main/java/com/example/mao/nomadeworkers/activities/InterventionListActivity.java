package com.example.mao.nomadeworkers.activities;

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

import java.text.SimpleDateFormat;
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

        Client swan = new Client("mougnoz", "swan", "M", "bla bla bla", "0123456789", "sqdqs@sqdd.com");
        swan.save();

//        Calendar tps = Calendar.getInstance();
//        tps.set(2016, 12, 1);
//        Intervention int1 = new Intervention("1ere intervention", tps, "pas fait", swan);
//        Intervention int2 = new Intervention("2eme intervention", tps, "pas fait", swan);
//        int1.save();
//        int2.save();
//
//        List<Intervention> interventionList = swan.getInterventions();

        tableLayout = (TableLayout)findViewById(R.id.listInterventionLayout);

        for(int i=0;i<20;i++){
            Calendar c = Calendar.getInstance();
            View tableRow = LayoutInflater.from(this).inflate(R.layout.list_intervention_row, null, false);
            TextView dateInter  = (TextView) tableRow.findViewById(R.id.dateIntervention);
            TextView descInter  = (TextView) tableRow.findViewById(R.id.descriptionIntervention);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c.getTime());
            String desc = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
            dateInter.setText(formattedDate);
            descInter.setText(desc.substring(0,45)+"[...]");
            tableLayout.addView(tableRow);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
