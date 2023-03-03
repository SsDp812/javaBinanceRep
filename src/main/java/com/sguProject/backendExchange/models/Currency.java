package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Ticker should not be empty")
    @Column(name = "ticker")
    private String ticker;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;
}
