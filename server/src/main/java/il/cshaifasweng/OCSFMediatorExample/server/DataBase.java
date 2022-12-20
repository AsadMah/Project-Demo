package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotEmployee;
import il.cshaifasweng.OCSFMediatorExample.entities.PricingChart;
import il.cshaifasweng.OCSFMediatorExample.entities.PricingChartEnum;
import org.hibernate.HibernateException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

public class DataBase {

    static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        final Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(ParkingLot.class);
//        configuration.addAnnotatedClass(PricingChartEnum.class);
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void connectData() {
        try {
            final SessionFactory sessionFactory = getSessionFactory();
            (DataBase.session = sessionFactory.openSession()).beginTransaction();
            initializeData();
        }
        catch (Exception exception) {
            if (DataBase.session != null) {
                DataBase.session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        }
        finally {
            if (DataBase.session != null) {
                DataBase.session.close();
                DataBase.session.getSessionFactory().close();
            }
        }
    }


    private static void initializeData() throws Exception {

        PricingChart pricingChart = new PricingChart(PricingChartEnum.PARK_VIA_KIOSK.value, PricingChartEnum.ONE_TIME_PURCHASED_AHEAD.value,
                PricingChartEnum.REGULAR_SUBSCRIPTION.value, PricingChartEnum.REGULAR_SUBSCRIPTION_MULIPLE_CARS.value, PricingChartEnum.FULL_SUBSCRIPTION.value);
        DataBase.session.save(pricingChart);
        DataBase.session.flush();

        ParkingLotEmployee employee_1 = new ParkingLotEmployee("Abed", 24, 24.0);
        ParkingLotEmployee employee_2 = new ParkingLotEmployee("Lian", 22, 20.0);
        ParkingLotEmployee employee_3 = new ParkingLotEmployee("Shehab", 23, 15.0);

        DataBase.session.save(employee_1);
        DataBase.session.save(employee_2);
        DataBase.session.save(employee_3);
        DataBase.session.flush();


        ParkingLot parkingLot_1 = new ParkingLot(3,3,4);
        parkingLot_1.addNewEmployee(employee_1);
        ParkingLot parkingLot_2 = new ParkingLot(3,3,3);
        parkingLot_2.addNewEmployee(employee_2);
        ParkingLot parkingLot_3 = new ParkingLot(3,2,5);
        parkingLot_3.addNewEmployee(employee_3);

        DataBase.session.save(parkingLot_1);
        DataBase.session.save(parkingLot_2);
        DataBase.session.save(parkingLot_3);
        DataBase.session.flush();

        DataBase.session.getTransaction().commit();
    }


}
