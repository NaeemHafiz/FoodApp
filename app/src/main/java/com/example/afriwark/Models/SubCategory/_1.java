
package com.example.afriwark.Models.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _1 {

    @SerializedName("sub_category_id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("sub_categories_name")
    @Expose
    private String subCategoriesName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoriesName() {
        return subCategoriesName;
    }

    public void setSubCategoriesName(String subCategoriesName) {
        this.subCategoriesName = subCategoriesName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
