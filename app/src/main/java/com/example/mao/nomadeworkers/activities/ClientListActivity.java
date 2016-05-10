package com.example.mao.nomadeworkers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        ArrayList<Client> clients = (ArrayList<Client>) Client.listAll(Client.class);
        ArrayAdapter<Client> adapter = new ArrayAdapter<>(this, R.layout.client_list_item, clients);

        ListView clientList = (ListView) findViewById(R.id.client_listview);
        clientList.setAdapter(adapter);

        clientList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent clientDetailIntent = new Intent(ClientListActivity.this, ClientShowActivity.class);
                Client client = (Client) parent.getAdapter().getItem(0);
                clientDetailIntent.putExtra("com.example.mao.nomadeworkers.CLIENT_ID", client.getId());
                startActivity(clientDetailIntent);
            }
        });
    }
}
