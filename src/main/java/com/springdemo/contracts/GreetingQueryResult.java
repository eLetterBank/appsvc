package com.springdemo.contracts;

import java.util.List;

public class GreetingQueryResult {
    private final String content;

    public GreetingQueryResult(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    private List titles;
    public void setTitles(List titles)
    {
        this.titles = titles;
    }

    public List getTitles()
    {
        return this.titles;
    }
}
