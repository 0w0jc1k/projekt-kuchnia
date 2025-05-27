package org.example;

public class Cook extends Person {


    public Cook(int id, String name) {
        super(id, name);
    }

    public void prepareDish(Order order) { //kucharz przygotowuje danie
   for(int i=0;i<=order.getDish().getPreparationTime();i++){ //symulacja czasu przygotowywania dania
       System.out.print(i);
   }
   System.out.println();
   order.setStatus(OrderStatus.READY);
    }


}
