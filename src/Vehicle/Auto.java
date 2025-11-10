package Vehicle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Auto implements Vehicle {

    private String mark;
    private Model[] models;
    Random random = new Random();

    public Auto(String mark, int size) {
        this.mark = mark;
        if (size == 0) {
            // Создаем пустой массив для последующего заполнения
            models = new Model[0];
        } else {
            models = new Model[size];
            for (int i = 0; i < size; i++) {
                Model newModel = new Model(String.format("%s%d", mark, i * 10), random.nextInt(1000000, 8000000));
                models[i] = newModel;
            }
        }
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for(Model model : models){
            sb.append("Название: ").append(model.model).append(", Цена: ").append(model.price).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        if(!(mark.equals(other.getMark()))) return false;
        if(!(models.length == other.getSize())) return false;

        String[] thisNames = this.getNames();
        String[] otherNames = other.getNames();
        double[] thisPrices = this.getPrices();
        double[] otherPrices = other.getPrices();

        for(int i = 0; i < models.length; i++)
        {
            if(!thisNames[i].equals(otherNames[i]) || 
            thisPrices[i] != otherPrices[i]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, Arrays.hashCode(getNames()), Arrays.hashCode(getPrices()));
    }

    @Override
    public Auto clone() {
        try{
            Auto cloned = (Auto) super.clone();

            cloned.models = new Model[models.length];

            for (int i = 0; i < models.length; i++) {
                cloned.models[i] = new Model(models[i].model, models[i].price);
            }
            return cloned;
        }
        catch(CloneNotSupportedException e){
            throw new RuntimeException("clone not support",e);
        }

    }

    @Override
    public String getVehicleType() {
        return "Auto";
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String[] getNames() {
        String[] names = new String[models.length];
        for (int i = 0; i < models.length; i++) {
            names[i] = models[i].model;
        }
        return names;
    }
    
    @Override
    public void setModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (newName.equals(models[i].model)) {
                throw new DuplicateModelNameException(newName);
            }
            if (index == -1 && oldName.equals(models[i].model)) {
                index = i;
            }
        }
        if (index != -1) {
            models[index].model = newName;
        } else {
            throw new NoSuchModelNameException(oldName);
        }
    }

    @Override
    public int getSize() {
        return models.length;
    }

    @Override
    public void setModelPrice(String name, double price)
            throws NoSuchModelNameException {
        if (price < 1000000 || price > 10000000) {
            throw new ModelPriceOutOfBoundsException("вы не попали в промежуток цены");
        }
        for (int i = 0; i < models.length; i++) {
            if (models[i].model.equals(name)) {
                models[i].price = price;
                return;
            }
        }
        throw new NoSuchModelNameException("такой модели нет" + name);
    }

    @Override
    public double getModelPrice(String name) throws NoSuchModelNameException {
        for (int i = 0; i < models.length; i++) {
            if (models[i].model.equals(name)) {
                return models[i].price;
            }
        }
        throw new NoSuchModelNameException("такой модели нет " + name);
    }

    @Override
    public double[] getPrices() {
        double[] prices = new double[models.length];
        for (int i = 0; i < models.length; i++) {
            prices[i] = models[i].price;
        }
        return prices;
    }

    @Override
    public void addNewModel(String model, double price)
            throws DuplicateModelNameException {
        if (price < 1000000 || price > 10000000) {
            throw new ModelPriceOutOfBoundsException("вы не попали в промежуток цены");
        }
        for (int i = 0; i < models.length; i++) {
            if (models[i].model.equals(model)) {
                throw new DuplicateModelNameException("такая модель уже есть" + model);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        Model newModel = new Model(model, price);
        models[models.length - 1] = newModel;
    }

    @Override
    public void deleteModel(String model) throws NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (models[i].model.equals(model)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchModelNameException("такой модели нет");
        } else if (index == models.length - 1) {
            models = Arrays.copyOf(models, models.length - 1);
        } else {
            System.arraycopy(models, index + 1, models, index, models.length - 1 - index);
            models = Arrays.copyOf(models, models.length - 1);
        }
    }

    public class Model implements Serializable {
        private String model;
        private double price;

        public Model(String model, double price) {
            this.model = model;
            this.price = price;
        }
    }
}
