package com.springdemo.contracts;

import java.util.ArrayList;
import java.util.List;

public class GreetingQuery {
    private String name;
    private List titles;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setTitles(List titles)
    {
        this.titles = titles;
    }

    public List getTitles()
    {
        return this.titles;
    }
}
