package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class Order {

    private String userId;
    private String orderId;
    private BigDecimal value;

    public Order(String userId, String orderId, BigDecimal value){
        this.userId = userId;
        this.orderId = orderId;
        this.value = value;
    }
}
