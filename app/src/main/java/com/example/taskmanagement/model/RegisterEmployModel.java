package com.example.taskmanagement.model;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 26/08/2022
 */
public class RegisterEmployModel {

    private String imageUrl;
    private String name;
    private String email;
    private String password;
    private String employCreatedBy;
    private String designation;
    private String key;

    public String getNormalUser() {
        return normalUser;
    }

    public void setNormalUser(String normalUser) {
        this.normalUser = normalUser;
    }

    private String normalUser;


    public RegisterEmployModel(String name, String email, String password, String imageUrl,String companyName,String key,String employCreatedBy,String designation,String normalUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.companyName=companyName;
        this.key=key;
        this.employCreatedBy=employCreatedBy;
        this.designation=designation;
        this.normalUser=normalUser;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }



    public String getEmployCreatedBy() {
        return employCreatedBy;
    }

    public void setEmployCreatedBy(String employCreatedBy) {
        this.employCreatedBy = employCreatedBy;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    String companyName;

    public RegisterEmployModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



//    @BindingAdapter("android:imageUrl")
//    public static void loadImage(ImageView imageView, Uri uri)
//    {
//        Picasso.get().load(uri).into(imageView);
//
//
//    }

    @BindingAdapter("android:imgUrlHP")
    public static void loadImageHP(ImageView imageView, Uri companyLogo)
    {
//        ImageView imageView= (ImageView) view;
//        Picasso.get().load(companyLogo).into(imageView);
        Picasso.get().load(companyLogo).into(imageView);


    }
}
