package Vehicle;
import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;

public class Cuadro implements Vehicle {
    private String mark;
    private ArrayList<Model> models;

    public class Model implements Serializable{
        private String name;
        private double price;

        public Model(String model, double price) {
            this.name = model;
            this.price = price;
        }
    }

    public Cuadro(String mark, int size){
        this.mark = mark;
        this.models = new ArrayList<>();
        if(size > 0){
            for(int i = 0; i < size; i++){
                String name = String.format("%s%d", mark, i);
                double price = 100000 + i * 50000;
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

    public int findIndex(String nameMod){
        for(int i = 0; i < models.size(); i++){
            if(models.get(i).name.equals(nameMod)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setModelPrice(String modelName, double newPrice) throws NoSuchModelNameException{
        if(newPrice <= 0){
            throw new ModelPriceOutOfBoundsException("не та цена"); 
        }
        int index = findIndex(modelName);
        if (index == -1 ){
            throw new NoSuchModelNameException(modelName);
        }
        models.get(index).price = newPrice;
        
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for(int i = 0; i < models.size(); i++){
            sb.append("Название: ").append(models.get(i).name).append(", Цена: ").append(models.get(i).price).append("\n");
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
            double price = models.get(i).price;
            try {
                if(!name.equals(other.getNames()[i])) return false;
                if(!(price == other.getPrices()[i])) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
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
    public double getModelPrice(String name) throws NoSuchModelNameException{
        int index = findIndex(name);
        return models.get(index).price;
    }

    @Override
    public void addNewModel(String name, double price) throws DuplicateModelNameException{
        if(price <= 0){
            throw new ModelPriceOutOfBoundsException(name);
        }
        int index = findIndex(name);
        if(index == -1){
            throw new DuplicateModelNameException(name);
        }
        models.add(new Model(name,price));
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException{
        int index = findIndex(name);
        if(index == -1){
            throw new NoSuchModelNameException(name);
        }
        models.remove(index);
    }

    @Override
    public void setModelName(String oldName, String NewName) throws NoSuchModelNameException{
        int index = findIndex(oldName);
        if(index == -1){
            throw new NoSuchModelNameException(oldName);
        }
        for(int i = 0; i < models.size(); i++){
            if(models.get(i).name.equals(NewName) && !oldName.equals(NewName)){
                throw new NoSuchModelNameException(NewName);
            }
            if(models.get(i).name.equals(oldName)){
                index = i;
            }
        }
        models.get(index).name = NewName;
    }

    @Override
    public Object clone() {
        Cuadro res = null;
        try{
            Cuadro cloned = (Cuadro) super.clone();
            if(this.models != null){
                res.models = new ArrayList<>(this.models.size());
                for(int i = 0; i < models.size(); i++){
                    if(this.models.get(i) != null){
                        res.models.add((Model) this.models.get(i).clone());
                    }
                }  
            }
        }catch(CloneNotSupportedException e){
        }
        return res;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(mark, models, models);
    }
    
}
