package com.sgtech.learnkotlin;


public class DataHolder {
    String title;
    int _id;

    public DataHolder(String title) {
        this.title = title;
    }

    public DataHolder() {
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public DataHolder(String title, int _id) {
        this.title = title;
        this._id = _id;
    }
}
