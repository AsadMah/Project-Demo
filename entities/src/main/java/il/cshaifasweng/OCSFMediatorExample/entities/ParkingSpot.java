package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking_spots")
public class ParkingSpot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int row;
    private int column;
    private int depth;
    private boolean occupied;
    private String currentCarId;
    private boolean saved;

    public ParkingSpot(int row, int column, int depth) {
        this.row = row;
        this.column = column;
        this.depth = depth;
    }

    public ParkingSpot(int row, int column, int depth, boolean occupied, String currentCarId, boolean saved) {
        this.row = row;
        this.column = column;
        this.depth = depth;
        this.occupied = occupied;
        this.currentCarId = currentCarId;
        this.saved = saved;
    }

    public ParkingSpot() {

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getCurrentCarId() {
        return currentCarId;
    }

    public void setCurrentCarId(String currentCarId) {
        this.currentCarId = currentCarId;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean free(){
        if(this.isOccupied())
            return false;
        if(this.isSaved())
            this.saved = false;
        return true;
    }
    public boolean markAsSaved(){
        if(this.isOccupied())
            return false;
        if(!this.isSaved())
            this.saved = true;
        return true;
    }


}
