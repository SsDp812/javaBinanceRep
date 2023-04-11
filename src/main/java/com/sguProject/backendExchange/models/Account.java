package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Username should not be empty")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 5, max = 30, message = "Password should be between 5 and 30 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Balance> balances;

    public Account() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addBalance(Balance balance) {
        if (balances == null)
            balances = new ArrayList<>();

        balances.add(balance);
    }
}
