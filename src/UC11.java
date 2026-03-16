import java.util.*;

class BookingProcessor extends Thread {
    BookingService service;
    String guest;
    String roomType;

    public BookingProcessor(BookingService service, String guest, String roomType) {
        this.service = service;
        this.guest = guest;
        this.roomType = roomType;
    }

    public void run() {
        synchronized(service) {
            try {
                Reservation r = service.book(guest, roomType);
                System.out.println("Booking confirmed: " + r.getGuestName() + ", Room Type: " + r.getRoomType());
            } catch (Exception e) {
                System.out.println("Booking failed for " + guest + ": " + e.getMessage());
            }
        }
    }
}

public class UseCase11 {
    public static void main(String[] args) {

        BookingService service = new BookingService();

        BookingProcessor t1 = new BookingProcessor(service, "Abhi", "Single");
        BookingProcessor t2 = new BookingProcessor(service, "Subha", "Single");
        BookingProcessor t3 = new BookingProcessor(service, "Vamathi", "Single");
        BookingProcessor t4 = new BookingProcessor(service, "Rahul", "Double");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}