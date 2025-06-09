package org.example;

/**
 * Reprezentuje kucharzy odpowiedzialnych za przygotowywanie zamowien.
 * Przetwarzaja zamowienia w zaleznosci od czasu symulacji.
 */
public class Cook extends Person {
    /**
     * Konstruktor do tworzenia nowej instancji new Cook
     * @param id unikatowy identyfikator kucharza
     * @param name imie kucharza
     */
    public Cook(int id, String name) {
        super(id, name);
    }

    /**
     * Przygotowuje danie zawarte w zamowieniu
     * @param order zamowienie, ktore nalezy przygotowac
     */
    public void prepareDish(Order order) {
   if (order.getStatus() == OrderStatus.CANCELLED) {
       System.out.println(getName()+" anulowal zamowienie!");
       return;
   }
   System.out.println(getName()+" przygotowal danie: "+order.getDish().getName());
   order.setStatus(OrderStatus.READY);
    }
}
