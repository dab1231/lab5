import java.io.FileReader;
import java.io.FileWriter;
import Vehicle.Auto;
import Vehicle.Motorcycle;
import Vehicle.Vehicle;
import Vehicle.TransportTools;
import Vehicle.Scooter;
import Vehicle.Cuadro;
import Vehicle.Moped;

public class Main {

    public static void main(String[] args) {

        System.out.println("--- Проверка Задания 2: TransportTools.CreateVehicle ---");
        Vehicle templateAuto = new Auto("Шаблон", 0);
        String newMark = "Lada";
        int newSize = 5;
        Vehicle newAuto = TransportTools.CreateVehicle(newMark, newSize, templateAuto);
        System.out.println(newAuto.getClass());
        System.out.println(newAuto);


        System.out.println("\n--- Проверка Задания 3: Класс Scooter ---");

        Vehicle scooter = new Scooter("TestScooter", 3);
        System.out.println(scooter);

        try {
            System.out.println("\nДобавляем 'Model S'...");
            scooter.addNewModel("Model S", 50000.0);

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при добавлении: " + e.getMessage());
        }
        System.out.println(scooter);

        try {
            scooter.setModelPrice("Model S", 55000.0);
            
            double newPrice = scooter.getModelPrice("Model S");
            if (newPrice != 55000.0) {
                System.out.println("ПРОВАЛ! Цена не совпадает с ожидаемой.");
            }

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при изменении цены: " + e.getMessage());
        }
        System.out.println(scooter);
        try {
            System.out.println("\nУдаляем 'Model X'...");
            scooter.deleteModel("Model S");

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при удалении: " + e.getMessage());
        }
        System.out.println(scooter);


        System.out.println("\n--- Проверка Задания 4: Класс Cuadro ---");

        Vehicle cuadro = new Cuadro("TestCuadro", 0);
        System.out.println(cuadro);

        try {
            System.out.println("\nДобавляем 'Raptor'...");
            cuadro.addNewModel("Raptor", 150000.0);
            double price = cuadro.getModelPrice("Raptor");

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при добавлении: " + e.getMessage());
        }
        System.out.println(cuadro);

        try {
            System.out.println("\nМеняем цену 'Raptor' на 160000.0...");
            cuadro.setModelPrice("Raptor", 160000.0);
            double newPrice = cuadro.getModelPrice("Raptor");
            if (newPrice != 160000.0) {
                System.out.println("ПРОВАЛ! Цена не совпадает с ожидаемой.");
            }

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при изменении цены: " + e.getMessage());
        }
        System.out.println(cuadro);
        try {
            System.out.println("\nУдаляем 'Raptor'...");
            cuadro.deleteModel("Raptor");

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при удалении: " + e.getMessage());
        }


        System.out.println("\n--- Проверка Задания 5: Класс Moped  ---");

        Vehicle moped = new Moped("TestMoped", 0);
        System.out.println(moped);

        try {
            System.out.println("\nДобавляем 'Alpha'...");
            moped.addNewModel("Alpha", 80000.0);
            double price = moped.getModelPrice("Alpha");

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при добавлении: " + e.getMessage());
        }
        System.out.println(moped);

        try {
            System.out.println("\nМеняем цену 'Alpha' на 85000.0...");
            moped.setModelPrice("Alpha", 85000.0);
            double newPrice = moped.getModelPrice("Alpha");
            if (newPrice != 85000.0) {
                System.out.println("ПРОВАЛ! Цена не совпадает с ожидаемой.");
            }

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при изменении цены: " + e.getMessage());
        }
        System.out.println(moped);

        try {
            System.out.println("\nУдаляем 'Alpha'...");
            moped.deleteModel("Alpha");

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Ошибка при удалении: " + e.getMessage());
        }
        System.out.println(moped);

        System.out.println("\n--- Проверка Задания 6: TransportTools.getAverageVeh ---");

        try {
            Vehicle v1 = new Auto("TestAutoAvg", 0);
            v1.addNewModel("ModelA1", 1000000.0);
            v1.addNewModel("ModelA2", 2000000.0);
            
            Vehicle v2 = new Motorcycle("TestMotoAvg", 0);
            v2.addNewModel("ModelM1", 3000000.0);
            v2.addNewModel("ModelM2", 4000000.0);
            v2.addNewModel("ModelM3", 5000000.0);

            System.out.println("Созданы 2 ТС:");
            System.out.println(v1);
            System.out.println(v2);

            double totalSum = 1000000.0 + 2000000.0 + 3000000.0 + 4000000.0 + 5000000.0;
            int totalCount = 5;
            double expectedAvg = totalSum / totalCount;
            
            System.out.println("\nОжидаемая средняя цена: " + expectedAvg);
            
            double actualAvg = TransportTools.getAverageVeh(v1, v2);
            
            System.out.println("Фактическая средняя цена: " + actualAvg);

        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Произошла ошибка: " + e.getMessage());
        }


        System.out.println("\n--- Проверка Задания 7 ---");
        
        try {
            Vehicle Cuadro = new Cuadro("Suzuki", 3);
            System.out.println(Cuadro);
            FileWriter fw = new FileWriter("testik.txt");

            TransportTools.writeVehicle(Cuadro, fw);   
            fw.close();

            FileReader fr = new FileReader("testik.txt");
            Vehicle readTextVehicle = TransportTools.readVehicle(fr);
            fr.close();
            
            System.out.println(Cuadro);
            
        } catch (Exception e) {
            System.out.println("ПРОВАЛ! Произошла ошибка: " + e.getMessage());
        }


        System.out.println("\n--- Все проверки завершены ---");
    }
}