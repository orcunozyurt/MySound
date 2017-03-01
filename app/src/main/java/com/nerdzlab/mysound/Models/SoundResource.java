package com.nerdzlab.mysound.Models;

/**
 * Created by orcun on 25.02.2017.
 */

public class SoundResource {

    private int resource_id;
    private String resource_name;

    public SoundResource(int resource_id, String resource_name) {
        this.resource_id = resource_id;
        this.resource_name = resource_name;
    }

    public SoundResource() {
    }

    @Override
    public String toString() {
        return "SoundResource{" +
                "resource_id=" + resource_id +
                ", resource_name='" + resource_name + '\'' +
                '}';
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }
}
