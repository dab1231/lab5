import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;
import Vehicle.Auto;
import Vehicle.Motorcycle;
import Vehicle.TransportTools;
import Vehicle.Vehicle;

public class Program 
{
    public static void main(String[] args)  throws DuplicateModelNameException, NoSuchModelNameException
    {
        try 
        {
            System.out.println("Тест с машинами");
            Vehicle car = new Auto("BMW", 4);
            System.out.println("Бренд: " + car.getMark());

            TransportTools.printNames(car);

            TransportTools.printPrices(car);

            car.setModelPrice("BMW0", 1000000);
            car.setModelPrice("BMW10", 1500000);
            car.setModelPrice("BMW20", 2000000);
            car.setModelPrice("BMW30", 2500000);
            System.out.println("Обновление цены");
            TransportTools.printPrices(car);

            car.addNewModel("BMW40", 3000000);
            System.out.println("После добавления модели4");
            TransportTools.printNames(car);

            car.setModelName("BMW40","BMW40New");
            car.setModelPrice("BMW40New",1200000);
            car.deleteModel("BMW0");
            TransportTools.printNames(car);
            System.out.println(TransportTools.getAVGPrice(car));
            
            System.out.println("\nТест для исключений");
            
            try 
            {
                car.getModelPrice("BMW90");
            } 
            catch (NoSuchModelNameException e) 
            {
                System.out.println("Ошибка: " + e.getMessage());
            }
            try 
            {
                car.addNewModel("BMW10", 10);
            } 
            catch (DuplicateModelNameException e) 
            {
                System.out.println("Ошибка: " + e.getMessage());
            }
            
            try 
            {
                car.setModelPrice("BMW12", 1000); 
            } 
            catch (ModelPriceOutOfBoundsException e) 
            {
                System.out.println("Ошибка: " + e.getMessage());
            }   
        } 
        catch (Exception e) 
        {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
