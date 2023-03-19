
/**
 * The class Beamer is a subclass of class Item. A beamer is an item a Player
 * can carry, if it is charged, it will memorize the room. If it is fired it 
 * will transport the player back to the room it was charged in.
 *
 * @author Hajar Assim
 * @version 2.00 March 12, 2023
 */
public class Beamer extends Item
{
    // stores the charged room
    private Room charged;

    /**
     * Constructor for objects of class Beamer. Initializes a beamer, all beamers are identical.
     */
    public Beamer()
    {
        super("a wonderful Beamer", 1.0, "beamer");
    }

    /**
     * Charges the Beamer by setting the charge to the current room.
     * 
     * @param room The current room the Beamer was in when charged.
     */
    public void charge(Room room)
    {
        charged = room;
    }
    
    /**
     * Returns true if the beamer is charged, if not, it returns false.
     * 
     * @return True if the beamer is charged, otherwise returns false.
     */
    public boolean isCharged()
    {
        if (charged != null){
            return true;
        }

        return false;
    }
    
    /**
     * Fires the beamer, which returns the room that it charged in order to allow
     * the Player to return to the charged room using the beamer.
     * 
     * @return The room that the beamer had charged. If beamer hasn't been charged, will return null.
     */
    public Room fire()
    {
        Room roomFromCharged = charged;
        charged = null;
        return roomFromCharged;
    }
}
