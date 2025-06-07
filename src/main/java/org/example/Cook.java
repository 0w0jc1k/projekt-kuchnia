package org.example;

public class Cook extends Person {

    public Cook(int id, String name) {
        super(id, name);
    }

    public void prepareDish(Order order) {//kucharz przygotowuje danie
   if (order.getStatus() == OrderStatus.CANCELLED) {
       System.out.println(getName()+" anulowal zamowienie!");
       return;
   }
   System.out.println(getName()+" przygotowal danie: "+order.getDish().getName());
   order.setStatus(OrderStatus.READY);
    }
}
