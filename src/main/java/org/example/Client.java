package org.example;

public class Client extends Person implements ClientAction{
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

    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". "+getName() + " zamówił: " + dish.getName());
    }

    public int satisfactionCalculation() {// klient otrzymuje danie i daje ocenke
        if(actualWaitTime <= 10) {
            this.satisfactionRating=5;
        }else if(actualWaitTime <= 15) {
            this.satisfactionRating=4;
        }else if(actualWaitTime <= 20) {
            this.satisfactionRating=3;
        }else if(actualWaitTime <= 25) {
            this.satisfactionRating=2;
        }else if(actualWaitTime <= 30) {
            this.satisfactionRating=1;
        }else{
            this.satisfactionRating=0;
        }
        return this.satisfactionRating;
    }

    public void updateStatus() { //update statusu klienta w zaleznosci od oczekiwania na zamowienie
        if (status == ClientStatus.SERVED || status == ClientStatus.LEFT) {
            return;
        }
        if(order != null && order.getStatus() == OrderStatus.READY) {
            status = ClientStatus.SERVED;
            actualWaitTime = order.getActualOrderTime();
            satisfactionRating = satisfactionCalculation();
            System.out.println("Klient: " + getName() + " zostal obsluzony w czasie: " + actualWaitTime + " [jednostek czasu]");
        }else{
            actualWaitTime++; //zwieksza czas oczekiwania jesli order nie jest jeszcze gotowy, z kazdym krokiem symulacji
            //sprawdzamy czy klient sie niecierpliwi lub czy wyszedl
            if(actualWaitTime>(waitTime - 15)&& status == ClientStatus.WAITING) {
                status = ClientStatus.IMPATIENT;
                System.out.println("Klient: "+getName()+" sie niecierpliwi!");
            }
            if(actualWaitTime>= waitTime) {
                status = ClientStatus.LEFT;
                satisfactionRating = 0;
                if(order != null){
                    order.setStatus(OrderStatus.CANCELLED);
                }
                System.out.println("Klient: "+getName()+" opuscil restauracje niezadowolony!");
            }
        }
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
}
