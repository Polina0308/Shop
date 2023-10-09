package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orderDetails")
public class OrderDetails {
    private static  final String SEQ_NAME = "orderDetail_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private  Product product;
    private  double amount;
    private  double price;
}
