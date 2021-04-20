package en.ubb.pet_shop.web.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author radu.
 */
public class BaseEntity<ID> {
    private ID id;

    public BaseEntity(ID id) {
        this.id = id;
    }

    public BaseEntity() {}

    /**
     * @return a hash map containing pairs composed of the name of an attribute and it's value as a string.
     */
    public Map<String, String> getAttributes() {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("id", id.toString());
        return attributes;
    }

    /**
     * Creates a base entity
     * @param attributes -> uses these attributes to create the entity
     * @return -> the entity
     */
    public static Object loadEntity(Map<String, String> attributes) {
        BaseEntity<Integer> entity = new BaseEntity<>();
        entity.setId(Integer.parseInt(attributes.get("id")));
        return entity;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }

    public String InsertDatabase() {
        return null;
    }
    public String CsvToString()
    {
        return "id:"+this.getId()+"\n";
    }
}
