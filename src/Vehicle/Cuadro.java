package Vehicle;
import java.util.ArrayList;

public class Cuadro implements Vehicle {
    private String mark;
    private ArrayList<String> modelNames;
    private ArrayList<Double> modelPrices;

    public Cuadro(String mark, int size){
        this.mark = mark;
        this.modelNames = new ArrayList<>();
        this.modelPrices = new ArrayList<>();
        if(size > 0){
            for(int i = 0; i < size; i++){
                String name = String.format("Hyundai%d", i);
                double price = 100000 + i * 50000;
                modelNames.add(name);
                modelPrices.add(price);
            }
        }
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public int getSize() {
        return modelNames.size();
    }

    @Override
    public String[] getNames() {
        return modelNames.toArray(new String[0]);
    }

    @Override
    public double[] getPrices() {
        double[] prices = new double[modelPrices.size()];
        for(int i = 0; i < modelPrices.size(); i++){
            prices[i] = modelPrices.get(i);
        }
        return prices;
    }

    @Override
    public void setModelPrice(String modelName, double newPrice) {
        int index = modelNames.indexOf(modelName);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + modelName + " не найдена.");
        }
        modelPrices.set(index, newPrice);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Марка: ").append(mark).append("\n");
        sb.append("Модели:").append("\n");
        for(int i = 0; i < modelNames.size(); i++){
            sb.append("Название: ").append(modelNames.get(i)).append(", Цена: ").append(modelPrices.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        if(!(mark.equals(other.getMark()))) return false;
        if(!(modelNames.size() == other.getSize())) return false;

        for(int i = 0; i < modelNames.size(); i++){
            String name = modelNames.get(i);
            double price = modelPrices.get(i);
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
    public double getModelPrice(String name) {
        int index = modelNames.indexOf(name);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + name + " не найдена.");
        }
        return modelPrices.get(index);
    }

    @Override
    public void addNewModel(String name, double price) {
        if(modelNames.contains(name)){
            throw new IllegalArgumentException("Модель с именем " + name + " уже существует.");
        }
        modelNames.add(name);
        modelPrices.add(price);
    }

    @Override
    public void deleteModel(String name) {
        int index = modelNames.indexOf(name);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + name + " не найдена.");
        }
        modelNames.remove(index);
        modelPrices.remove(index);
    }

    @Override
    public void setModelName(String oldName, String NewName) {
        int index = modelNames.indexOf(oldName);
        if(index == -1){
            throw new IllegalArgumentException("Модель с именем " + oldName + " не найдена.");
        }
        if(modelNames.contains(NewName)){
            throw new IllegalArgumentException("Модель с именем " + NewName + " уже существует.");
        }
        modelNames.set(index, NewName);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Cuadro cloned = (Cuadro) super.clone();
        cloned.modelNames = new ArrayList<>(this.modelNames);
        cloned.modelPrices = new ArrayList<>(this.modelPrices);
        return cloned;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(mark, modelNames, modelPrices);
    }
    
}
