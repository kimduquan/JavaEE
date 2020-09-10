/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.roles;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import epf.tasks.Task;
import epf.work_products.Artifact;
import javax.persistence.NamedNativeQuery;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Role",
        title = "Role"
)
@Entity
@Table(name = "_ROLE")
@JsonbPropertyOrder({
    "name",
    "additionallyPerforms",
    "modifies"
})
@NamedNativeQuery(
        name = "Role.GetUserRoles", 
        query = "SELECT GRANTEDROLE \n" +
                "FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "WHERE \n" +
                "    GRANTEETYPE = 'ROLE' \n" +
                "    AND (\n" +
                "        GRANTEE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        OR\n" +
                "        GRANTEDROLE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        )\n" +
                "UNION\n" +
                "SELECT GRANTEE \n" +
                "FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "WHERE \n" +
                "    GRANTEETYPE = 'ROLE' \n" +
                "    AND (\n" +
                "        GRANTEE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        OR\n" +
                "        GRANTEDROLE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        );"
)
public class Role {

	@Column(name = "NAME")
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "ROLE_ADDITIONALLY_PERFORMS",
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "TASK"
            )
    )
    private List<Task> additionallyPerforms;
    
    @ManyToMany
    @JoinTable(
            name = "ROLE_MODIFIES",
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> modifies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Name("Additionally_Performs")
    public List<Task> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(List<Task> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }
    
    @Name("Modifies")
    public List<Artifact> getModifies(){
        return modifies;
    }

    public void setModifies(List<Artifact> modifies) {
        this.modifies = modifies;
    }
}
