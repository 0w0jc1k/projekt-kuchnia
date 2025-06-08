package org.example;

public class Order {
    private Client client;
    private Dish dish;
    private int id;
    private OrderStatus status;
    private int actualOrderTime; //czas mijajacy od zlozenia zamowienia
    private int preparationProgress;

    public Order(Client client, Dish dish, int id) {
        this.client = client;
        this.dish = dish;
        this.id = id;
        this.status = OrderStatus.PENDING;
        this.client.setOrder(this);
        this.actualOrderTime = 1;
        this.preparationProgress = 0;
    }

    public Client getClient() {
        return client;
    }

    public Dish getDish() {

        return dish;
    }

    public int getId() {

        return id;
    }

    public OrderStatus getStatus() {

        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getActualOrderTime() {
        return actualOrderTime;
    }

    public void incrementActualOrderTime(){
        actualOrderTime++;
    }

    public int getPreparationProgress() {
        return preparationProgress;
    }

    public void incrementPreparationTime(){
        preparationProgress++;
    }
}
