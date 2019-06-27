
package com.example.afriwark.Models.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _2 {

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_categories_name")
    @Expose
    private String subCategoriesName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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
