package tablePerClass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "boat_table_per_class")
public class Boat extends Vehicle {

   private Integer engines;
   private Integer maxSpeed;

   public Boat() {

   }

   public Integer getEngines() {
      return engines;
   }

   public void setEngines(Integer engines) {
      this.engines = engines;
   }

   public Integer getMaxSpeed() {
      return maxSpeed;
   }

   public void setMaxSpeed(Integer maxSpeed) {
      this.maxSpeed = maxSpeed;
   }
}
