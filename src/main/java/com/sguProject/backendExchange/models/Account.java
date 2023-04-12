package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

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
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Balance> balances;

    public Account() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Balance> getBalances() {
        return balances;
    }

    public void setBalances(Set<Balance> balances) {
        this.balances = balances;
    }

    public void addBalance(Balance balance) {
        if (balances == null)
            balances = new HashSet<>();

        balances.add(balance);
    }
}
