package com.trip.expensemanager;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * An entity for messsages sent from the web console to the registered devices
 *
 * Its associated endpoint, MessageEndpoint.java, was NOT automatically generated 
 * from this class. While it is easy to generate endpoints automatically, you can
 * write an endpoint manually without generating it. You still need to generate
 * the associated client library from the endpoint when changes are made.
 *
 * For more information on endpoints, see
 * http://developers.google.com/eclipse/docs/cloud_endpoints.
 */

@Entity
public class MessageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date creationDate;

    private String token;

    private String mobNo;

    public Long getId(){
        return this.id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

}
