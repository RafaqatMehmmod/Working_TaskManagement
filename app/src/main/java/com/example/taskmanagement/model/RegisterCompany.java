package com.example.taskmanagement.model;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 17/08/2022
 */
public class RegisterCompany {
    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    String loginID;
    public RegisterCompany(String companyLogo, String companyName, String companyEmail, String companyType, String companyCountry,String loginID) {
        this.companyLogo = companyLogo;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyType = companyType;
        this.companyCountry = companyCountry;
        this.loginID=loginID;
    }

    public RegisterCompany() {
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    private String companyName,companyEmail,companyType,companyCountry;

//    public Uri getCompanyLogo() {
//        return companyLogo;
//    }
//
//    public void setCompanyLogo(Uri companyLogo) {
//        this.companyLogo = companyLogo;
//    }

    private String companyLogo;

    @BindingAdapter("android:imgUrl")
    public static void loadImage(ImageView imageView, String companyLogo)
    {
//        ImageView imageView= (ImageView) view;
//        imageView.setImageURI(companyLogo);

        Picasso.get().load(companyLogo).into(imageView);

    }
}
