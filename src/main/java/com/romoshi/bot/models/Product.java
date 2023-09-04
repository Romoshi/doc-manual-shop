package com.romoshi.bot.models;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Value("${product.imageUrl}")
    private String imageUrl;

    //@Value("${product.description}")
    private String description;

    //@Value("${product.price}")
    private int price;
}
