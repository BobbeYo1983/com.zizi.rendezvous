package com.zizi.rendezvous;

/**
 * Класс профайла пользователя
 */
public class ModelUser {

    private int countRequestMeetings; // оставшееся количество бесплатных заявок
    private String id; //идентификатор пользователя firebase

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCountRequestMeetings() {
        return countRequestMeetings;
    }

    public void setCountRequestMeetings(int countRequestMeetings) {
        this.countRequestMeetings = countRequestMeetings;
    }



}
