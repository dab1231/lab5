package Vehicle;
import java.io.Serializable;
import java.util.LinkedList;

import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

import java.util.Iterator;
public class Moped implements Vehicle {
    private String mark;
    private LinkedList<Model> models;

    public class Model implements Serializable{
        private String name;
        private double price;

        public Model(String model, double price) {
            this.name = model;
            this.price = price;
        }
    }       

    public Moped(String mark, int size){
        this.mark = mark;
        this.models = new LinkedList<>();
        if(size > 0){
            for(int i = 0; i < size; i++){
                String name = String.format("%s%d", mark, i);
                double price = 80000 + i * 20000;
                models.add(new Model(name,price));
            }
        }
    }

    public Model findModel(String name){
        Iterator<Model> i = models.iterator();
        while(i.hasNext()){
            Model model = i.next();
            if(model.name.equals(name)){
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
        for(int i =0; i < models.size(); i++){
            names[i] = models.get(i).name;
        }
        return names;
    }

    

    @Override
    public double[] getPrices() {
        double[] prices = new double[models.size()];
        for(int i = 0; i < models.size(); i++){
            prices[i] = models.get(i).price;
        }
        return prices;
    }
    
    @Override
    public void setModelPrice(String modelName, double newPrice) throws NoSuchModelNameException{
        if(newPrice <= 0){
            throw new ModelPriceOutOfBoundsException(modelName);
        }
        Model model = findModel(modelName);
        if(model== null){
            throw new NoSuchModelNameException(modelName);
        }
        model.price = newPrice;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for(int i = 0; i < models.size(); i++){
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        if(!(mark.equals(other.getMark()))) return false;
        if(!(models.size() == other.getSize())) return false;
        for(int i = 0; i < models.size(); i++){
            String name = models.get(i).name;
            try {
                if(!name.equals(other.getNames()[i])) return false;
                if(this.getPrice != other.getPrices()[i]) return false;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return true;
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
    public void addNewModel(String name, double price) {
        if(models.contains(name)){
            throw new IllegalArgumentException("Модель с именем " + name + " уже существует.");
        }
        models.add(name);
    }

    @Override
    public double getModelPrice(String name) {
        int index = models.indexOf(name);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + name + " не найдена.");
        }
    }

    @Override
    public void deleteModel(String name) {
        int index = models.indexOf(name);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + name + " не найдена.");
        }
        models.remove(index);
    }

    @Override
    public void setModelName(String oldName, String NewName) {  
        int index = models.indexOf(oldName);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + oldName + " не найдена.");
        }
        if(models.contains(NewName)){
            throw new IllegalArgumentException("Модель с именем " + NewName + " уже существует.");
        }
        models.set(index, NewName);
    }

    @Override
    public Object clone() {
        try{
            Moped cloned = (Moped) super.clone();
            cloned.models = new LinkedList<>(this.models);
            return cloned;
        }
        catch(CloneNotSupportedException e){
            throw new RuntimeException("clone not support",e);
        }

    }

    @Override
    public int hashCode() {
    }
    
}
