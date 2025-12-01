import Vehicle.Vehicle;
import java.lang.reflect.Method;

public class ReflectionTask {
    
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Пример: java ReflectionTask Vehicle.Auto setModelPrice BMW0 5500000");
            return;
        }

        String className = args[0];      
        String methodName = args[1];     
        String modelName = args[2];      
        double prices = Double.parseDouble(args[3]);

        try {
            Class<?> vehicleClass = Class.forName(className);

            Vehicle vehicleInstance = (Vehicle) vehicleClass
                .getConstructor(String.class, int.class)
                .newInstance("BMW", 3);

            System.out.println(vehicleInstance);

            Method method = vehicleClass.getMethod(methodName, String.class, double.class);
            System.out.println();

            method.invoke(vehicleInstance, modelName, prices);

            System.out.println(vehicleInstance);

        
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
