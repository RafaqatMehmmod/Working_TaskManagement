package com.example.taskmanagement.model;

/**
 * Created by Rafaqat Mehmood
 * Whatsapp No:+923101025532
 * 23/08/2022
 */
public class AddTaskModel {

    public AddTaskModel(String title, String description, String from, String to,String assignTo,String assignerDesignation,String status,String day,String month,String year,String email) {
        this.title = title;
        this.description = description;
        this.from = from;
        this.to = to;
        this.assignTo=assignTo;
        this.assignerDesignation=assignerDesignation;
        this.status=status;
        this.day=day;
        this.month=month;
        this.year=year;
        this.email=email;

    }

    String title;
    String assignTo;
    String day;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    String month;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    String year;

    public AddTaskModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssignerDesignation() {
        return assignerDesignation;
    }

    public void setAssignerDesignation(String assignerDesignation) {
        this.assignerDesignation = assignerDesignation;
    }

    String assignerDesignation;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    String description;
    String from;
    String to;
}
