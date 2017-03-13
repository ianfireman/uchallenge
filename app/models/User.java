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
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified;
    
    public User(final String name, final String email, final String password, final String telefone){
    	this.name = name;
    	this.password = password;
    	this.email = email;
    	this.telefone = telefone;
    }
    
    public Long getId() {
        return id;
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

    @PrePersist
    protected void onCreate() {
    	modified = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	modified = new Date();
    }
}
