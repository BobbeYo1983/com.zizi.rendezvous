package com.zizi.rendezvous.Models;

/**
 * Класс профайла пользователя
 */
public class ModelUser {

    //private String id; //идентификатор пользователя firebase
    private String email; //email пользователя
    private String userID; //идентификатор пользователя firebase
    private String tokenDevice; //уникальный ключ девайса
    private String screenExtension; //расширение экрана
    private String countRequestMeetings; // оставшееся количество бесплатных заявок
    private boolean acceptRules; //принял ли пользователь правила

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public String getScreenExtension() {
        return screenExtension;
    }

    public void setScreenExtension(String screenExtension) {
        this.screenExtension = screenExtension;
    }


/*    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/



    public String getCountRequestMeetings() {
        return countRequestMeetings;
    }

    public void setCountRequestMeetings(String countRequestMeetings) {
        this.countRequestMeetings = countRequestMeetings;
    }

    public boolean isAcceptRules() {
        return acceptRules;
    }

    public void setAcceptRules(boolean acceptRules) {
        this.acceptRules = acceptRules;
    }


}
