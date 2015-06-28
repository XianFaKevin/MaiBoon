package com.orange.maiboon;

import org.w3c.dom.Text;

import java.sql.Date;

/**
 * Created by Kevin on 28/6/2015.
 */
public class Profile {
    Date date;
    int contact;
    Date in;
    Date out;
    int duration;
    int people;
    String room;
    int price;
    boolean paid;
    boolean accounted;
    Text remarks;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getIn() {
        return in;
    }

    public void setIn(Date in) {
        this.in = in;
    }

    public Date getOut() {
        return out;
    }

    public void setOut(Date out) {
        this.out = out;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isAccounted() {
        return accounted;
    }

    public void setAccounted(boolean accounted) {
        this.accounted = accounted;
    }

    public Text getRemarks() {
        return remarks;
    }

    public void setRemarks(Text remarks) {
        this.remarks = remarks;
    }
}
