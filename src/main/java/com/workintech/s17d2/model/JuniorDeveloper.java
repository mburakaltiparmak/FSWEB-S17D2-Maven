package com.workintech.s17d2.model;

public class JuniorDeveloper extends Developer{
    public JuniorDeveloper(int id, String name, double salary, Experience experience) {
        super(id, name, salary, Experience.JUNIOR);
    }

    public JuniorDeveloper(int id, String name, double salary) {
        super(id, name, salary);
    }
}
