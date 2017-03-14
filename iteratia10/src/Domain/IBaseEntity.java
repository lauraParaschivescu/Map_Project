package Domain;

import java.io.Serializable;

public interface IBaseEntity<ID> {
    ID getId();
    void setId(ID id);
}