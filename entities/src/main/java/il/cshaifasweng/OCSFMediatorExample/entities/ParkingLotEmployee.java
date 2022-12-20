package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking_lot_employee")
public class ParkingLotEmployee implements Serializable {
    private String name;
    private int age;
    private double salary;
    @ManyToOne(fetch = FetchType.EAGER)
    private ParkingLot parkingLot;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ParkingLotEmployee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public ParkingLotEmployee() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public String toString() {
        return "ParkingLotEmployee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", parkingLot=" + parkingLot +
                ", id=" + id +
                '}';
    }

}
