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
public class SubjectSetting extends BaseModel {

    private Subject subject_id;
    private int type_id;
    private String setting_title;
    private String setting_value;
    private int display_order;
    private int status;

    public SubjectSetting() {
    }

    public SubjectSetting(int id, Subject subject_id, int type_id, String setting_title, String setting_value, int display_order, int status) {
        this.id = id;
        this.subject_id = subject_id;
        this.type_id = type_id;
        this.setting_title = setting_title;
        this.setting_value = setting_value;
        this.display_order = display_order;
        this.status = status;
    }

    public SubjectSetting(Subject subject_id, int type_id, String setting_title, String setting_value, int display_order, int status) {
        this.subject_id = subject_id;
        this.type_id = type_id;
        this.setting_title = setting_title;
        this.setting_value = setting_value;
        this.display_order = display_order;
        this.status = status;
    }

    public SubjectSetting(int type_id, String setting_title, String setting_value, int display_order, int status) {
        this.type_id = type_id;
        this.setting_title = setting_title;
        this.setting_value = setting_value;
        this.display_order = display_order;
        this.status = status;
    }

    public Subject getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Subject subject_id) {
        this.subject_id = subject_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getSetting_title() {
        return setting_title;
    }

    public void setSetting_title(String setting_title) {
        this.setting_title = setting_title;
    }

    public String getSetting_value() {
        return setting_value;
    }

    public void setSetting_value(String setting_value) {
        this.setting_value = setting_value;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubjectSetting{" + "setting_id=" + id + ", subject_id="
                + subject_id + ", type_id=" + type_id + ", setting_title="
                + setting_title + ", setting_value=" + setting_value + ", display_order="
                + display_order + ", status=" + status + '}';
    }

}
