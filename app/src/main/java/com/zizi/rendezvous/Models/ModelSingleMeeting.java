package com.zizi.rendezvous.Models;

import com.google.firebase.firestore.ServerTimestamp;
import com.zizi.rendezvous.Data.Data;

import java.util.ArrayList;
import java.util.Date;

public class ModelSingleMeeting {

    //!обратить внимание на регистр, далее этот класс используется при читке и записи с БД,
    //!везде регистр сделал маленькими, заработало, пока дальше не стал разбираться
    //!Переменные с нижним подчеркиванием из БД почему то не выбираются
    private String userID;
    private String tokenDevice;

    private String name;
    private String gender;
    private String age;
    private String phone;
    private String onlyWrite;
    private String socNet;
    private String contact;
    private String gender_partner;
    private String age_min;
    private String age_max;
    private String region;
    private String town;
    private String placeAnyPlace;
    private ArrayList<String> placeArray;
    private String placeOtherDescription;
    private String time;
    private String comment;

    @ServerTimestamp //если поле null, то в БД будет писаться метка времени FareStore, если задать, то заданная
    private Date timeStamp;



    public ModelSingleMeeting() {}

    public String getUserID() {
        return userID;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getOnlyWrite() {
        return onlyWrite;
    }

    public String getSocNet() {
        return socNet;
    }

    public String getContact() {
        return contact;
    }

    public String getGender_partner() {
        return gender_partner;
    }

    public String getAge_min() {
        return age_min;
    }

    public String getAge_max() {
        return age_max;
    }

    public String getRegion() {
        return region;
    }

    public String getTown() {
        return town;
    }

    public String getPlaceAnyPlace() {
        return placeAnyPlace;
    }

    public ArrayList<String> getPlaceArray() {
        return placeArray;
    }

    public String getPlaceOtherDescription() {
        return placeOtherDescription;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }



    //Setters///////////////////////////////////////////////////////////////////////////////////////
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTokenDevice(String token) {
        this.tokenDevice = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOnlyWrite(String onlyWrite) {
        this.onlyWrite = onlyWrite;
    }

    public void setSocNet(String socNet) {
        this.socNet = socNet;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setGender_partner(String gender_partner) {
        this.gender_partner = gender_partner;
    }

    public void setAge_min(String age_min) {
        this.age_min = age_min;
    }

    public void setAge_max(String age_max) {
        this.age_max = age_max;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setPlaceAnyPlace(String placeAnyPlace) {
        this.placeAnyPlace = placeAnyPlace;
    }

    public void setPlaceArray(ArrayList<String> placeArray) {
        this.placeArray = placeArray;
    }

    public void setPlaceOtherDescription(String placeOtherDescription) {
        this.placeOtherDescription = placeOtherDescription;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String CreateStringFromArrayListPlaces () {

        String tmpStr = "";

        // если любое место, то так и пишем, если нет, то перечисляем все выбранные
        if(placeAnyPlace.equals(Data.ANY_PLACE)){
            tmpStr = Data.ANY_PLACE;
        } else { // не выбрано, что встреча в любом месте

            for (String place : placeArray) { // пробегаемся по массиву с местами
                if (!place.equals("")) { //если строка не пустая, то приплюсовываем к выбранным местам
                    tmpStr += "\n- " + place;
                }

                if (place.equals(Data.OTHER)){ //если место Прочее, то дописать какое именно

                    tmpStr += ": " + placeOtherDescription;
                }
            }

            if (!tmpStr.equals("")){ // если строка после цикла не пустая, то есть есть какие-то выбранные места
                tmpStr = "Выбранные места: " + tmpStr;
            }
        }

        return tmpStr;
    }

}
