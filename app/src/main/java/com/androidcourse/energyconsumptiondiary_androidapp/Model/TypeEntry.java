package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.Objects;

public class TypeEntry {
    private String id;
    private int value;
    private ImpactType type;

    public TypeEntry() {
    }

    public TypeEntry(String id, int value, ImpactType type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeEntry)) return false;
        TypeEntry typeEntry = (TypeEntry) o;
        return getId() == typeEntry.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
