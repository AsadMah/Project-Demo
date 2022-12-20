package il.cshaifasweng.OCSFMediatorExample.server;

import com.google.gson.Gson;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotEmployee;
import il.cshaifasweng.OCSFMediatorExample.entities.PricingChart;
import il.cshaifasweng.OCSFMediatorExample.entities.PricingChartEnum;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleServerClass extends AbstractServer {
    private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
    private static ArrayList<ParkingLot> parkingLots = new ArrayList<>();
    private static PricingChart pricingChart;
    private static HashMap<String,Double> prices = new HashMap<>();

    public SimpleServerClass(int port) {
        super(port);
        addData();
    }

    private void addData() {
//        prices.put("park_via_kiosk",8);
//        prices.put("one_time_purchased_ahead",7);
//        prices.put("regular_subscription",54*7);
//        prices.put("regular_multiple_cars",7);
//        prices.put("full_subscription",7);


        pricingChart = new PricingChart(PricingChartEnum.PARK_VIA_KIOSK.value, PricingChartEnum.ONE_TIME_PURCHASED_AHEAD.value,
                PricingChartEnum.REGULAR_SUBSCRIPTION.value, PricingChartEnum.REGULAR_SUBSCRIPTION_MULIPLE_CARS.value, PricingChartEnum.FULL_SUBSCRIPTION.value);

        ParkingLotEmployee employee_1 = new ParkingLotEmployee("Abed", 24, 24.0);
        ParkingLotEmployee employee_2 = new ParkingLotEmployee("Lian", 22, 20.0);
        ParkingLotEmployee employee_3 = new ParkingLotEmployee("Shehab", 23, 15.0);


        ParkingLot parkingLot_1 = new ParkingLot(3,3,4);
        parkingLot_1.addNewEmployee(employee_1);
        parkingLots.add(parkingLot_1);
        ParkingLot parkingLot_2 = new ParkingLot(3,3,3);
        parkingLot_2.addNewEmployee(employee_2);
        parkingLots.add(parkingLot_2);
        ParkingLot parkingLot_3 = new ParkingLot(3,2,5);
        parkingLot_3.addNewEmployee(employee_3);
        parkingLots.add(parkingLot_3);




    }
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Gson gson = new Gson();
        Message message = (Message) msg;
        String request = message.getMessage();

        try{
            if (request.isBlank()){
                message.setMessage("Error! we got an empty message");
                client.sendToClient(message);
                return;
            }
            if(request.startsWith("#getAllParkingLots")){
                sendParkingLots(message,client);
                return;
            }
            if(request.startsWith("#getPricingChart")){
                sendPricesChart(message,client);
            }
            if(request.startsWith("#updatePrice")){

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void sendToAllClients(Message message) {
        try {
            for (SubscribedClient SubscribedClient : SubscribersList) {
                SubscribedClient.getClient().sendToClient(message);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void sendParkingLots(Message message,ConnectionToClient client) throws IOException {
        message.setObject(parkingLots);
        client.sendToClient(message);
    }
    public void sendPricesChart(Message message,ConnectionToClient client) throws IOException {
        message.setObject(pricingChart);
        client.sendToClient(message);
    }
    public void updatePriceChart(Message message,ConnectionToClient client) throws  IOException{
        ArrayList<String> response = ((ArrayList<String>)message.getObject());
    }
}