package com.romoshi.bot.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Value("${product.imageUrl}")
    private String imageUrl;

    @Column(nullable = false)
    @Value("${product.description}")
    private String description;

    @Column(nullable = false)
    @Value("${product.price}")
    private int price;
}
