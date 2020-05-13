/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author NISHIMWE Elyse
 */
@Entity
@Table(name = "Account")
public class Account implements Serializable {
    @Column(name = "firstname", nullable = false, length = 20)
    private String firstname;
    @Column(name = "lastname", nullable = false,length = 20)
    private String lastname;
    @Id
    @Column(name = "username", unique = true, nullable = false,length = 10)
    private String username;
    @Column(name = "password", nullable = false,length = 8)
    private String password;
    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;
    @Column(name = "phonenumber", unique = true, nullable = false,length = 13)
    private String phonenumber;

    public Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String firstname, String lastname, String username, String password, String email, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    
    
}
