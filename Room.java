import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version October 21, 2012
 * 
 * @author Hajar Assim 101232456
 * @version 2.00 March 12, 2023
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;            // stores the items of this room.
    static private ArrayList<Room> rooms = new ArrayList<Room>();   // stores all the rooms.
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard". Adds this current room to the ArrayList of rooms.
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        rooms.add(this); // adds the current room
    }
    
    /**
     * Adds an item to the room.
     * 
     * @param item The item to be added to the room.
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items:
     *              a fir tree that weighs 500.5kg.
     *              a fir tree that weighs 500.5kg.
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Prints a line of strings describing the room's items, for example
     * "Items: 
     *      a fir tree that weighs 500.5kg.
     *      a wooden chair that weighs 5.0kg.
     */
    private String getItems()
    {
        String returnString = "Items:";
        for(Item item : items) {
            returnString += "\n     " + item.getDescription();
        }
        return returnString;
    }
    

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Return the item that is removed from the current room.
     * If there is no item in the room, return null.
     * 
     * @param itemRemove The item to be removed within the room.
     * @return The item that was removed from the room.
     */
    public Item removeItem(String itemRemove) 
    {
        for (Item item:items) {
            if (item.getName().equals(itemRemove)){
                Item removed = item;
                items.remove(item);
                return removed;
            }
        }
        return null;
    }
    
    /**
     * Static method that returns the ArrayList of rooms.
     * 
     * @return An ArrayList<Room> of all the current rooms.
     */
    public static ArrayList<Room> getRooms(){
        return rooms;
    }
}

