package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

public class TypeEntry {
    int id;
    int value;
    ImpactType type;

    public TypeEntry(int id, int value, ImpactType type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public TypeEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ImpactType getType() {
        return type;
    }

    public void setType(ImpactType type) {
        this.type = type;
    }
}
