/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author quang
 */
public class Team extends BaseModel {

    private String team_name;
    private Class class_id;
    private String topic_code;
    private String topic_name;
    private String gitlab_url;
    private int status;
    private String desc;

    public Team() {
    }

    public Team(String team_name, Class class_id, String topic_code,
            String topic_name, String gitlab_url, int status, String desc, int id) {
        super(id);
        this.team_name = team_name;
        this.class_id = class_id;
        this.topic_code = topic_code;
        this.topic_name = topic_name;
        this.gitlab_url = gitlab_url;
        this.desc = desc;
        this.status = status;
    }

    public Team(String team_name, Class class_id, String topic_code, String topic_name, String gitlab_url, int status, String desc) {
        this.team_name = team_name;
        this.class_id = class_id;
        this.topic_code = topic_code;
        this.topic_name = topic_name;
        this.gitlab_url = gitlab_url;
        this.status = status;
        this.desc = desc;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Class getClass_id() {
        return class_id;
    }

    public void setClass_id(Class class_id) {
        this.class_id = class_id;
    }

    public String getTopic_code() {
        return topic_code;
    }

    public void setTopic_code(String topic_code) {
        this.topic_code = topic_code;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getGitlab_url() {
        return gitlab_url;
    }

    public void setGitlab_url(String gitlab_url) {
        this.gitlab_url = gitlab_url;
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

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", team_name=" + team_name + ", class_id=" + class_id + ", topic_code=" + topic_code
                + ", topic_name=" + topic_name + ", gitlab_url=" + gitlab_url
                + ", status=" + status + ", desc=" + desc + '}';
    }

}
