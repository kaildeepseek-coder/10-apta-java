import java.util.ArrayList;
import java.util.List;
 
interface Bookable {
    void bookTicket(Ticket ticket);
}
 
interface Payable {
    void pay(double amount);
}
 
class Movie {
    private String title;
    private String genre;
    private int duration;
 
    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void showInfo() {
        System.out.println("Фильм: " + title);
        System.out.println("Жанры: " + genre);
        System.out.println("Ұзақтығы: " + duration + " минут");
    }
}
 
class Session {
    private Movie movie;
    private String time;
    private double price;
    private int availableSeats;
 
    public Session(Movie movie, String time, double price, int availableSeats) {
        this.movie = movie;
        this.time = time;
        this.price = price;
        this.availableSeats = availableSeats;
    }
 
    public Movie getMovie() {
        return movie;
    }
 
    public double getPrice() {
        return price;
    }
 
    public int getAvailableSeats() {
        return availableSeats;
    }
 
    public void decreaseSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }
 
    public void showSession() {
        System.out.println("Фильм: " + movie.getTitle());
        System.out.println("Уақыты: " + time);
        System.out.println("Бағасы: " + price + " тг");
        System.out.println("Бос орындар: " + availableSeats);
    }
}
 
class Customer {
    private String name;
 
    public Customer(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
}
 
class Ticket {
    private Customer customer;
    private Session session;
 
    public Ticket(Customer customer, Session session) {
        this.customer = customer;
        this.session = session;
    }
 
    public Customer getCustomer() {
        return customer;
    }
 
    public Session getSession() {
        return session;
    }
 
    public void showTicket() {
        System.out.println("Клиент: " + customer.getName());
        System.out.println("Фильм: " + session.getMovie().getTitle());
        System.out.println("Билет бағасы: " + session.getPrice() + " тг");
    }
}
 
class Cinema implements Bookable {
    private String name;
    private List<Session> sessions = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
 
    public Cinema(String name) {
        this.name = name;
    }
 
    public void addSession(Session session) {
        sessions.add(session);
    }
 
    public void showSessions() {
        System.out.println("\\nКинотеатр: " + name);
 
        for (Session session : sessions) {
            session.showSession();
            System.out.println("----------------");
        }
    }
 
    @Override
    public void bookTicket(Ticket ticket) {
        Session session = ticket.getSession();
 
        if (sessions.contains(session) && session.getAvailableSeats() > 0) {
            session.decreaseSeat();
            tickets.add(ticket);
            System.out.println("Билет сәтті брондалды!");
        } else {
            System.out.println("Бос орын жоқ немесе сеанс табылмады!");
        }
    }
 
    public void showTickets() {
        System.out.println("\\nБрондалған билеттер:");
 
        for (Ticket ticket : tickets) {
            ticket.showTicket();
            System.out.println("----------------");
        }
    }
}
 
class CashPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.println("Қолма-қол төлем: " + amount + " тг");
        System.out.println("Төлем сәтті орындалды!");
    }
}
 
class CardPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.println("Картамен төлем: " + amount + " тг");
        System.out.println("Төлем сәтті орындалды!");
    }
}
 
class PaymentService {
    private Payable payment;
 
    public PaymentService(Payable payment) {
        this.payment = payment;
    }
 
    public void makePayment(double amount) {
        payment.pay(amount);
    }
}
 
public class Main {
    public static void main(String[] args) {
 
        Movie movie1 = new Movie("Аватар", "Фантастика", 180);
        Movie movie2 = new Movie("Титаник", "Драма", 195);
 
        Session session1 = new Session(movie1, "14:00", 2500, 30);
        Session session2 = new Session(movie2, "18:00", 3000, 20);
 
        Cinema cinema = new Cinema("Star Cinema");
 
        cinema.addSession(session1);
        cinema.addSession(session2);
 
        cinema.showSessions();
 
        Customer customer1 = new Customer("Али");
        Customer customer2 = new Customer("Аружан");
 
        Ticket ticket1 = new Ticket(customer1, session1);
        Ticket ticket2 = new Ticket(customer2, session2);
 
        cinema.bookTicket(ticket1);
        cinema.bookTicket(ticket2);
 
        cinema.showTickets();
 
        System.out.println("\\nТөлем жасау:");
 
        PaymentService payment1 = new PaymentService(new CashPayment());
        payment1.makePayment(session1.getPrice());
 
        PaymentService payment2 = new PaymentService(new CardPayment());
        payment2.makePayment(session2.getPrice());
 
        System.out.println("\\nҚалған бос орындар:");
        cinema.showSessions();
 
        System.out.println("Бағдарлама аяқталды!");
    }
}
