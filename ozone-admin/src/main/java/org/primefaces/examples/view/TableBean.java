package org.primefaces.examples.view;  
   
import java.util.ArrayList; 
import java.util.List;  
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
  
import org.primefaces.examples.domain.Car;  

@ManagedBean(name="tableBean")
@SessionScoped
public class TableBean {  
	
	private final static String[] colors; 	  
	private final static String[] manufacturers;  
	private List<Car> carsSmall;
	private List<Car> cars;  
	private Car selectedCar;  
	private boolean editMode;  
  
    static {  
        colors = new String[10];  
        colors[0] = "Black";  
        colors[1] = "White";  
        colors[2] = "Green";  
        colors[3] = "Red";  
        colors[4] = "Blue";  
        colors[5] = "Orange";  
        colors[6] = "Silver";  
        colors[7] = "Yellow";  
        colors[8] = "Brown";  
        colors[9] = "Maroon";  
  
        manufacturers = new String[10];  
        manufacturers[0] = "Mercedes";  
        manufacturers[1] = "BMW";  
        manufacturers[2] = "Volvo";  
        manufacturers[3] = "Audi";  
        manufacturers[4] = "Renault";  
        manufacturers[5] = "Opel";  
        manufacturers[6] = "Volkswagen";  
        manufacturers[7] = "Chrysler";  
        manufacturers[8] = "Ferrari";  
        manufacturers[9] = "Ford";  
    }  
  
    public TableBean() {  
        carsSmall = new ArrayList<Car>();  
        cars = new ArrayList<Car>();  
          
        populateRandomCars(carsSmall, 9); 
        populateRandomCars(cars, 9);  
    }  
  
    private void populateRandomCars(List<Car> list, int size) {
//    	Car c = new Car();
//    	c.setModel("mercedes");
//    	c.setYear("2009");
//    	c.setManufacturer("man1");
//    	c.setColor("red");
//    	list.add(c);
//    	
//    	Car c2 = new Car();
//    	c2.setModel("BMW");
//    	c2.setYear("2011");
//    	c2.setManufacturer("man2");
//    	c2.setColor("blue");
//    	list.add(c2);
		for(int i = 0 ; i < size ; i++)
			list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));
	}  
    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }
    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
	}
	
	private String getRandomManufacturer() {
	        return manufacturers[(int) (Math.random() * 10)];
	}



  
    public Car getSelectedCar() {  
        return selectedCar;  
    }  
    public void setSelectedCar(Car selectedCar) {  
    	System.out.println("**setSelectedCar");
        this.selectedCar = selectedCar;  
    }  
  
    public List<Car> getCarsSmall() {  
        return carsSmall;  
    }

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public void setCarsSmall(List<Car> carsSmall) {
		this.carsSmall = carsSmall;
	}

	public String[] getColors() {
		return colors;
	}

	public String[] getManufacturers() {
		return manufacturers;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}  
	
    public int getRandomPrice() {  
        return (int) (Math.random() * 100000);  
    }  
	
    
}  