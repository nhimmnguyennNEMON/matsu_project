/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import utils.TwoFactorUtils;

/**
 *
 * @author pallgree
 */
public class User extends BaseModel {
    private int user_id;
    private String full_name;
    private String email;
    private String date_of_birth;
    private String password;
    private int role_id;
    private String slack_account;
    private String avatar_link;
    private boolean status;
    private boolean is_verify;
    private String UUID;
    private String class_user;
    private long created_time;
    private String secret_key;
    private String phone_number;
    private String address;
    private int gender;
    private String time_create_token;
    private boolean online;
    private  String roll_number;

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public User() {
        this.is_verify=false;
        this.avatar_link = "https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png";
        this.secret_key= TwoFactorUtils.generateSecret(32);
    }

    public User(int user_id, String full_name, String email, String date_of_birth, String password, int role_id, String slack_account, String avatar_link, boolean status, boolean is_verify, String UUID, String class_user, long created_time, String secret_key, String phone_number, String address, int gender, String time_create_token) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.role_id = role_id;
        this.slack_account = slack_account;
        this.avatar_link = avatar_link;
        this.status = status;
        this.is_verify = is_verify;
        this.UUID = UUID;
        this.class_user = class_user;
        this.created_time = created_time;
        this.secret_key = secret_key;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.time_create_token = time_create_token;
    }
    
    public User(String full_name, String date_of_birth, int role_id, boolean status, String phone_number, String address, int gender) {
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.role_id = role_id;
        this.status = status;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
    }
    
    public User(String full_name, String date_of_birth, String slack_account, String avatar_link) {
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.slack_account = slack_account;
        this.avatar_link = avatar_link;
    }

    public User(String full_name, String email, String date_of_birth, int role_id, String avatar_link, boolean status, String phone_number, int gender, String time_create_token) {
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.role_id = role_id;
        this.avatar_link = avatar_link;
        this.status = status;
        this.phone_number = phone_number;
        this.gender = gender;
        this.time_create_token = time_create_token;
    }
    
    public User(String full_name, String email, String date_of_birth, String password,
            int role_id, String slack_account, String avatar_link, boolean status,
            boolean is_verify, String UUID, String class_user, String time_create_token) {
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.role_id = role_id;
        this.slack_account = slack_account;
        this.avatar_link = avatar_link;
        this.status = status;
        this.is_verify = is_verify;
        this.UUID = UUID;
        this.class_user = class_user;
        this.time_create_token = time_create_token;
    }

    public User(String full_name, String email, String date_of_birth, String password, int role_id, String slack_account, String avatar_link, boolean status, boolean is_verify, String UUID, String class_user, long created_time, String secret_key, String phone_number, String address, int gender, String time_create_token) {
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.role_id = role_id;
        this.slack_account = slack_account;
        this.avatar_link = avatar_link;
        this.status = status;
        this.is_verify = is_verify;
        this.UUID = UUID;
        this.class_user = class_user;
        this.created_time = created_time;
        this.secret_key = secret_key;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.time_create_token = time_create_token;
    }
    
    public User(String full_name, String email, String date_of_birth, String password,
            int role_id, String slack_account, String avatar_link, boolean status,
            boolean is_verify, String UUID, String class_user,String time_create_token, int id) {
        super(id);
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.role_id = role_id;
        this.slack_account = slack_account;
        this.avatar_link = avatar_link;
        this.status = status;
        this.is_verify = is_verify;
        this.UUID = UUID;
        this.class_user = class_user;
        this.time_create_token = time_create_token;
    }

    public User(int user_id, String full_name, String email, String date_of_birth, int role_id, String avatar_link, boolean status, String phone_number, String address, int gender, String time_create_token) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.role_id = role_id;
        this.avatar_link = avatar_link;
        this.status = status;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.time_create_token = time_create_token;
    }

    public User(String full_name, int role_id, String date_of_birth, boolean status, String phone_number, String address, int gender) {
        this.full_name = full_name;
        this.role_id = role_id;
        this.date_of_birth = date_of_birth;
        this.status = status;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
    }
    
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    
    public User(String name, java.util.Date date, String slack, String img) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getSlack_account() {
        return slack_account;
    }

    public void setSlack_account(String slack_account) {
        this.slack_account = slack_account;
    }

    public String getAvatar_link() {
        return avatar_link;
    }

    public void setAvatar_link(String avatar_link) {
        this.avatar_link = avatar_link;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIs_verify() {
        return is_verify;
    }

    public void setIs_verify(boolean is_verify) {
        this.is_verify = is_verify;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getClass_user() {
        return class_user;
    }

    public void setClass_user(String class_user) {
        this.class_user = class_user;
    }

    public String getTime_Create_Token() {
        return time_create_token;
    }

    public void setTime_Create_Token(String time_create_token) {
        this.time_create_token = time_create_token;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getTime_create_token() {
        return time_create_token;
    }

    public void setTime_create_token(String time_create_token) {
        this.time_create_token = time_create_token;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", full_name=" + full_name + ", email=" + email + ", date_of_birth=" + date_of_birth + ", password=" + password + ", role_id=" + role_id + ", slack_account=" + slack_account + ", avatar_link=" + avatar_link + ", status=" + status + ", is_verify=" + is_verify + ", UUID=" + UUID + ", class_user=" + class_user + ", created_time=" + created_time + ", secret_key=" + secret_key + ", phone_number=" + phone_number + ", address=" + address + ", gender=" + gender + ", time_create_token=" + time_create_token + '}';
    }

    

}
