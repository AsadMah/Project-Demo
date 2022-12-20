package entities;

import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "prices")
public class PricingChart {
    @Column(name = "park_via_kiosk")
    private double parkViaKiosk;
    @Column(name = "one_time_purchased_ahead")
    private double oneTimePurchasedAhead;
    @Column(name = "regular_subscription")
    private double regularSubscription;
    @Column(name = "regular_multiple_cars")
    private double regularMultipleCars;
    @Column(name = "full_subscription")
    private double fullSubscription;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public PricingChart(double parkViaKiosk, double oneTimePurchasedAhead, double regularSubscription, double regularMultipleCars, double fullSubscription) {
        this.parkViaKiosk = parkViaKiosk;
        this.oneTimePurchasedAhead = oneTimePurchasedAhead;
        this.regularSubscription = regularSubscription;
        this.regularMultipleCars = regularMultipleCars;
        this.fullSubscription = fullSubscription;
    }

    public PricingChart() {
    }

    public double getParkViaKiosk() {
        return parkViaKiosk;
    }

    public void setParkViaKiosk(double parkViaKiosk) {
        this.parkViaKiosk = parkViaKiosk;
    }

    public double getOneTimePurchasedAhead() {
        return oneTimePurchasedAhead;
    }

    public void setOneTimePurchasedAhead(double oneTimePurchasedAhead) {
        this.oneTimePurchasedAhead = oneTimePurchasedAhead;
    }

    public double getRegularSubscription() {
        return regularSubscription;
    }

    public void setRegularSubscription(double regularSubscription) {
        this.regularSubscription = regularSubscription;
    }

    public double getRegularMultipleCars() {
        return regularMultipleCars;
    }

    public void setRegularMultipleCars(double regularMultipleCars) {
        this.regularMultipleCars = regularMultipleCars;
    }

    public double getFullSubscription() {
        return fullSubscription;
    }

    public void setFullSubscription(double fullSubscription) {
        this.fullSubscription = fullSubscription;
    }
    public double getRegularByNumberOFCars(int numberOfCars){
        return numberOfCars * regularMultipleCars;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
