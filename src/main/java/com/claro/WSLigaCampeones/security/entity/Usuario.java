package com.claro.WSLigaCampeones.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TBL_LIGA_SECURITY_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT d FROM Usuario d WHERE d.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.existsByNombreUsuario", query = "SELECT d FROM Usuario d WHERE d.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.existsByEmail", query = "SELECT d FROM Usuario d WHERE d.email = :email")})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_USUARIO") 
    private int id;
    @NotNull
    @Column(name="NOMBRE") 
    private String nombre;
    @NotNull
    @Column(name="NOMBRE_USUARIO",unique = true) 
    private String nombreUsuario;
    @NotNull
    @Column(name="EMAIL")
    private String email;
    @NotNull
    @Column(name="PASSWORD")
    private String password;
    private String cedula;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBL_LIGA_SECURITY_USUARIO_ROL", joinColumns = @JoinColumn(name = "ID_USUARIO"),
    inverseJoinColumns = @JoinColumn(name = "ID_ROL"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario() {
    }

//    public Usuario(@NotNull String cedula, @NotNull String password) {
//        this.cedula = cedula;
//        this.password = password;
//    }
    
    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
    
    
}
