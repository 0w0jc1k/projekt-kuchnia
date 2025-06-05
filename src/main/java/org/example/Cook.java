package org.example;

public class Cook extends Person {


    public Cook(int id, String name) {
        super(id, name);
    }

    public void prepareDish(Order order) {//kucharz przygotowuje danie
   if (order.getStatus() == OrderStatus.CANCELLED) {
       System.out.println("Anulowano zamowienie!");
       return;
   }

   System.out.println();
   order.setStatus(OrderStatus.READY);
    }


}
