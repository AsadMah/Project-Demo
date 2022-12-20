package entities;

public enum PricingChartEnum {
//
    PARK_VIA_KIOSK(8),
    ONE_TIME_PURCHASED_AHEAD(7),
    REGULAR_SUBSCRIPTION(60* ONE_TIME_PURCHASED_AHEAD.value),
    REGULAR_SUBSCRIPTION_MULIPLE_CARS(54 * ONE_TIME_PURCHASED_AHEAD.value),
    FULL_SUBSCRIPTION(72 * ONE_TIME_PURCHASED_AHEAD.value),
    ;

    public final double value;
    private PricingChartEnum(double value){this.value = value;}
}
