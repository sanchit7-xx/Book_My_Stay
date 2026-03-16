import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}



class BookingValidator {
    static List<String> rooms = Arrays.asList("Single","Double","Suite");

    public static void validate(String guest,String roomType) throws InvalidBookingException {
        if(guest == null || guest.trim().isEmpty())
            throw new InvalidBookingException("Invalid guest name");

        if(!rooms.contains(roomType))
            throw new InvalidBookingException("Invalid room type");
    }
}

class BookingService {
    Map<String,Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Single",2);
        inventory.put("Double",2);
        inventory.put("Suite",1);
    }

    public Reservation book(String guest,String roomType) throws InvalidBookingException {
        BookingValidator.validate(guest,roomType);

        int available = inventory.get(roomType);
        if(available <= 0)
            throw new InvalidBookingException("Room not available");

        inventory.put(roomType,available-1);
        return new Reservation(guest,roomType);
    }
}

public class UseCase9 {
    public static void main(String[] args) {

        BookingService service = new BookingService();

        try {
            Reservation r1 = service.book("Abhi","Single");
            System.out.println("Guest: " + r1.getGuestName() + ", Room Type: " + r1.getRoomType());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Reservation r2 = service.book("Subha","Double");
            System.out.println("Guest: " + r2.getGuestName() + ", Room Type: " + r2.getRoomType());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Reservation r3 = service.book("Vamathi","Suite");
            System.out.println("Guest: " + r3.getGuestName() + ", Room Type: " + r3.getRoomType());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            service.book("Rahul","Triple");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}