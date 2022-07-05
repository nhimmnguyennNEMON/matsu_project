/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author SY NGUYEN
 */
public class Feature extends BaseModel{
    
    private String feature_name;
    private int team_id;
    private int class_id;
    private String class_code;
    private int trainer_id;
    private String topic_name;
    private String topic_code;
    private int subject_id;
    private String subject_code;
    private String subject_name;
    private String team_name;
    private int team_leader;
    private int user_id;
    private String full_name;
    private int status;
    private String desc;

    public Feature() {
    }

    public Feature(String feature_name, int team_id, int class_id, String class_code, int trainer_id, String topic_name, String topic_code, int subject_id, String subject_code, String subject_name, String team_name, int team_leader, int user_id, String full_name, int status, String desc) {
        this.feature_name = feature_name;
        this.team_id = team_id;
        this.class_id = class_id;
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.topic_name = topic_name;
        this.topic_code = topic_code;
        this.subject_id = subject_id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.team_name = team_name;
        this.team_leader = team_leader;
        this.user_id = user_id;
        this.full_name = full_name;
        this.status = status;
        this.desc = desc;
    }

    public Feature(String feature_name, int team_id, int class_id, String class_code, int trainer_id, String topic_name, String topic_code, int subject_id, String subject_code, String subject_name, String desc, int team_id_cu, int team_leader, int user_id, String full_name, int status, int id) {
        super(id);
        this.feature_name = feature_name;
        this.team_id = team_id;
        this.class_id = class_id;
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.topic_name = topic_name;
        this.topic_code = topic_code;
        this.subject_id = subject_id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.team_name = team_name;
        this.team_leader = team_leader;
        this.user_id = user_id;
        this.full_name = full_name;
        this.status = status;
        this.desc = desc;
    }

    public Feature(String feature_name, int team_id, int status, String desc) {
        this.feature_name = feature_name;
        this.team_id = team_id;
        this.status = status;
        this.desc = desc;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_code() {
        return topic_code;
    }

    public void setTopic_code(String topic_code) {
        this.topic_code = topic_code;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(int team_leader) {
        this.team_leader = team_leader;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Feature{" + "feature_name=" + feature_name + ", team_id=" + team_id + ", class_id=" + class_id + ", class_code=" + class_code + ", trainer_id=" + trainer_id + ", topic_name=" + topic_name + ", topic_code=" + topic_code + ", subject_id=" + subject_id + ", subject_code=" + subject_code + ", subject_name=" + subject_name + ", team_name=" + team_name + ", team_leader=" + team_leader + ", user_id=" + user_id + ", full_name=" + full_name + ", status=" + status + '}' + "\n";
    }

    
    
}
