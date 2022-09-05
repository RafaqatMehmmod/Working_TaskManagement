package com.example.taskmanagement.model;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 29/06/2022
 */
public class RegisterUserModel {
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;
    String name;

    public RegisterUserModel(String imageUrl, String name, String designation) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.designation = designation;
    }

    public RegisterUserModel() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String designation;

    @BindingAdapter("android:imgUrlHP")
    public static void loadImageHP(ImageView imageView, Uri companyLogo)
    {
//        ImageView imageView= (ImageView) view;
//        Picasso.get().load(companyLogo).into(imageView);
        Picasso.get().load(companyLogo).into(imageView);


    }


}
