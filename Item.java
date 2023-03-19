
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds items that may be contained within rooms in the Zuul game.
 *
 * @author Hajar Assim 101232456
 * @version 2.00 March 12, 2023
 */
public class Item
{    
    private String description; // long description of item
    private double weight; // weight of item in kg
    private String name; // the item's name

    /**
     * Contructor that creates an item that will be contained within one of the rooms.
     * 
     * @param description The item's description.
     * @param weight The item's weight.
     * @param name The item's name.
     */
    public Item(String description, double weight, String name)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    /**
     * Returns a string representation of the item containing the weight and description and name.
     *
     * @return a complete String description of the item.
     */
    public String getDescription()
    {
        return name + ": " + description + " that weighs " + weight + "kg.";
    }
    
    /**
     * Returns the item's name.
     *
     * @return a String of the item's name.
     */
    public String getName()
    {
        return name;
    }
}
