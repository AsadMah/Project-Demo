package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import entities.ParkingLot;
import entities.PricingChart;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {

	private static SessionFactory sessionFactory;
	private static Session session;
	private static List<ParkingLot> parkingLotList;

//	Constants.
	public enum Constants
	{
		EMPTY(""),
		BLANK("Error! we got an empty message"),
		PARKING_LOTS("Get parking lot lists"),
		PRICES("Get parking prices"),
		;




		public final String label;

		private Constants(String label) {
			this.label = label;
		}
	}
	private static ArrayList<ParkingLot> parkingLots = new ArrayList<>();
	private static PricingChart pricingChart;

	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg,ConnectionToClient client){
		Message message = (Message) msg;
		String request = message.getMessage();
		try{
			SimpleServer.session = SimpleServer.sessionFactory.openSession();
			if (request.isBlank()){
				message.setMessage("Error! we got an empty message");
				client.sendToClient(message);
				return;
			}
			if(request.startsWith("#ParkingLotsRequest")){
				sendParkingLotList(client);
				return;
			}
			if(request.startsWith("#getPrices")) {
				sendPrices(client);
				return;
			}
			if(request.startsWith("#changePrices")){
				updatePricingTable((PricingChart)message.getObject(),client);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}


//	@Override
//	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
//		Message message = (Message) msg;
//		String request = message.getMessage();
//		try {
//			//we got an empty message, so we will send back an error message with the error details.
//			if (request.isBlank()){
//				message.setMessage("Error! we got an empty message");
//				client.sendToClient(message);
//			}
//			//we got a request to change submitters IDs with the updated IDs at the end of the string, so we save
//			// the IDs at data field in Message entity and send back to all subscribed clients a request to update
//			//their IDs text fields. An example of use of observer design pattern.
//			//message format: "change submitters IDs: 123456789, 987654321"
//			else if(request.startsWith("change submitters IDs:")){
//				message.setData(request.substring(23));
//				IDs=request.substring(23);
//				message.setMessage("update submitters IDs");
//				sendToAllClients(message);
//			}
//			//we got a request to add a new client as a subscriber.
//			else if (request.equals("add client")){
//				SubscribedClient connection = new SubscribedClient(client);
//				SubscribersList.add(connection);
//				message.setMessage("client added successfully");
//				client.sendToClient(message);
//			}
//			//we got a message from client requesting to echo Hello, so we will send back to client Hello world!
//			else if(request.startsWith("echo Hello")){
//				message.setMessage("Hello World!");
//				client.sendToClient(message);
//			}
//			else if(request.startsWith("send Submitters IDs")){
//				message.setMessage(IDs);
//				client.sendToClient(message);
//			}
//			else if (request.startsWith("send Submitters")){
//				message.setMessage("abed Elrahman abo hussien, Asa'd Mhajne");
//				client.sendToClient(message);
//			}
//			else if (request.equals("what day it is?")) {
//				LocalDate currentDate=LocalDate.now();
//				message.setMessage((currentDate.format(DateTimeFormatter.ofPattern("d/MM/y"))));
//				client.sendToClient(message);
//				//add code here to send the date to client
//			}
//			else if (request.startsWith("add")){
//				//add code here to sum 2 numbers received in the message and send result back to client
//				//(use substring method as shown above)
//				//message format: "add n+m"
//				String numbersToAdd=request.substring(4);
//				String[] splitedNumbers =numbersToAdd.split("[+]");
//				int result=Integer.parseInt(splitedNumbers[0])+Integer.parseInt(splitedNumbers[1]);
//				message.setMessage(Integer.toString(result));
//				client.sendToClient(message);
//
//			}else{
//				//add code here to send received message to all clients.
//				//The string we received in the message is the message we will send back to all clients subscribed.
//				//Example:
//					// message received: "Good morning"
//					// message sent: "Good morning"
//				//see code for changing submitters IDs for help
//				message.setMessage(request);
//				sendToAllClients(message);
//			}
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//	}

//	public void sendToAllClients(Message message) {
//		try {
//			for (SubscribedClient SubscribedClient : SubscribersList) {
//				SubscribedClient.getClient().sendToClient(message);
//			}
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//	}

	public void sendParkingLotList(ConnectionToClient client) throws IOException {
		final CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
		final CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
		query.from(ParkingLot.class);
		final ArrayList<ParkingLot> data = (ArrayList<ParkingLot>)(ArrayList)SimpleServer.session.createQuery(query).getResultList();
		client.sendToClient(parkingLots);
	}
	public void sendPrices(ConnectionToClient client) throws IOException{
		final CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
		final CriteriaQuery<PricingChart> query = builder.createQuery(PricingChart.class);
		query.from(PricingChart.class);
		final ArrayList<PricingChart> data = (ArrayList<PricingChart>)(ArrayList)SimpleServer.session.createQuery(query).getResultList();
		client.sendToClient(data.get(0));
	}
	private void updatePricingTable(PricingChart newChart,ConnectionToClient client) throws IOException {
		final CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
		final CriteriaQuery<PricingChart> query = builder.createQuery(PricingChart.class);
		query.from(PricingChart.class);
		final ArrayList<PricingChart> data = (ArrayList<PricingChart>)(ArrayList)SimpleServer.session.createQuery(query).getResultList();
		for(PricingChart oldChart : data){
			if(oldChart.getId() == newChart.getId()){
				SimpleServer.session.beginTransaction();
				oldChart.setFullSubscription(newChart.getFullSubscription());
				oldChart.setOneTimePurchasedAhead(newChart.getOneTimePurchasedAhead());
				oldChart.setParkViaKiosk(newChart.getParkViaKiosk());
				oldChart.setRegularSubscription(newChart.getRegularSubscription());
				oldChart.setRegularMultipleCars(newChart.getRegularMultipleCars());
				SimpleServer.session.save(oldChart);
				SimpleServer.session.flush();
				SimpleServer.session.getTransaction().commit();
				client.sendToClient(new Message("#RefreshAdd","refresh"));
			}
		}
	}


}
