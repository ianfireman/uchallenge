package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import java.util.Date;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"email"})) 
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(name="email", unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String telefone;
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified;
    
    public User(){
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
    	return name;
    }

    public String getEmail() {
        return email;
    }
    
    public String getTelefone() {
        return telefone;
    }
}
