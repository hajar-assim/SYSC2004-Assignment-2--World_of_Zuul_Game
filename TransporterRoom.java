import java.util.Random;

/**
 * Class TransporterRoom is a subclass of Room. This is a special room that when it is left
 * it will transport you to a random room in the game.
 *
 * @author Hajar Assim
 * @version 2.00 March 12, 2023
 */
public class TransporterRoom extends Room
{
    // instance variables - replace the example below with your own
    private Random random;
    
    /**
     * Constructor for objects of class TransporterRoom.
     * Creates a TransporterRoom.
     */
    public TransporterRoom()
    {
        // initialise instance variables
        super("in a transporter room.");
        setExit("anywhere", null);
        random = new Random();
    }
    
    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction Ignored.
     * @return A randomly selected room.
    */
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }
    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
    */
    private Room findRandomRoom()
    {
        int randomRoomIndex = random.nextInt(Room.getRooms().size());
        return Room.getRooms().get(randomRoomIndex);
    }
}
