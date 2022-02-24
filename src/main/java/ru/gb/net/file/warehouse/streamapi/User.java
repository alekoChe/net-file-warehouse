package ru.gb.net.file.warehouse.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

    private final String login;
    private final List<Order> order;

    public User(String login, Order... order) {
        this.login = login;
        this.order = new ArrayList<>(Arrays.asList(order));
    }

    public String getLogin() {
        return login;
    }

    public List<Order> getOrders() {
        return order;
    }
}
