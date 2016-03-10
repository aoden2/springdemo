package com.tdt.springdemo.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    protected long id;
    @Column(unique = true)
    protected String email;
    protected String password;
    @ManyToOne
    @JoinColumn(name = "store_id")
    protected Store store;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
