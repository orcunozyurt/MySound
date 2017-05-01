package com.nerdzlab.mysound.Models;

/**
 * Created by orcun on 25.02.2017.
 */

public class SoundResource {

    private int resource_id;
    private String resource_name;
    private int icon_id;

    public SoundResource(int resource_id, String resource_name, int icon_id) {
        this.resource_id = resource_id;
        this.resource_name = resource_name;
        this.icon_id = icon_id;
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

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getSectionName() {
        String[] arr = resource_name.split("_");

        String output = arr[0].substring(0, 1).toUpperCase() + arr[0].substring(1);

        return output;
    }

    public String getCleanName() {
        String[] arr = resource_name.split("_");
        String clean_name="";

        int i = 0;
        for ( String ss : arr) {

            if(i != 0 )
            {
                clean_name += ss + " ";
            }
            i++;
        }

        String output = clean_name.substring(0, 1).toUpperCase() + clean_name.substring(1);
        return output;
    }
}
