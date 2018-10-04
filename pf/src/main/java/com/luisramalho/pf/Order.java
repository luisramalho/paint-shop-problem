package com.luisramalho.pf;

import java.util.ArrayList;

/**
 * A class representing an order.
 *
 * Created by Luís Ramalho on 19/11/16.
 * <info@luisramalho.com>
 */

class Order {
    private int id;
    private int numberOfPaintColors;
    private int numberOfCustomers;
    private ArrayList<Customer> customers;

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id + 1;
    }

    int getNumberOfPaintColors() {
        return numberOfPaintColors;
    }

    void setNumberOfPaintColors(String numberOfPaintColors) {
        this.numberOfPaintColors = Integer.parseInt(numberOfPaintColors);
    }

    int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    void setNumberOfCustomers(String numberOfCustomers) {
        this.numberOfCustomers = Integer.parseInt(numberOfCustomers);
    }

    ArrayList<Customer> getCustomers() {
        return customers;
    }

    void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}
