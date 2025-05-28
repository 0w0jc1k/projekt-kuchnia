package org.example;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private int waitTime;
    private int satisfactionRating;
    private ClientStatus status;
    private Order order;
    private int actualWaitTime = 0;

    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
        this.status = ClientStatus.WAITING;
    }

    public int getActualWaitTime(){
        return actualWaitTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". "+getName() + " zamówił: " + dish.getName());
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public int getSatisfactionRating() {
        return satisfactionRating;
    }

    public Order getOrder() {
        return order;
    }

    public int satisfactionCalculation() {// klient otrzymuje danie i daje ocenke
        if(waitTime >=25 && waitTime <=30) { //ocena klienta w zaleznosci od czasu oczekiwania
            this.satisfactionRating = 5;
        }else if(waitTime <=25 && waitTime >=15) {
            this.satisfactionRating = 4;
        }else if(waitTime <=15 && waitTime >=10) {
            this.satisfactionRating = 3;
        }else if(waitTime <=10 && waitTime >=5) {
            this.satisfactionRating = 2;
        }else if(waitTime <=5 && waitTime >=1) {
            this.satisfactionRating = 1;
        }else{
            this.satisfactionRating = 0;
        }
        return this.satisfactionRating;
    }

    public void updateStatus() { //update statusu klienta w zaleznosci od oczekiwania na zamowienie
        if (status == ClientStatus.SERVED || status == ClientStatus.LEFT) {
            return;
        }
        waitTime= 30 - order.getDish().getPreparationTime();
        if(waitTime<= 15 && status == ClientStatus.WAITING) {
            status = ClientStatus.IMPATIENT;
            System.out.println("Klient: "+getName()+" sie niecierpliwi!");
        }else if (waitTime <= 0) {
            status = ClientStatus.LEFT;
            satisfactionRating = 0;
            if (order != null) {
                order.setStatus(OrderStatus.CANCELLED);
            }
            System.out.println("Klient: "+getName()+" opuscil restauracje niezadowolony!");
        } else if (order != null && order.getStatus() == OrderStatus.READY) {
            status = ClientStatus.SERVED;
            actualWaitTime = order.getDish().getPreparationTime();
            satisfactionRating = satisfactionCalculation();
            System.out.println(getName() + " otrzymał zamówienie po "+actualWaitTime+" jednostkach czasu! Ocena: " + satisfactionRating);
        }
    }

}
