package com.github.curioustechizen.fragmentstate;

import java.io.Serializable;

/**
 * Created by CRAFT BOX on 3/7/2018.
 */

public class ThirdModel implements Serializable{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    String name;
    String formFactor;
}
