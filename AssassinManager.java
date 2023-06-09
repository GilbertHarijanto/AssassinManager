/**@Gilbert CS145 AssassinManager
 *@version 1.0 (06/04/2023)
 *@see AssassinManager class*/
import java.util.*;

public class AssassinManager {
   private AssassinNode killRing;
   private AssassinNode graveyard;

   /*Constructor to initialize the killRing using a list of names.
   @param names The list of names representing the initial players.
   @throws IllegalArgumentException if the provided list of names is empty.
   */
   public AssassinManager(List < String > names) {
      if (names.isEmpty()) throw new IllegalArgumentException();

      killRing = new AssassinNode(names.get(0));
      AssassinNode person = killRing;
      for (int i = 1; i < names.size(); i++) {
         person.next = new AssassinNode(names.get(i));
         person = person.next;
      }
   }

   public void printKillRing() {
      AssassinNode person = killRing;
      while (person != null) {
         String stalkedPerson;
         if (person.next != null) {
            stalkedPerson = person.next.name;
         } else {
            stalkedPerson = killRing.name;
         }
         System.out.println("    " + person.name + " is stalking " + stalkedPerson);
         person = person.next;
      }
   }

   public void printGraveyard() {
      AssassinNode person = graveyard;
      while (person != null) {
         System.out.println("    " + person.name + " was killed by " + person.killer);
         person = person.next;
      }
   }

   /*Checks if the kill ring contains a specific player.
   @param name The name of the player to check.
   @return True if the player is in the kill ring, otherwise returns false.
   */
   public boolean killRingContains(String name) {
      return contains(killRing, name);
   }
   /*Checks if the graveyard contains a specific player.
   @param name The name of the player to check.
   @return True if the player is in the graveyard, otherwise returns false.
   */
   public boolean graveyardContains(String name) {
      return contains(graveyard, name);
   }

   /*Checks if the game is over.
   @return True if the game is over, otherwise returns false.
   */
   public boolean gameOver() {
      return killRing.next == null;
   }

   /*Returns the name of the winner of the game.
   @return The name of the winner, if the game is over, returns null if the game is not yet over.
   */
   public String winner() {
      if (gameOver()) {
         return killRing.name;
      } else {
         return null;
      }
   }

   /*Simulates a kill in the game, removes a player from the kill ring and moves them to the graveyard.
   @param name The name of the player to be killed.
   @throws IllegalStateException if the game is over.
   @throws IllegalArgumentException if the specified player does not exist in the kill ring.
   */
   public void kill(String name) {
      if (gameOver()) throw new IllegalStateException();
      if (!killRingContains(name)) throw new IllegalArgumentException();

      AssassinNode person = killRing;
      AssassinNode prevPerson = null;

      if (person.name.equalsIgnoreCase(name)) {
         while (person.next != null) {
            prevPerson = person;
            person = person.next;
         }
         person.killer = killRing.name;
         graveyard = new AssassinNode(killRing.name, graveyard);
         graveyard.killer = person.name;
         killRing = killRing.next;
         return;
      }

      prevPerson = person;
      person = person.next;
      while (person != null) {
         if (person.name.equalsIgnoreCase(name)) {
            prevPerson.next = person.next;
            person.killer = prevPerson.name;
            AssassinNode graveNode = new AssassinNode(person.name, graveyard);
            graveNode.killer = person.killer;
            graveyard = graveNode;
            return;
         }
         prevPerson = person;
         person = person.next;
      }
   }

   /*Helper method to check if a name exists in a specific linked list starting from a given node.
   @param start The starting node for the search.
   @param name The name to search for.
   @return True if the name exists in the linked list, otherwise returns false.
   */
   private boolean contains(AssassinNode start, String name) {
      AssassinNode person = start;
      while (person != null) {
         if (person.name.equalsIgnoreCase(name)) return true;
         person = person.next;
      }
      return false;
   }
}