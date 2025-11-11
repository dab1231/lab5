package Vehicle;

import java.util.HashMap;
import java.util.Random;
import java.util.Objects;
import java.util.Arrays;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Scooter implements Vehicle {
    private String mark;
    private HashMap<String, Double> models;

    public Scooter(String mark, int size) {
        this.mark = mark;
        this.models = new HashMap<>();
        Random random = new Random();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String name = String.format("%s%d", mark, i * 10);
                double price = random.nextDouble(50000, 300000);
                models.put(name, price);
            }
        }
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public int getSize() {
        return models.size();
    }

    @Override
    public String[] getNames() {
        return models.keySet().toArray(new String[0]);
    }

    @Override
    public double[] getPrices() {
        double[] prices = new double[models.size()];
        int index = 0;
        for (Double price : models.values()) {
            prices[index++] = price;
        }
        return prices;
    }

    @Override
    public void setModelPrice(String modelName, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 50000 || newPrice > 300000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 50000 до 300000");
        }
        if (!models.containsKey(modelName)) {
            throw new NoSuchModelNameException("Модель с именем " + modelName + " не найдена");
        }
        models.put(modelName, newPrice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for (HashMap.Entry<String, Double> entry : models.entrySet()) {
            sb.append("Название: ").append(entry.getKey()).append(", Цена: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Vehicle))
            return false;
        Vehicle other = (Vehicle) obj;
        if (!(mark.equals(other.getMark())))
            return false;
        if (!(models.size() == other.getSize()))
            return false;

        String[] thisNames = this.getNames();
        String[] otherNames = other.getNames();
        double[] thisPrices = this.getPrices();
        double[] otherPrices = other.getPrices();

        for (int i = 0; i < models.size(); i++) {
            if (!thisNames[i].equals(otherNames[i]) || thisPrices[i] != otherPrices[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, Arrays.hashCode(getNames()), Arrays.hashCode(getPrices()));
    }

    @Override
    public Scooter clone() {
        try {
            Scooter cloned = (Scooter) super.clone();
            cloned.models = new HashMap<>(this.models);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone not support", e);
        }
    }

    @Override
    public String getVehicleType() {
        return "Scooter";
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public double getModelPrice(String name) throws NoSuchModelNameException {
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        return models.get(name);
    }

    @Override
    public void addNewModel(String name, double price) throws DuplicateModelNameException {
        if (price < 50000 || price > 300000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 50000 до 300000");
        }
        if (models.containsKey(name)) {
            throw new DuplicateModelNameException("Модель с именем " + name + " уже существует");
        }
        models.put(name, price);
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        models.remove(name);
    }

    @Override
    public void setModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException {
        if (!models.containsKey(oldName)) {
            throw new NoSuchModelNameException("Модель с именем " + oldName + " не найдена");
        }
        if (models.containsKey(newName)) {
            throw new DuplicateModelNameException("Модель с именем " + newName + " уже существует");
        }
        double price = models.get(oldName);
        models.remove(oldName);
        models.put(newName, price);
    }
}
