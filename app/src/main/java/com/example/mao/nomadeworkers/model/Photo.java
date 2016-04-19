package com.example.mao.nomadeworkers.model;

import com.orm.SugarRecord;

public class Photo extends SugarRecord
{
    private String chemin;
    private Intervention intervention;

    public Photo() {}

    public Photo(String chemin, Intervention intervention)
    {
        this.chemin = chemin;
        this.intervention = intervention;
    }

    public String getChemin()
    {
        return chemin;
    }

    public void setChemin(String chemin)
    {
        this.chemin = chemin;
    }

    public Intervention getIntervention()
    {
        return intervention;
    }

    public void setIntervention(Intervention intervention)
    {
        this.intervention = intervention;
    }
}
