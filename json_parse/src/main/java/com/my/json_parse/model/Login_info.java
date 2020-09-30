/**
 * Copyright 2020 bejson.com
 */
package com.my.json_parse.model;

/**
 * Auto-generated: 2020-09-29 20:30:43
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Login_info {

    private int status;
    private String error;
    private int first_login;
    private long user_id;
    private int is_lack;
    private int is_agree;
    private String nick_name;
    private User_info user_info;
    private int new_level;
    private int login_send_score;
    private String applozic_password;
    private String _time_stamp;
    private String act;
    private String ctl;
    private int code;
    private String msg;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setFirst_login(int first_login) {
        this.first_login = first_login;
    }

    public int getFirst_login() {
        return first_login;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setIs_lack(int is_lack) {
        this.is_lack = is_lack;
    }

    public int getIs_lack() {
        return is_lack;
    }

    public void setIs_agree(int is_agree) {
        this.is_agree = is_agree;
    }

    public int getIs_agree() {
        return is_agree;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }

    public User_info getUser_info() {
        return user_info;
    }

    public void setNew_level(int new_level) {
        this.new_level = new_level;
    }

    public int getNew_level() {
        return new_level;
    }

    public void setLogin_send_score(int login_send_score) {
        this.login_send_score = login_send_score;
    }

    public int getLogin_send_score() {
        return login_send_score;
    }

    public void setApplozic_password(String applozic_password) {
        this.applozic_password = applozic_password;
    }

    public String getApplozic_password() {
        return applozic_password;
    }

    public void set_time_stamp(String _time_stamp) {
        this._time_stamp = _time_stamp;
    }

    public String get_time_stamp() {
        return _time_stamp;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getAct() {
        return act;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }

    public String getCtl() {
        return ctl;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Login_info{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", first_login=" + first_login +
                ", user_id=" + user_id +
                ", is_lack=" + is_lack +
                ", is_agree=" + is_agree +
                ", nick_name='" + nick_name + '\'' +
                ", user_info=" + user_info +
                ", new_level=" + new_level +
                ", login_send_score=" + login_send_score +
                ", applozic_password='" + applozic_password + '\'' +
                ", _time_stamp='" + _time_stamp + '\'' +
                ", act='" + act + '\'' +
                ", ctl='" + ctl + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}