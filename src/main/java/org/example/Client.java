package org.example;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private int waitTime;
    private int satisfactionRating;
    private ClientStatus status;
    private Order order;

    public Client(int id, String name) { //konstruktor do tworzenia klientów
        super(id, name);
        this.waitTime = 30;
        this.status = ClientStatus.WAITING;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie u kuchni
        this.order = new Order(this,dish,1);
        kitchen.addOrder(this.order);
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setSatisfactionRating(int rating) { //ustala stopien zadowolenia klienta
        if (rating >= 1 && rating <= 5) {
            this.satisfactionRating = rating;
        }
    }

    public int getSatisfactionRating() {
        return satisfactionRating;
    }

    public Order getOrder() {
        return order;
    }

    public void receiveDish() {// klient otrzymuje danie
        this.status = ClientStatus.SERVED;
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
    }

    public void updateStatus() { //klient wychodzi jesli nie otrzymal zamowienia
    this.waitTime--;
    if(this.waitTime <= 0 && this.status != ClientStatus.SERVED) {
        this.status = ClientStatus.LEFT;
        }
    }

}
