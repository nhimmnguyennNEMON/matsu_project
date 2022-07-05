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
public class Issue extends BaseModel {
    private int asignee_id;
    private String issue_title;
    private String desciption;
    private int gitlab_id;
    private String gitlab_link;
    private String due_date;
    private int team_id;
    private int milestone_id;
    private int function_id;
    private String labels;
    private String status;
    private int created_id;
    private int updated_id;
    private String created_time;
    private String updated_time;
    private String created_name;
    private String updated_name;
    private String team_code;
    private String class_code;
    private String milestone_name;
    private String function_name;
    private String assignee_name;

    public String getAssignee_name() {
        return assignee_name;
    }

    public void setAssignee_name(String assignee_name) {
        this.assignee_name = assignee_name;
    }

    public int getAsignee_id() {
        return asignee_id;
    }

    public void setAsignee_id(int asignee_id) {
        this.asignee_id = asignee_id;
    }

    public String getIssue_title() {
        return issue_title;
    }

    public void setIssue_title(String issue_title) {
        this.issue_title = issue_title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getGitlab_id() {
        return gitlab_id;
    }

    public void setGitlab_id(int gitlab_id) {
        this.gitlab_id = gitlab_id;
    }

    public String getGitlab_link() {
        return gitlab_link;
    }

    public void setGitlab_link(String gitlab_link) {
        this.gitlab_link = gitlab_link;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public int getFunction_id() {
        return function_id;
    }

    public void setFunction_id(int function_id) {
        this.function_id = function_id;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreated_id() {
        return created_id;
    }

    public void setCreated_id(int created_id) {
        this.created_id = created_id;
    }

    public int getUpdated_id() {
        return updated_id;
    }

    public void setUpdated_id(int updated_id) {
        this.updated_id = updated_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getCreated_name() {
        return created_name;
    }

    public void setCreated_name(String created_name) {
        this.created_name = created_name;
    }

    public String getUpdated_name() {
        return updated_name;
    }

    public void setUpdated_name(String updated_name) {
        this.updated_name = updated_name;
    }

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getMilestone_name() {
        return milestone_name;
    }

    public void setMilestone_name(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    @Override
    public String toString() {
        return "Issue{" + "asignee_id=" + asignee_id + ", issue_title=" + issue_title + ", desciption=" + desciption + ", gitlab_id=" + gitlab_id + ", gitlab_link=" + gitlab_link + ", due_date=" + due_date + ", team_id=" + team_id + ", milestone_id=" + milestone_id + ", function_id=" + function_id + ", labels=" + labels + ", status=" + status + ", created_id=" + created_id + ", updated_id=" + updated_id + ", created_time=" + created_time + ", updated_time=" + updated_time + ", created_name=" + created_name + ", updated_name=" + updated_name + ", team_code=" + team_code + ", class_code=" + class_code + ", milestone_name=" + milestone_name + ", function_name=" + function_name + ", assignee_name=" + assignee_name + '}';
    }
     
    

    
    

   
 
}
