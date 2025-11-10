import Vehicle.Vehicle;
import Vehicle.Auto;
import Vehicle.Motorcycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


// public class ReflectionTask {

//     public static void main(String[] args) {
//         try{
//             if(args.length < 4){
//                 System.err.println("Должно быть 4 аргумента: <VehicleType> <Mark> <ModelName> <ModelPrice>");
//                 return;
//             }
        
//             String vehicleType = args[0];
//             String mark = args[1];
//             String modelName = args[2];
//             double modelPrice = Double.parseDouble(args[3]);

//             Vehicle  vehicle = new Auto("Toyota",3);
//             Class<?> clazz = Class.forName("Vehilce.Auto");
//             Method method = clazz.getMethod("setModelPrice", String.class, double.class);
//             method.invoke(vehicle,modelName, modelPrice);
//         }
//         catch (InvocationTargetException e){
//             e.getCause().printStackTrace();
//         }
//     }
// }


public class ReflectionTask {
    
    public static void main(String[] args) {
        // Проверяем количество аргументов
        if (args.length < 4) {
            System.out.println("Использование: java ReflectionTask <полное_имя_класса> <имя_метода> <название_модели> <новая_цена>");
            System.out.println("Пример: java ReflectionTask Vehicle.Auto setModelPrice BMW0 5500000");
            return;
        }

        String className = args[0];      // Полное имя класса
        String methodName = args[1];     // Имя метода
        String modelName = args[2];      // Название модели
        double prices = Double.parseDouble(args[3]);       // Новая цена

        try {
            Class<?> vehicleClass = Class.forName(className);
            System.out.println("=== Загружен класс: " + vehicleClass.getName() + " ===");

            Vehicle vehicleInstance = (Vehicle) vehicleClass
                .getConstructor(String.class, int.class)
                .newInstance("TestMark", 3);

            System.out.println("=== Информация об автомобиле ДО изменения цены ===  ");
            System.out.println(vehicleInstance);
            System.out.println("---------------------------------------------");

            Method method = vehicleClass.getMethod(methodName, String.class, double.class);
            System.out.println("Найден метод: " + method.getName());
            System.out.println("Параметры метода: " + java.util.Arrays.toString(method.getParameterTypes()));
            System.out.println();

            System.out.println("Вызываем метод: " + methodName + "(\"" + modelName + "\", " + prices + ")");
            method.invoke(vehicleInstance, modelName, prices);
            System.out.println("Метод успешно выполнен!");

            System.out.println("=== Информация об автомобиле ПОСЛЕ изменения цены ===  ");
            System.out.println(vehicleInstance);

        } catch (java.lang.reflect.InvocationTargetException e) {
            System.err.println("Ошибка при выполнении метода: " + e.getCause().getMessage());
            System.err.println("Возможно, указанная модель не существует или цена вне допустимого диапазона");
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
