import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRooms;
    private Item itemHeld;
    private int numItemsBeforeFood;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRooms = new Stack<Room>();
        previousRoom = null;
        itemHeld = null;
        numItemsBeforeFood = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        TransporterRoom transporter;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter = new TransporterRoom();
        
        // create the items
        Item firTree1 = new Item("a fir tree", 500.5, "tree");
        Item firTree2 = new Item("a fir tree", 500.5, "tree");
        Item woodenChair = new Item("a wooden chair", 5.0, "chair");
        Item woodenChair2 = new Item("a wooden chair", 5.0, "chair");
        Item computer1 = new Item("a lab computer", 2.0, "computer");
        Item computer2 = new Item("a lab computer", 2.0, "computer");
        Item computer3 = new Item("a lab computer", 2.0, "computer");
        Item pen = new Item("a ballpoint pen", 0.04, "pen");
        Item beerGlass = new Item("a beer glass", 0.5, "beer");
        Item stool1 = new Item("a wooden stool", 2.0, "stool");
        Item stool2 = new Item("a wooden stool", 2.0, "stool");
        Item officePlant = new Item("an office plant", 1.0, "plant");
        Beamer beamer1 = new Beamer();
        Beamer beamer2 = new Beamer();
        
        Item cookie1 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        Item cookie2 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        Item cookie3 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        Item cookie4 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        Item cookie5 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        Item cookie6 = new Item("a yummy chocolate chip cookie", 0.2, "cookie");
        
        // initialise room items
        outside.addItem(firTree1);
        outside.addItem(firTree2);
        
        theatre.addItem(woodenChair);
        theatre.addItem(cookie4);
        theatre.addItem(cookie5);
        theatre.addItem(beamer1);
        
        pub.addItem(beerGlass);
        pub.addItem(stool1);
        pub.addItem(stool2);
        pub.addItem(cookie1);
        pub.addItem(cookie2);
        
        lab.addItem(computer1);
        lab.addItem(computer2);
        lab.addItem(computer3);
        lab.addItem(beamer2);
        
        office.addItem(pen);
        office.addItem(officePlant);
        office.addItem(cookie3);
        
        transporter.addItem(cookie6);
        transporter.addItem(woodenChair2);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);
        theatre.setExit("north", transporter);
        
        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomAndCarry();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        } 
        else if (commandWord.equals("take")) {
            take(command);
        } 
        else if (commandWord.equals("drop")){
            drop(command);
        }
        else if (commandWord.equals("charge")){
            charge(command);
        }
        else if (commandWord.equals("fire")){
            fire(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom; // make the previous room the room we were just in
            previousRooms.push(previousRoom); // add previous room to the stack
            currentRoom = nextRoom;
            printRoomAndCarry();
        }
    }
    
    /** 
     * "look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("Look where?");
            return;
        } else {
            // prints the long description of the current room we are in
            printRoomAndCarry();
        }
    }
    
    /** 
     * "eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("Eat what?");
            return;
        } else {
            // prints a response to let us know we have successfully eaten
            if (itemHeld.getName().equals("cookie")){
                System.out.println("You have eaten and are no longer hungry.");
                itemHeld = null;
                numItemsBeforeFood = 5;
            }
            else {
                System.out.println("You are not carrying a cookie.");
            }
        }
    }
    
    /** 
     * "back" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("Back what?");
            return;
        } else {
            // if you are the beginning of the game, you cannot go back
            if (previousRoom == null) {
                System.out.println("No room to go back to.");                
            } else {
                // the back command takes you to the previous room that you visited
                // prints a response to let us know we have successfully gone back
                Room tempRoom = currentRoom; // storing the current room
                currentRoom = previousRoom; // changing the current room to the previous room
                previousRoom = tempRoom; // making the previousroom to the room we were just in
                previousRooms.push(previousRoom); // pushing the previous room to the stack
                System.out.println("You have gone back to the previous room."); // msg to indicate we have moved rooms
                printRoomAndCarry(); // print the description of the current room we are in and items being carried
                
            }
        }
    }
    
    /** 
     * "stackBack" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("stackBack what?");
            return;
        } else {
            // if you are the beginning of the game, you cannot go back
            if (previousRooms.empty()) {
                System.out.println("No room to go back to.");                
            } else {
                // the back command takes you to the previous room that you visited
                // prints a response to let us know we have successfully gone back
                currentRoom = previousRooms.pop();
                System.out.println("You have gone back to the previous room.");
                printRoomAndCarry();
            }
        }
    }
    
    /** 
     * "take" was entered. Check the rest of the command to see what we want to take. 
     * Once we take an item, it is removed from the room and the player is now carrying
     * it.
     * 
     * If we are not carrying an item, we can take an item from the current room.
     * 
     * @param command The command to be processed
     */
    private void take(Command command) {
        if(!command.hasSecondWord()) {
            // has no second word.
            System.out.println("take what?");
            return;
        } 
        String itemName = command.getSecondWord();
        // if the player is already holding onto an item
        if (itemHeld != null){
            System.out.println("You are already holding something.");
            return;
        }
        
        // the player has not eaten yet
        if (numItemsBeforeFood <= 0 && !itemName.equals("cookie")) {
            System.out.println("You must take and eat a cookie before taking anything else.");
            return;
        }
        
        itemHeld = currentRoom.removeItem(itemName);
        // no such item in the room
        if (itemHeld == null){
            System.out.println("That item is not in the room.");
            return;
        } else {
        // if the item exists
            System.out.println("You have picked up " + itemHeld.getName() + ".");
            numItemsBeforeFood --;
            return;
        }
    }
    
    /** 
     * "drop" was entered. Check the rest of the command to see
     * whether we really want to drop.
     * 
     * If we are carrying an item, we may drop it.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("drop what?");
            return;
        }
        // nothing being held
        if (itemHeld == null){
            System.out.println("You have nothing to drop.");
            return;
        } else {
            currentRoom.addItem(itemHeld);
            System.out.println("You have dropped " + itemHeld.getName() + ".");
            itemHeld = null;
        }
    }
    
    /** 
     * "charge" was entered. Check the rest of the command to see
     * whether we really want to charge.
     * 
     * If we are carrying an uncharged beamer, we can charge it.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("charge what?");
            return;
        }

        if(!itemHeld.getName().equals("beamer") || itemHeld == null){
            System.out.println("You are currently not holding a beamer.");
        } else {
            Beamer beamerHeld = (Beamer) itemHeld;
            if (beamerHeld.isCharged()) {
                System.out.println("The beamer is already charged.");
            } else {
                beamerHeld.charge(currentRoom);
                System.out.println("beamer successfully charged.");
            }
        }
    }
    
    /** 
     * "fire" was entered. Check the rest of the command to see
     * whether we really want to fire.
     * 
     * If we are carrying a charged beamer, it will fire.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command) {
        if(command.hasSecondWord()) {
            // has no second word.
            System.out.println("fire what?");
            return;
        }

        if(!itemHeld.getName().equals("beamer") || itemHeld == null){
            System.out.println("You are currently not holding a beamer.");
        } else {
            Beamer beamerHeld = (Beamer) itemHeld;
            if (!beamerHeld.isCharged()) {
                System.out.println("The beamer is not charged.");
            } else {
                previousRoom = currentRoom; // make the previous room the room we were just in
                previousRooms.push(previousRoom); // add previous room to the stack
                currentRoom = beamerHeld.fire(); // take us to the room the beamer stored
                System.out.println("beamer successfully fired.");
                printRoomAndCarry();
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * Prints a description of the room including the items within it, as well as the item we are carrying.
     */
    private void printRoomAndCarry(){
        System.out.println(currentRoom.getLongDescription());
        if (itemHeld == null) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.println("You are carrying:");
            System.out.println("     "+ itemHeld.getDescription());
        }
    }
}
