import java.util.*;

class CancellationService {
    Stack<String> rollbackStack = new Stack<>();
    Map<String, Reservation> bookings = new HashMap<>();

    public void confirmBooking(String roomId, Reservation reservation) {
        bookings.put(roomId, reservation);
    }

    public void cancel(String roomId, BookingService service) {
        Reservation r = bookings.get(roomId);

        if (r == null) {
            System.out.println("Cancellation failed for Room ID: " + roomId);
            return;
        }

        rollbackStack.push(roomId);

        String type = r.getRoomType();
        service.inventory.put(type, service.inventory.get(type) + 1);

        bookings.remove(roomId);

        System.out.println("Cancelled booking for " + r.getGuestName() + ", Room Type: " + type);
    }
}

public class UseCase10 {
    public static void main(String[] args) {

        BookingService service = new BookingService();
        CancellationService cancelService = new CancellationService();

        try {
            Reservation r1 = service.book("Abhi", "Single");
            Reservation r2 = service.book("Subha", "Double");
            Reservation r3 = service.book("Vamathi", "Suite");

            cancelService.confirmBooking("R1", r1);
            cancelService.confirmBooking("R2", r2);
            cancelService.confirmBooking("R3", r3);

            System.out.println("Guest: " + r1.getGuestName() + ", Room Type: " + r1.getRoomType());
            System.out.println("Guest: " + r2.getGuestName() + ", Room Type: " + r2.getRoomType());
            System.out.println("Guest: " + r3.getGuestName() + ", Room Type: " + r3.getRoomType());

            cancelService.cancel("R2", service);
            cancelService.cancel("X1", service);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}