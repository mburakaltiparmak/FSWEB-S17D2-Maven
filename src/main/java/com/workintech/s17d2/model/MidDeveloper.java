package com.workintech.s17d2.model;

public class MidDeveloper extends Developer{
    public MidDeveloper(int id, String name, double salary, Experience experience) {
        super(id, name, salary, Experience.MID);
    }

    public MidDeveloper(int id, String name, double salary) {
        super(id, name, salary);
    }
}
