package com.sguProject.backendExchange.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "owner")
    private Set<Coin> balances;

    public Account() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Coin> getBalances() {
        return balances;
    }

    public void setBalances(Set<Coin> balances) {
        this.balances = balances;
    }
}
