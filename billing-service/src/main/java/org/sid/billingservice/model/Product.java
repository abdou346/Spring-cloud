package org.sid.billingservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private double quantity;
}