package com.example.mao.nomadeworkers.model;

import com.orm.SugarRecord;

import java.util.List;

public class Client extends SugarRecord
{
    private String nom;
    private String prenom;
    private String civilite;
    private String adresse;
    private String telephone;

    public Client() {}

    public Client(String nom, String prenom, String civilite, String adresse, String telephone)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getCivilite()
    {
        return civilite;
    }

    public void setCivilite(String civilite)
    {
        this.civilite = civilite;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public List<Intervention> getInterventions()
    {
        return Intervention.find(Intervention.class, "client = ?", String.valueOf(this.getId()));
    }
}
