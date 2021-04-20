package en.ubb.pet_shop.web.domain;

import java.util.HashMap;
import java.util.Map;

public class Pet extends BaseEntity<Integer>{
    private static int nextId = 0;
    private String name;
    private String species;
    private double sellingPrice;


    public Pet(String name, String species, double sellingPrice) {
        this.name = name;
        this.species = species;
        this.sellingPrice = sellingPrice;
        this.setId(GetNextId());
    }

    public Pet(int id,String name, String species, double sellingPrice)
    {
        this.name = name;
        this.species = species;
        this.sellingPrice = sellingPrice;
        this.setId(id);
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("id", Integer.toString(getId()));
        attributes.put("name", name);
        attributes.put("species", species);
        attributes.put("sellingprice", Double.toString((int)sellingPrice));
        return attributes;
    }

    /**
     * Creates a Pet entity
     * @param attributes -> uses these attributes to create the entity
     * @return -> the entity
     */
    public static Object loadEntity(Map<String, String> attributes) {
        int id = Integer.parseInt(attributes.get("id"));
        String name = attributes.get("name");
        String species = attributes.get("species");
        double sellingprice = Double.parseDouble(attributes.get("sellingprice"));
        return new Pet(id, name, species ,sellingprice);
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return " Pet with " +
                " id=" + getId() +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", sellingPrice=" + sellingPrice + '\n';
    }
    @Override
    public String InsertDatabase() {
        return " VALUES("+getId()+",'"+getName()+"','"+getSpecies()+"',"+getSellingPrice()+");";
    }
    public String CsvToString()
    {
        return "id:"+this.getId()+";"+
                "name:"+this.getName()+";"+
                "species:"+this.getSpecies()+";"
        +"sellingprice:"+this.getSellingPrice()+'\n';
    }
}