import Vehicle.Auto;
import Vehicle.Motorcycle;
import Vehicle.TransportTools;
import Vehicle.Vehicle;

import java.io.*;

import Exceptions.DuplicateModelNameException;
import Exceptions.NoSuchModelNameException;

public class Test {
    public static void main(String[] args)
            throws DuplicateModelNameException, NoSuchModelNameException, IOException {
        // try {
        //     Vehicle t = new Motorcycle("BMW", 3);
        //     Vehicle t1 = new Motorcycle("BMW", 3);
            
        //     t.setModelPrice("BMW0", 1100000);
        //     t.setModelPrice("BMW10", 1200000);
        //     t.setModelPrice("BMW20", 1300000);
            
        //     t1.setModelPrice("BMW0", 1100000);
        //     t1.setModelPrice("BMW10", 1200000);
        //     t1.setModelPrice("BMW20", 1300000);
            
        //     System.out.println("первый транспорт");
        //     System.out.println(t);
        //     System.out.println("второй транспорт");
        //     System.out.println(t1);
            
        //     boolean q = t.equals(t1);
        //     int hash = t.hashCode();
        //     int hash2 = t1.hashCode();
            
        //     System.out.println(q);
        //     System.out.println(hash);
        //     System.out.println(hash2);
            
        //     Vehicle t2 = (Vehicle) t.clone();

        //     t2.addNewModel("AUDI", 2500000);
        //     t2.deleteModel("BMW0");
        //     //t2.setModelPrice("BMW0", 3000000);
        //     System.out.println(t);
        //     System.out.println(t2);
        // } catch (CloneNotSupportedException e) {
        // }
        Vehicle vehicle = new  Auto("Toyota", 3);
        System.err.println(vehicle);
        FileWriter fw = new FileWriter("testik.txt");
        TransportTools.writeVehicle(vehicle, fw);
        FileReader fr = new FileReader("testik.txt");
        Vehicle readVehicle = TransportTools.readVehicle(fr);
        System.err.println(readVehicle);
        
    }

}
