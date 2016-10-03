import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;

public class Sighting {
  private int animalId;
  private String location;
  private String ranger;
  private Timestamp timeSighted;
  private int id;

  public Sighting(int animalId, String location, String ranger) {
    this.animalId = animalId;
    this.location = location;
    this.ranger = ranger;
  }

  public int getAnimalId() {
    return animalId;
  }

  public String getLocation() {
    return location;
  }

  public String getRanger() {
    return ranger;
  }

  public Timestamp getTimeSighted() {
    return timeSighted;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherSighting) {
    if (!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getAnimalId() == newSighting.getAnimalId() &&
      this.getLocation().equals(newSighting.getLocation()) &&
      this.getRanger().equals(newSighting.getRanger()) &&
      this.getId() == newSighting.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings(animalId, location, ranger, timeSighted) VALUES (:animalId, :location, :ranger, now())";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("animalId", this.animalId)
      .addParameter("location", this.location)
      .addParameter("ranger", this.ranger)
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String content, String animalId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sightings SET animalId = :animalId, ranger = :ranger, location = :location WHERE id = :id";
      con.createQuery(sql)
      .addParameter("animalId", animalId)
      .addParameter("ranger", ranger)
      .addParameter("location", location)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static List<Sighting> all() {
    String sql = "SELECT * FROM sightings";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings where id=:id";
      Sighting sighting = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Sighting.class);
      return sighting;
    }
  }

  // public static Animal getAnimal(int animalId) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM animals where id=:id";
  //     return con.createQuery(sql)
  //     .addParameter("id", animalId)
  //     .executeAndFetchFirst(Animal.class);
  //   }
  // }
}
