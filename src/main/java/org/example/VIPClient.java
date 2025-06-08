package org.example;

public class VIPClient extends Client{
    public VIPClient(int id, String name) {
        super(id, name);
    }

    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addVipOrder(this.order);
        System.out.println(getId()+". [VIP] "+getName() + " zamówił/a: " + dish.getName());
    }

}
