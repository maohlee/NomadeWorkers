package com.example.mao.nomadeworkers.model;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.List;

public class Intervention extends SugarRecord
{
    private String description;
    private Calendar date;
    private String status;
    private Client client;

    public Intervention() {}

    public Intervention(String description, Calendar date, String status, Client client)
    {
        this.description = description;
        this.date = date;
        this.status = status;
        this.client = client;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Calendar getDate()
    {
        return date;
    }

    public void setDate(Calendar date)
    {
        this.date = date;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public List<Photo> getPhotos()
    {
        return Photo.find(Photo.class, "intervention = ?", String.valueOf(this.getId()));
    }
}
