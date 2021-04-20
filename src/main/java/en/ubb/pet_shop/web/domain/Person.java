package en.ubb.pet_shop.web.domain;

import java.util.HashMap;
import java.util.Map;

public class Person extends BaseEntity<Integer>{
    private static int nextId = 0;
    private String name;
    private double budget;


    public Person(String name, double budget) {
        this.name = name;
        this.budget = budget;
        this.setId(GetNextId());
    }

    public Person(int id, String name,double budget)
    {
        this.name = name;
        this.budget = budget;
        this.setId(id);
        // To preserve unique IDs.
        if (id >= nextId) {
            nextId = id + 1;
        }
    }


    /**
     Function to assure that the id's are unique
     Input Parameters type: none
     Output Parameters type: int
     Returns: an int representing the current id
     */
    private int GetNextId()
    {
        int id = nextId;
        nextId++;
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("id", Integer.toString(getId()));
        attributes.put("name", name);
        attributes.put("budget", Double.toString(budget));
        return attributes;
    }

    /**
     * Creates a person entity
     * @param attributes -> uses these attributes to create the entity
     * @return -> the entity
     */
    public static Object loadEntity(Map<String, String> attributes) {
        int id = Integer.parseInt(attributes.get("id"));
        double budget = Double.parseDouble(attributes.get("budget"));
        String name = attributes.get("name");
        return new Person(id, name, budget);
    }

    @Override
    public String toString() {
        return " Person with " +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", budget=" + budget + '\n';
    }

    @Override
    public String InsertDatabase() {
        return " VALUES("+getId()+",'"+getName()+"',"+getBudget()+");";
    }
    public String CsvToString()
    {
        return "id:"+this.getId()+";"+
                "name:"+this.getName()+";"+
                "budget:"+this.getBudget()+'\n';
    }
}