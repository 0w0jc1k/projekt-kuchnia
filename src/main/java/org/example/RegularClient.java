package org.example;

public class RegularClient extends Client{
    public RegularClient(int id, String name){
        super(id, name);
    }

    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". "+getName() + " zamówił/a: " + dish.getName());
    }

    }

