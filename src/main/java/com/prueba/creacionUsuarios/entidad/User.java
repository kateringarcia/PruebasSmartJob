package com.prueba.creacionUsuarios.entidad;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class User {
   
	@Id
    @GeneratedValue
    private UUID id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "created")
    private LocalDateTime created;
	
	@Column(name = "modified")
    private LocalDateTime modified;
	
	@Column(name = "lastLogin")
    private LocalDateTime lastLogin;
    
	@Column(name = "token")
	private String token;
    
	@Column(name = "isActive")
	private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones;
    
    @PrePersist
    public void prePersist() {
        if (this.created == null) {
            this.created = LocalDateTime.now();  // Se establece la fecha de creación al momento de persistir.
        }
        this.modified = LocalDateTime.now();  // La fecha de modificación también se actualiza.
        this.lastLogin = LocalDateTime.now();
        this.isActive = true;
    }
}

