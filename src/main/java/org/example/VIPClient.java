package org.example;

public class VIPClient extends Client{
    public VIPClient(int id, String name) {
        super(id, name);
    }

    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". [VIP] "+getName() + " zamówił/a: " + dish.getName());
        kitchen.getOrders().remove(this.order);//vipowie maja priorytet w przygotowaniu zamowienia
        kitchen.getOrders().add(0,this.order);
    }
}
