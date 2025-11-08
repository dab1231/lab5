package Vehicle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Motorcycle implements Vehicle, Cloneable {
    private String mark;
    private int size = 0;
    private Model head;
    private transient long lastModified; // не сериализуется
    Random random = new Random();

    {
        lastModified = System.currentTimeMillis();
    }

    public Motorcycle(String mark, int size) {
        this.mark = mark;
        
        if (size == 0) {
            // Создаем пустой мотоцикл
            this.size = 0;
            this.head = null;
        } else {
            this.size = 1;
            head = new Model(String.format("BMW%d", 0), random.nextDouble(1000000,8000000));
            head.next = head;
            head.prev = head;

            for (int i = 1; i < size; i++) {
                try {
                    this.addNewModel(String.format("BMW%d", i * 10), random.nextDouble(1000000, 8000000));
                } catch (DuplicateModelNameException e) {
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        Model flag = head;
        for (int i = 0; i < size; i++) {
            sb.append("Название: ").append(flag.name).append(", Цена: ").append(flag.price).append("\n");
            flag = flag.next;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Vehicle)) return false; 
        Vehicle other = (Vehicle) obj;
        if(!(mark.equals(other.getMark()))) return false;
        if(size != other.getSize()) return false;

        String[] thisNames = this.getNames();
        String[] otherNames = other.getNames();
        double[] thisPrices = this.getPrices();
        double[] otherPrices = other.getPrices();

        for(int i = 0; i < size; i++)
        {
            if(!thisNames[i].equals(otherNames[i]) || 
            thisPrices[i] != otherPrices[i]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, size, Arrays.hashCode(getPrices()), Arrays.hashCode(getNames()));
    }

    @Override
    public Motorcycle clone() {
        try {
            Motorcycle cloned = (Motorcycle) super.clone();

            if (head == null) return cloned;

            Model newHead = new Model(this.head.name, this.head.price);
            cloned.head = newHead;
            newHead.next = newHead;
            newHead.prev = newHead;

            Model currentOrig = this.head.next;
            Model currentCloned = newHead;

            for (int i = 1; i < size; i++) {
                Model newModel = new Model(currentOrig.name, currentOrig.price);

                newModel.next = currentCloned.next;
                newModel.prev = currentCloned;
                currentCloned.next.prev = newModel;
                currentCloned.next = newModel;

                currentCloned = newModel;
                currentOrig = currentOrig.next;
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone not support", e);
        }
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
        lastModified = System.currentTimeMillis();
    }

    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public String[] getNames() {
        String[] names = new String[size];
        if (head == null) {
            return names;
        }
        Model flag = head;

        for (int i = 0; i < size; i++) {
            names[i] = flag.name;
            flag = flag.next;
        }
        return names;
    }

    @Override
    public void setModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        Model flag = head;
        Model flag2 = null;

        for (int i = 0; i < size; i++) {
            if (flag.name.equals(newName)) {
                throw new DuplicateModelNameException("уже есть модели с именем" + newName);
            }
            if (flag2 == null && flag.name.equals(oldName)) {
                flag2 = flag;
            }
            flag = flag.next;
        }

        if (flag2 == null) {
            throw new NoSuchModelNameException("такой модели нет");
        }
        else{
            flag2.name = newName;
        }
        lastModified = System.currentTimeMillis();
    }
    

    @Override
    public void addNewModel(String name, double price) throws DuplicateModelNameException {
        // Проверка на дубликаты
        if (head != null) {
            Model flag = head;
            for (int i = 0; i < size; i++) 
            {
                if (flag.name.equals(name)) 
                {
                    throw new DuplicateModelNameException("Такая модель уже есть: " + name);
                }
                flag = flag.next;
            }
        }
        
        Model newModel = new Model();
        newModel.name = name;
        newModel.price = price;
        
        if (head == null) {
            head = newModel;
            head.next = head;
            head.prev = head;
        } else {
            newModel.next = head;
            newModel.prev = head.prev;
            head.prev.next = newModel;
            head.prev = newModel;
        }
        
        size++;
        lastModified = System.currentTimeMillis();
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        if (head == null) {
            throw new NoSuchModelNameException(name);
        }

        if (head.name.equals(name)) {
            if (head.next == head) {
                head = null;
            } else {
                head.prev.next = head.next;
                head.next.prev = head.prev;
                head = head.next;
            }
            size--;
            lastModified = System.currentTimeMillis();
            return;

        } else {
            Model flag = head.next;
            while (flag != head && !flag.name.equals(name)) {
                flag = flag.next;
            }

            if (flag != head) {
                flag.prev.next = flag.next;
                flag.next.prev = flag.prev;
                size--;
                lastModified = System.currentTimeMillis();
                return;
            }
            throw new NoSuchModelNameException("такой модели нет");
        }
    }

    @Override
    public double[] getPrices() {
        double[] prices;

        if (head == null) {
            return prices = new double[0];
        }

        else {
            Model flag = head;
            prices = new double[size];
            for (int i = 0; i < size; i++) {
                prices[i] = flag.price;
                flag = flag.next;
            }
        }
        return prices;
    }

    @Override
    public void setModelPrice(String name, double price)
            throws NoSuchModelNameException {
        if (price < 1000000 || price > 8000000) {
            throw new ModelPriceOutOfBoundsException("вы не попали в диапазон цен");
        }

        if (head == null) {
            throw new NoSuchModelNameException("такой модели нет");
        }

        Model flag = head;

        for (int i = 0; i < size; i++) {
            if (flag.name.equals(name)) {
                flag.price = price;
                lastModified = System.currentTimeMillis();
                return;
            }
            flag = flag.next;
        }

        throw new NoSuchModelNameException("такой модели нет");
    }

    @Override
    public double getModelPrice(String name) throws NoSuchModelNameException {
        if (head == null) {
            throw new NoSuchModelNameException("такой модели нет");
        }

        Model flag = head;
        for (int i = 0; i < size; i++) {
            if (flag.name.equals(name)) {
                return flag.price;
            }
        }
        throw new NoSuchModelNameException("такой модели нет");
    }

    private class Model implements Serializable {
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;
        transient Random random = new Random(); 

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public Model() {
        }
    }
}
