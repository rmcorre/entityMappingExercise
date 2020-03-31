package singleTable;

import javax.persistence.*;

@Entity(name = "CarSingleTable")
@DiscriminatorValue("car")
public class Car extends Vehicle {

   private Integer maxSpeed;
   private Integer gears;
   private Integer engines;

   public Car() {

   }

   public Integer getMaxSpeed() {
      return maxSpeed;
   }

   public void setMaxSpeed(Integer maxSpeed) {
      this.maxSpeed = maxSpeed;
   }

   public Integer getGears() {
      return gears;
   }

   public void setGears(Integer gears) {
      this.gears = gears;
   }

   public Integer getEngines() {
      return engines;
   }

   public void setEngines(Integer engines) {
      this.engines = engines;
   }
}
