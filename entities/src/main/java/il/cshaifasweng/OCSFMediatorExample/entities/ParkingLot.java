package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parking_lots")
public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int floor;
    private int rowsInEachFloor;
    private int rowCapacity;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parkingLot")
    private List<ParkingSpot> spots;
    @ManyToOne(fetch = FetchType.EAGER)
    PricingChart pricingChart;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parkingLot")
    private List<ParkingLotEmployee> employees;



    public ParkingLot(int floor, int rowsInEachFloor, int rowCapacity) {
        this.floor = floor;
        this.rowsInEachFloor = rowsInEachFloor;
        this.rowCapacity = rowCapacity;
        spots = new ArrayList<>();
        employees = new ArrayList<>();
    }


    public ParkingLot(int floor, int rowsInEachFloor, int rowCapacity, int parkingLotId, List<ParkingSpot> spots, List<ParkingLotEmployee> employees) {
        this(floor,rowsInEachFloor,rowCapacity);
        this.spots = spots;
        this.employees = employees;
    }

    public ParkingLot() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRowsInEachFloor(int rowsInEachFloor) {
        this.rowsInEachFloor = rowsInEachFloor;
    }

    public void setRowCapacity(int rowCapacity) {
        this.rowCapacity = rowCapacity;
    }

    private List<ParkingSpot> getNewSpots(int floor, int rowsInEachFloor, int rowCapacity) {
        List<ParkingSpot> addedSpots = new ArrayList<>();
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < rowsInEachFloor; j++) {
                for (int k = 0; j < rowCapacity; k++) {
                   addedSpots.add(new ParkingSpot(floor, rowsInEachFloor, rowCapacity));
                }
            }
        }
        return  addedSpots;
    }




    public void setEmployees(List<ParkingLotEmployee> employees) {
        this.employees = employees;
    }

    public int getFloor() {
        return floor;
    }

    public int getRowsInEachFloor() {
        return rowsInEachFloor;
    }

    public int getRowCapacity() {
        return rowCapacity;
    }


    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpot> addedSpots) {
        this.spots = addedSpots;
    }

    public PricingChart getPricingChart() {
        return pricingChart;
    }

    public void setPricingChart(PricingChart pricingChart) {
        this.pricingChart = pricingChart;
    }

    public List<ParkingLotEmployee> getEmployees() {
        return employees;
    }

    public boolean addNewEmployee(ParkingLotEmployee p){
        p.setParkingLot(this);
        return employees.add(p);
    }

    public void init(){
        spots = getNewSpots(floor,rowsInEachFloor,rowCapacity);
    }
}
