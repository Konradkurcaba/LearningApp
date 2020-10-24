package pl.kurcaba.learn.helper.common.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable
{
    @Id
    private String uuid = UUID.randomUUID().toString();

    @Version
    private Long version;

    public String getUuid()
    {
        return uuid;
    }

    /**
     * It should be used only for deserialization.
     * @param aNewUuid new uuid.
     */
    protected void setUuid(String aNewUuid)
    {
        uuid = aNewUuid;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid);
    }
}
