import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class SightingTest {
  private Sighting testSighting;
  private Sighting anotherSighting;

  @Before
  public void initialize() {
    testSighting = new Sighting(1, "Sector 1", "Tom Sawyer");
    anotherSighting = new Sighting(1, "Sector 2", "Jane Goodall");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void sighting_instantiatesCorrectly_true() {
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void getAnimalId_sightingInstantiatesWithAnimalId() {
    assertEquals(1, testSighting.getAnimalId());
  }

  @Test
  public void getLocation_sightingInstantiatesWithLocation() {
    assertEquals("Sector 1", testSighting.getLocation());
  }

  @Test
  public void getAnimalId_sightingInstantiatesWithRanger() {
    assertEquals("Tom Sawyer", testSighting.getRanger());
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    testSighting.save();
    anotherSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
    assertEquals(true, Sighting.all().get(1).equals(anotherSighting));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    testSighting.save();
    assertTrue(testSighting.getId() > 0);
  }

  @Test
  public void find_returnsSightingWithSameId_anotherSighting() {
    testSighting.save();
    anotherSighting.save();
    assertEquals(Sighting.find(anotherSighting.getId()), anotherSighting);
  }

  @Test
  public void equals_returnsTrueIfContentsAretheSame() {
    Sighting sameSighting = new Sighting(1, "Sector 1", "Tom Sawyer");
    assertTrue(testSighting.equals(sameSighting));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    testSighting.save();
    assertTrue(Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void save_assignsIdToObject() {
    testSighting.save();
    Sighting savedSighting = Sighting.all().get(0);
    assertEquals(testSighting.getId(), savedSighting.getId());
  }

  @Test
    public void save_recordsTimeOfCreationInDatabase() {
      testSighting.save();
      Timestamp savedTestSighting = Sighting.find(testSighting.getId()).getTimeSighted();
      Timestamp rightNow = new Timestamp(new Date().getTime());
      assertEquals(rightNow.getDay(), savedTestSighting.getDay());
  }

  // @Test
  // public void getAnimalbyAnimalId_retrievesAnimalNamebyId() {
  //   testSighting.save();
  //   Animal firstAnimal = new Animal("Racoon", "Not-Endangered");
  //   firstAnimal.save();
  //   Animal secondAnimal = new Animal("Spotted Owl", "Endangered");
  //   secondAnimal.save();
  //   assertEquals(testSihgihting.getAnimal(testSighting.getAnimalId()).getName(), firstAnimal.getName());
  // }
}
