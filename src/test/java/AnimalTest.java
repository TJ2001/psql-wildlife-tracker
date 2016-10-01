import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class AnimalTest {
  private Animal testAnimal;
  private Animal anotherAnimal;

  @Before
  public void initialize() {
    testAnimal = new Animal("Racoon", "Not-Endangered");
    anotherAnimal = new Animal("Spotted Owl", "Endangered");
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
  public void find_returnsAnimalWithSameId_anotherAnimal() {
    testAnimal.save();
    anotherAnimal.save();
    assertEquals(Animal.find(anotherAnimal.getId()), anotherAnimal);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Animal sameAnimal = new Animal("Racoon", "Not-Endangered");
    assertTrue(testAnimal.equals(sameAnimal));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    testAnimal.save();
    assertTrue(Animal.all().get(0).equals(testAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    testAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(testAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void checkup_increasesAnimalCheckupCounter() {
    testAnimal.checkup();
    assertTrue(testAnimal.getCheckupCounter() > (Animal.MIN_ALL));
  }

  @Test
  public void checkup_increasesAnimalTrackerCount() {
    testAnimal.putTracker();
    assertTrue(testAnimal.getTrackerCount() > (Animal.MIN_ALL));
  }

  @Test
  public void animal_cannotRecieveMoreThanMaxChekupsPerYear() {
    for(int i = Animal.MIN_ALL; i <= (Animal.MAX_CHECKUPS_PER_YEAR + 2); i++) {
      try {
        testAnimal.checkup();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testAnimal.getCheckupCounter() <= Animal.MAX_CHECKUPS_PER_YEAR);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkup_throwsExceptionsIfCheckupCounterIsAtMaxValue() {
    for(int i = Animal.MIN_ALL; i <= (Animal.MAX_CHECKUPS_PER_YEAR); i++) {
      testAnimal.checkup();
    }
  }

  @Test
  public void animal_cannotHaveMoreThanMaxTrackerAdded() {
    for(int i = Animal.MIN_ALL; i <= (Animal.MAX_TRACKERADDED + 1); i++) {
      try {
        testAnimal.putTracker();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testAnimal.getTrackerCount() <= Animal.MAX_TRACKERADDED);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void trackerCount_throwsExceptionsIfTrackerCountIsAtMaxValue() {
    for(int i = Animal.MIN_ALL; i <= (Animal.MAX_TRACKERADDED); i++) {
      testAnimal.putTracker();
    }
  }

  @Test
  public void getSightings_retrievesAllSightingsFromDatabase_sightingsList() {
    testAnimal.save();
    Sighting firstSighting = new Sighting(testAnimal.getId(), "Sector 1", "Tom Sawyer");
    firstSighting.save();
    Sighting secondSighting = new Sighting(testAnimal.getId(), "Sector 2", "Jane Goodall");
    secondSighting.save();
    Sighting[] sightings = new Sighting[] { firstSighting, secondSighting };
    assertTrue(testAnimal.getSightings().containsAll(Arrays.asList(sightings)));
  }
}
