package Vehicle;

import Exceptions.DuplicateModelNameException;
import Exceptions.NoSuchModelNameException;

public interface Vehicle extends java.io.Serializable, Cloneable {
    String getVehicleType();

    String getMark();

    void setMark(String mark);

    String[] getNames();

    double getModelPrice(String name) throws NoSuchModelNameException;

    void setModelPrice(String name, double price) throws  NoSuchModelNameException;

    double[] getPrices();

    void addNewModel(String name, double price) throws DuplicateModelNameException;

    void deleteModel(String name) throws NoSuchModelNameException;

    int getSize();

    void setModelName(String oldName, String NewName) throws DuplicateModelNameException, NoSuchModelNameException;

    Object clone() throws CloneNotSupportedException;
}
