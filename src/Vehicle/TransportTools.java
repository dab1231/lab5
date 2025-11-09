package Vehicle;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import Exceptions.DuplicateModelNameException;

public class TransportTools {

    public static Vehicle CreateVehicle(String mark, int sizeModels, Vehicle template) {
        try {
            Class<?> clazz = template.getClass();
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
            return (Vehicle) constructor.newInstance(mark, sizeModels);

        } catch (NoSuchMethodException e) {
            return null;
           
        } catch (Exception e) {
            return null;
        }
    }

    public static double getAverageVeh(Vehicle... vehicles){
        double totalSum = 0;
        int totalcount = 0;
        for(Vehicle v : vehicles){
            double[] prices = v.getPrices();
            for(double price : prices){
             totalSum += price;
                totalcount++;
            }
        }
        if(totalcount == 0){
            return 0;
        }
        return totalSum / totalcount;
    }

    public static double getAVGPrice(Vehicle vehicle) {
        double[] prices = vehicle.getPrices();
        if (vehicle.getSize() == 0) {
            return 0;
        }
        System.out.println("Средняя цена моделей марки:" + vehicle.getMark() + ":");
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    public static void printNames(Vehicle vehicle) {
        if (vehicle.getSize() == 0) {
            return;
        }
        String[] names = vehicle.getNames();
        System.out.println("Модели марки:" + vehicle.getMark() + ":");
        for (String name : names) {
            System.out.println(name);
        }
    }

    public static void printPrices(Vehicle vehicle) {
        if (vehicle.getSize() == 0) {
            return;
        }
        double[] prices = vehicle.getPrices();
        System.out.println("Цены моделей марки:" + vehicle.getMark() + ":");
        for (double price : prices) {
            System.out.println(price);
        }
    }

    
    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        
        String type = v.getVehicleType();
        byte[] typeBytes = type.getBytes();
        dos.writeInt(typeBytes.length);  
        dos.write(typeBytes);            
        
        String mark = v.getMark();
        byte[] markBytes = mark.getBytes();
        dos.writeInt(markBytes.length);  
        dos.write(markBytes);            
        
        dos.writeInt(v.getSize());
        
        String[] names = v.getNames();
        for (String name : names) {
            byte[] nameBytes = name.getBytes();
            dos.writeInt(nameBytes.length);  
            dos.write(nameBytes);            
        }
        
        double[] prices = v.getPrices();
        for (double price : prices) {
            dos.writeDouble(price);
        }
        
        dos.flush(); 
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        
        int typeLength = dis.readInt();
        byte[] typeBytes = new byte[typeLength];
        dis.read(typeBytes);
        String type = new String(typeBytes);
        
        int markLength = dis.readInt();
        byte[] markBytes = new byte[markLength];
        dis.read(markBytes);
        String mark = new String(markBytes);
        
        int size = dis.readInt();
        
        String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            int nameLength = dis.readInt();
            byte[] nameBytes = new byte[nameLength];
            dis.read(nameBytes);
            names[i] = new String(nameBytes);
        }
        
        double[] prices = new double[size];
        for (int i = 0; i < size; i++) {
            prices[i] = dis.readDouble();
        }
        
        Vehicle vehicle;
        if (type.equals("Auto")) {
            vehicle = new Auto(mark, 0); 
        } else if (type.equals("Motorcycle")) {
            vehicle = new Motorcycle(mark, 0); 
        } else {
            throw new IOException("Неизвестный тип транспорта: " + type);
        }
        
        for (int i = 0; i < size; i++) {
            try {
                vehicle.addNewModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
            }
        }
        
        return vehicle;
    }

    
    public static void writeVehicle(Vehicle v, Writer out) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        
        pw.printf("%s\n", v.getVehicleType());
        
        pw.printf("%s\n", v.getMark());
        
        pw.printf("%d\n", v.getSize());
        
        String[] names = v.getNames();
        double[] prices = v.getPrices();
        
        for (int i = 0; i < names.length; i++) {
            pw.printf("%s\n",names[i]);
            pw.printf("%.2f\n",prices[i]);
        }
        
        pw.flush();
    }

    public static Vehicle readVehicle(Reader in) throws IOException {
        Scanner sc = new Scanner(in);

        String type = sc.nextLine();
        
        String mark = sc.nextLine();
        
        int size = sc.nextInt();
        sc.nextLine();
        
        String[] names = new String[size];
        double[] prices = new double[size];
        
        for (int i = 0; i < size; i++) {
            names[i] = sc.nextLine();
            prices[i] = sc.nextDouble();
            sc.nextLine();
        }
        
        Vehicle vehicle;
        if (type.equals("Auto")) {
            vehicle = new Auto(mark, 0);
        } else if (type.equals("Motorcycle")) {
            vehicle = new Motorcycle(mark, 0);
        } else {
            throw new IOException("Неизвестный тип транспорта: " + type);
        }
        
        for (int i = 0; i < size; i++) {
            try {
                vehicle.addNewModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
            }
        }
        
        return vehicle;
    }
}
