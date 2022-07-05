/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pallgree
 */
public class ClassUser extends BaseModel {
    private int class_id;
    private int team_id;
    private int user_id;
    private int team_lead;
    private String dropout_date;
    private String user_notes;
    private String ongoing_eval;
    private String final_pres__eval;
    private String final_topic_eval;
    private int status;
    private String full_name;
    private String email;
    private String roll_number;
    private String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTeam_lead() {
        return team_lead;
    }

    public void setTeam_lead(int team_lead) {
        this.team_lead = team_lead;
    }

    public String getDropout_date() {
        return dropout_date;
    }

    public void setDropout_date(String dropout_date) {
        this.dropout_date = dropout_date;
    }

    public String getUser_notes() {
        return user_notes;
    }

    public void setUser_notes(String user_notes) {
        this.user_notes = user_notes;
    }

    public String getOngoing_eval() {
        return ongoing_eval;
    }

    public void setOngoing_eval(String ongoing_eval) {
        this.ongoing_eval = ongoing_eval;
    }

    public String getFinal_pres__eval() {
        return final_pres__eval;
    }

    public void setFinal_pres__eval(String final_pres__eval) {
        this.final_pres__eval = final_pres__eval;
    }

    public String getFinal_topic_eval() {
        return final_topic_eval;
    }

    public void setFinal_topic_eval(String final_topic_eval) {
        this.final_topic_eval = final_topic_eval;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    

    @Override
    public String toString() {
        return "ClassUser{" + "class_id=" + class_id + ", team_id=" + team_id + ", user_id=" + user_id + ", team_lead=" + team_lead + ", dropout_date=" + dropout_date + ", user_notes=" + user_notes + ", ongoing_eval=" + ongoing_eval + ", final_pres__eval=" + final_pres__eval + ", final_topic_eval=" + final_topic_eval + ", status=" + status + ", full_name=" + full_name + ", email=" + email + ", roll_number=" + roll_number + '}';
    }
     
  
   
    
}
