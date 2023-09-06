package com.romoshi.bot.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
    @ColumnDefault("https://ibb.co/QQ0SCMn")
    private String imageUrl;

    @Column(nullable = false)
    @ColumnDefault("Description.")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("100")
    private int price;
}
