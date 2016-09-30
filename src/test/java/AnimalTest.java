import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class AnimalTest {
  private Animal testAnimal;

  @Before
  public void initialize() {
    testAnimal = new Animal("Racoon", "Not-Endangered", 1);
    anotherAnimal = new Animal("Spotted Owl", "Endangered", 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_true() {
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getName_animalInstantiatesWithName() {
    assertEquals("Racoon", testAnimal.getName());
  }

  @Test
  public void getType_animalInstantiatesWithType() {
    assertEquals("Not-Endangered", testAnimal.getType());
  }

  @Test
  public void getSightingId_animalInstantiatesWithSightingId() {
    assertEquals(1, testAnimal.getSightingId());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
    testAnimal.save();
    anotherAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(testAnimal));
    assertEquals(true, Animal.all().get(1).equals(anotherAnimal));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    testAnimal.save();
    assertTrue(testAnimal.getId() > 0);
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    testAnimal.save();
    anotherAnimal.save();
    assertEquals(Animal.find(anotherAnimal.getId()), testAnimal);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Animal sameAnimal = new Animal("Racoon", "Not-Endangered", 1);
    assertTrue(testAnimal.equals(sameAnimal));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Animal myAnimal = new Animal("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    myAnimal.save();
    assertTrue(Animal.all().get(0).equals(myAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    testAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(testAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void likes_ownException() {
    testAnimal.save();
    try {
        testAnimal.checkup();
      } catch (UnsupportedOperationException exception){ }
    assertTrue(0 == testSighting.getLikes());
  }

  @Test
  public void likes_gainLikes() {
    Sighting testSighting = new Sighting("This topic sucks", 1);
    testSighting.save();
    try {
        testSighting.likes(2);
      } catch (UnsupportedOperationException exception){ }
    assertTrue(1 == testSighting.getLikes());
  }

  @Test
  public void likes_OnlyOneLikesperUser() {
    Sighting testSighting = new Sighting("This topic sucks", 1);
    testSighting.save();
    try {
        testSighting.likes(2);
        testSighting.likes(2);
      } catch (UnsupportedOperationException exception){ }
    assertTrue(1 == testSighting.getLikes());
  }



  // @Test
  // public void getMessage_manyToMany () {
  //   testAnimal.save();
  //   Message myMessage = new Message("This animal is okay.", 1);
  //   myMessage.save();
  //   testAnimal.add(myMessage);
  //   assertTrue(myMessage.equals(testAnimal.getMessage().get(0)));
  // }

  // @Test
  // public void getMonsters_retrievesAllMonstersFromDatabase_monstersList() {
  //   Animal testAnimal = new Animal("Henry", "henry@henry.com");
  //   testAnimal.save();
  //   Monster firstMonster = new Monster("Squirtle", "Water", testAnimal.getId());
  //   firstMonster.save();
  //   Monster secondMonster = new Monster("Pikachu", "Elecric", testAnimal.getId());
  //   secondMonster.save();
  //   Monster[] monsters = new Monster[] { firstMonster, secondMonster };
  //   assertTrue(testAnimal.getMonsters().containsAll(Arrays.asList(monsters)));
  // }

}
