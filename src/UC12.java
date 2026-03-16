import java.io.*;
import java.util.*;

class PersistenceService {

    public void save(String file, BookingService service) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(service.inventory);
            out.close();
            System.out.println("System state saved");
        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    public void load(String file, BookingService service) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            service.inventory = (Map<String,Integer>) in.readObject();
            in.close();
            System.out.println("System state restored");
        } catch (Exception e) {
            System.out.println("No previous state found, starting fresh");
        }
    }
}

public class UseCase12 {
    public static void main(String[] args) {

        BookingService service = new BookingService();
        PersistenceService persistence = new PersistenceService();

        persistence.load("booking.dat", service);

        try {
            Reservation r1 = service.book("Abhi","Single");
            Reservation r2 = service.book("Subha","Double");

            System.out.println("Guest: " + r1.getGuestName() + ", Room Type: " + r1.getRoomType());
            System.out.println("Guest: " + r2.getGuestName() + ", Room Type: " + r2.getRoomType());

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        persistence.save("booking.dat", service);
    }
}