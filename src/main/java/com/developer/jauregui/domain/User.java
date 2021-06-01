package com.developer.jauregui.domain;

import java.io.Serializable;

public class User implements Serializable {

    private String vUserID;
    private Integer vRole;
    private String vEmail;
    private String vPwd;
    private Integer vStatus;
    private String vToken;

    //Metodo para crear un usuario
    public User(String vUserID,Integer vRole,String vEmail,String vPwd,Integer vStatus){
        this.vUserID = vUserID;
        this.vRole = vRole;
        this.vEmail = vEmail;
        this.vPwd = vPwd;
        this.vStatus = vStatus;
    }

    //Metodo para autenticar un usuario
    public User(String vUserID,Integer vRole,String vEmail,String vPwd,Integer vStatus,String vToken){
        this.vUserID = vUserID;
        this.vRole = vRole;
        this.vEmail = vEmail;
        this.vPwd = vPwd;
        this.vStatus = vStatus;
        this.vToken = vToken;
    }

    public Integer getvRole() {
        return vRole;
    }

    public String getvToken() {
        return vToken;
    }

    public String getvUserID() {
        return vUserID;
    }

    public void setvUserID(String vUserID) {
        this.vUserID = vUserID;
    }


    public String getvEmail() {
        return vEmail;
    }

    public void setvEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public String getvPwd() {
        return vPwd;
    }

    public void setvPwd(String vPwd) {
        this.vPwd = vPwd;
    }


    public Integer getvStatus() {
        return vStatus;
    }

    public void setvStatus(Integer vStatus) {
        this.vStatus = vStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "vUserID='" + vUserID + '\'' +
                ", vRole='" + vRole + '\'' +
                ", vEmail='" + vEmail + '\'' +
                ", vPwd='" + vPwd + '\'' +
                ", vStatus='" + vStatus + '\'' +
                '}';
    }
}
