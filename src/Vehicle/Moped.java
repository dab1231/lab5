package Vehicle;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Iterator;
import java.util.Objects;
import java.util.Arrays;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Moped implements Vehicle {
    private String mark;
    private LinkedList<Model> models;

    public class Model implements Serializable {
        private String name;
        private double price;

        public Model(String model, double price) {
            this.name = model;
            this.price = price;
        }
    }

    public Moped(String mark, int size) {
        this.mark = mark;
        this.models = new LinkedList<>();
        Random random = new Random();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String name = String.format("%s%d", mark, i * 10);
                double price = random.nextDouble(80000, 250000);
                models.add(new Model(name, price));
            }
        }
    }

    public Model findModel(String name) {
        Iterator<Model> i = models.iterator();
        while (i.hasNext()) {
            Model model = i.next();
            if (model.name.equals(name)) {
                return model;
            }
        }
        return null;
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

    @Override
    public void setModelPrice(String modelName, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 80000 || newPrice > 250000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 80000 до 250000");
        }
        Model model = findModel(modelName);
        if (model == null) {
            throw new NoSuchModelNameException("Модель с именем " + modelName + " не найдена");
        }
        model.price = newPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for (Model model : models) {
            sb.append("Название: ").append(model.name).append(", Цена: ").append(model.price).append("\n");
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
        return "Moped";
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public void addNewModel(String name, double price) throws DuplicateModelNameException {
        if (price < 80000 || price > 250000) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть в диапазоне от 80000 до 250000");
        }
        if (findModel(name) != null) {
            throw new DuplicateModelNameException("Модель с именем " + name + " уже существует");
        }
        models.add(new Model(name, price));
    }

    @Override
    public double getModelPrice(String name) throws NoSuchModelNameException {
        Model model = findModel(name);
        if (model == null) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        return model.price;
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        Model model = findModel(name);
        if (model == null) {
            throw new NoSuchModelNameException("Модель с именем " + name + " не найдена");
        }
        models.remove(model);
    }

    @Override
    public void setModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        Model oldModel = findModel(oldName);
        if (oldModel == null) {
            throw new NoSuchModelNameException("Модель с именем " + oldName + " не найдена");
        }

        Model newModel = findModel(newName);
        if (newModel != null && !oldName.equals(newName)) {
            throw new DuplicateModelNameException("Модель с именем " + newName + " уже существует");
        }

        oldModel.name = newName;
    }

    @Override
    public Moped clone() {
        try {
            Moped cloned = (Moped) super.clone();
            if (this.models != null) {
                cloned.models = new LinkedList<>();
                for (Model model : this.models) {
                    if (model != null) {
                        cloned.models.add(cloned.new Model(model.name, model.price));
                    }
                }
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone not support", e);
        }
    }
}
