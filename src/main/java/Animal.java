import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

//type for endangered or non-endangered

public class Animal implements Getter {

  public String name;
  public String type;
  public int sightingId;
  public int id;
  public int checkupCounter;
  public int trackerCount;

  public static final int MAX_CHECKUPS_PER_YEAR = 2;
  public static final int MAX_TRACKERADDED = 1;
  public static final int MIN_ALL = 0;

  public Animal(String name, String type, int sightingId) {
    this.name = name;
    this.type = type;
    this.sightingId = sightingId;
    this.checkupCounter = MIN_ALL;
    this.trackerCount = MIN_ALL;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public int getSightingId() {
    return sightingId;
  }

  public int getId() {
    return id;
  }

  public int getCheckupCounter(){
    return checkupCounter;
  }

  public int getTrackerCount() {
    return trackerCount;
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if (!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName()) &&
      this.getType().equals(newAnimal.getType()) &&
      this.getSightingId() == newAnimal.getSightingId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals(name, type, sightingId) VALUES (:name, :type, :sightingId)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("type", this.type)
      .addParameter("sightingId", this.sightingId)
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id=:id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String name, int sightingId, String instructions) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name, type=:type, sightingId =:sightingId WHERE id=:id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("type", type)
      .addParameter("sightingId", sightingId)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static List<Animal> all() {
    String sql = "SELECT * FROM animals";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Animal.class);
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals where id=:id";
      Animal animal = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Animal.class);
      return animal;
    }
  }


  public void checkup() {
    if (checkupCounter == MAX_CHECKUPS_PER_YEAR) {
      System.out.println("You cannot do more than two checkups annually");
      throw new UnsupportedOperationException("You cannot do more than two checkups annually");
    } else {
      checkupCounter++;
    }
  }

  public void putTracker() {
    if (trackerCount == MAX_TRACKERADDED) {
      throw new UnsupportedOperationException("This animal already has a tracker!");
    } else {
      trackerCount++;
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings where animalId=:id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Sighting.class);
    }
  }

}
