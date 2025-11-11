package Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Objects;
import java.util.Arrays;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Cuadro implements Vehicle {
    private String mark;
    private ArrayList<Model> models;

    public class Model implements Serializable {
        private String name;
        private double price;

        public Model(String model, double price) {
            this.name = model;
            this.price = price;
        }
    }

    public Cuadro(String mark, int size) {
        this.mark = mark;
        this.models = new ArrayList<>();
        Random random = new Random();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String name = String.format("%s%d", mark, i * 10);
                double price = random.nextDouble(100000, 500000);
                Model mod = new Model(name, price);
                models.add(mod);
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
        String[] names = new String[models.size()];
        for (int i = 0; i < models.size(); i++) {
            names[i] = models.get(i).name;
        }
        return names;
    }

    @Override
    public double[] getPrices() {
        double[] prices = new double[models.size()];
        for (int i = 0; i < models.size(); i++) {
            prices[i] = models.get(i).price;
        }
        return prices;
    }

    public int findIndex(String nameMod) {
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).name.equals(nameMod)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setModelPrice(String modelName, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 100000 || newPrice > 500000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 100000 до 500000");
        }
        int index = findIndex(modelName);
        if (index == -1) {
            throw new NoSuchModelNameException("Модель с именем " + modelName + " не найдена");
        }
        models.get(index).price = newPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for (int i = 0; i < models.size(); i++) {
            sb.append("Название: ").append(models.get(i).name).append(", Цена: ").append(models.get(i).price)
                    .append("\n");
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
    public String getVehicleType() {
        return "Cuadro";
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public double getModelPrice(String name) throws NoSuchModelNameException {
        int index = findIndex(name);
        if (index == -1) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        return models.get(index).price;
    }

    @Override
    public void addNewModel(String name, double price) throws DuplicateModelNameException {
        if (price < 100000 || price > 500000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 100000 до 500000");
        }
        int index = findIndex(name);
        if (index != -1) {
            throw new DuplicateModelNameException("Модель с именем " + name + " уже существует");
        }
        models.add(new Model(name, price));
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        int index = findIndex(name);
        if (index == -1) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        models.remove(index);
    }

    @Override
    public void setModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        int indexOld = findIndex(oldName);
        if (indexOld == -1) {
            throw new NoSuchModelNameException("Модель с именем " + oldName + " не найдена");
        }

        int indexNew = findIndex(newName);
        if (indexNew != -1 && !oldName.equals(newName)) {
            throw new DuplicateModelNameException("Модель с именем " + newName + " уже существует");
        }

        models.get(indexOld).name = newName;
    }

    @Override
    public Cuadro clone() {
        try {
            Cuadro cloned = (Cuadro) super.clone();
            if (this.models != null) {
                cloned.models = new ArrayList<>(this.models.size());
                for (int i = 0; i < this.models.size(); i++) {
                    if (this.models.get(i) != null) {
                        cloned.models.add(cloned.new Model(this.models.get(i).name, this.models.get(i).price));
                    }
                }
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone not support", e);
        }
    }
}
