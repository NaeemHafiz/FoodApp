
package com.example.afriwark.Models.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryInfo {

    @SerializedName("1")
    @Expose
    private com.example.afriwark.Models.SubCategory._1 _1;
    @SerializedName("2")
    @Expose
    private com.example.afriwark.Models.SubCategory._2 _2;

    public com.example.afriwark.Models.SubCategory._1 get1() {
        return _1;
    }

    public void set1(com.example.afriwark.Models.SubCategory._1 _1) {
        this._1 = _1;
    }

    public com.example.afriwark.Models.SubCategory._2 get2() {
        return _2;
    }

    public void set2(com.example.afriwark.Models.SubCategory._2 _2) {
        this._2 = _2;
    }

}
