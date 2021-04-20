package en.ubb.pet_shop.web.domain;

import java.util.HashMap;
import java.util.Map;

public class Transaction extends BaseEntity<Integer>{

    private int person;
    private int pet;
    private static int nextId = 0;

    public Transaction(int pet ,int person)
    {
        this.person = person;
        this.pet = pet;
        this.setId(GetNextId());
    }
    public Transaction(int id,int pet ,int person)
    {
        this.person = person;
        this.pet = pet;
        this.setId(id);
        // To preserve unique IDs.
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public int getPerson() {
        return person;
    }

    public int getPet() {
        return pet;
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

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("id", Integer.toString(getId()));
        attributes.put("pet", Integer.toString(pet));
        attributes.put("person", Integer.toString(person));
        return attributes;
    }

    /**
     * Creates a Pet entity
     * @param attributes -> uses these attributes to create the entity
     * @return -> the entity
     */
    public static Object loadEntity(Map<String, String> attributes) {
        int id = Integer.parseInt(attributes.get("id"));
        int pet = Integer.parseInt(attributes.get("pet"));
        int person = Integer.parseInt(attributes.get("person"));
        return new Transaction(id,pet, person);
    }

    @Override
    public String toString()
    {
        return " Customer with id " +
                person+ " bought pet with id " +
                pet + " " + "\n";
    }
    @Override
    public String InsertDatabase() {
        return " VALUES("+getId()+","+getPet()+","+getPerson()+");";
    }
    //todo change to id
    @Override
    public String CsvToString()
    {
        return "id:"+this.getId()+";"+
                "pet:"+this.getPet()+";"+
                "person:"+this.getPerson()+'\n';
    }
}
