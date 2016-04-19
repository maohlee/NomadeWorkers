package com.example.mao.nomadeworkers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;

public class ClientShowActivity extends AppCompatActivity
{
    private Client client;
    public static final String CLIENT_ID = "com.example.mao.nomadeworkers.CLIENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_show);

        Intent intent = getIntent();
        long clientId = intent.getLongExtra(ClientShowActivity.CLIENT_ID, 0);
        if (clientId != 0) {
            this.client = Client.findById(Client.class, clientId);
        } else {
            finish();
        }

        TextView nom = (TextView) findViewById(R.id.nom_client);
        String nomComplet = client.getCivilite() + ". " + client.getNom() + " " + client.getPrenom();
        nom.setText(nomComplet);

        TextView telephone = (TextView) findViewById(R.id.telephone_client);
        telephone.setText(client.getTelephone());

        TextView adresse = (TextView) findViewById(R.id.adresse_client);
        adresse.setText(client.getAdresse());

        TextView email = (TextView) findViewById(R.id.email_client);
        email.setText(client.getEmail());

        Button modifierBtn = (Button) findViewById(R.id.modifier_client_btn);
        modifierBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ClientShowActivity.this, ClientEditActivity.class);
                intent.putExtra(ClientShowActivity.CLIENT_ID, client.getId());
                startActivity(intent);
            }
        });
    }
}
