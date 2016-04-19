package com.example.mao.nomadeworkers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;

public class ClientEditActivity extends AppCompatActivity
{
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);

        Intent intent = getIntent();
        long clientId = intent.getLongExtra(ClientShowActivity.CLIENT_ID, 0);
        if (clientId != 0) {
            this.client = Client.findById(Client.class, clientId);
        } else {
            this.client = new Client();
        }

        this.fillForm();

        Button save = (Button) findViewById(R.id.save_client_btn);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (saveClient()) {
                    Toast.makeText(ClientEditActivity.this,
                            "ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClientEditActivity.this,
                            "erreur", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fillForm()
    {
        EditText nom = (EditText) findViewById(R.id.nom_client_input);
        nom.setText(this.client.getNom());

        EditText prenom = (EditText) findViewById(R.id.prenom_client_input);
        prenom.setText(this.client.getPrenom());

        RadioGroup civilite = (RadioGroup) findViewById(R.id.civilite_client_input);
        switch (this.client.getCivilite()) {
            case "M":
                civilite.check(R.id.civilite_m_client_input);
                break;
            case "Mme":
                civilite.check(R.id.civilite_mme_client_input);
                break;
            case "Mlle":
                civilite.check(R.id.civilite_mlle_client_input);
                break;
            default:
                civilite.check(R.id.civilite_m_client_input);
        }

        EditText telephone = (EditText) findViewById(R.id.telephone_client_input);
        telephone.setText(this.client.getTelephone());

        EditText adresse = (EditText) findViewById(R.id.adresse_client_input);
        adresse.setText(this.client.getAdresse());

        // todo email
    }

    protected boolean validateForm()
    {
        EditText nom = (EditText) findViewById(R.id.nom_client_input);
        if (nom.getText().toString().isEmpty()) {
            return false;
        }

        EditText prenom = (EditText) findViewById(R.id.prenom_client_input);
        if (prenom.getText().toString().isEmpty()) {
            return false;
        }

        EditText telephone = (EditText) findViewById(R.id.telephone_client_input);
        if (telephone.getText().toString().isEmpty()) {
            return false;
        }

        EditText adresse = (EditText) findViewById(R.id.adresse_client_input);
        if (adresse.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    protected boolean saveClient()
    {
        boolean saved = false;
        if (this.validateForm()) {

            EditText nom = (EditText) findViewById(R.id.nom_client_input);
            EditText prenom = (EditText) findViewById(R.id.prenom_client_input);
            RadioGroup civilite = (RadioGroup) findViewById(R.id.civilite_client_input);
            RadioButton checkedCivilite = (RadioButton) findViewById(civilite.getCheckedRadioButtonId());
            EditText telephone = (EditText) findViewById(R.id.telephone_client_input);
            EditText adresse = (EditText) findViewById(R.id.adresse_client_input);

            this.client.setNom(nom.getText().toString());
            this.client.setPrenom(prenom.getText().toString());
            this.client.setCivilite(checkedCivilite.getText().toString());
            this.client.setTelephone(telephone.getText().toString());
            this.client.setAdresse(adresse.getText().toString());

            this.client.save();
            saved = true;
        }

        return saved;
    }
}
