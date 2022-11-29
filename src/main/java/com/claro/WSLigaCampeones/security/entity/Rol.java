package com.claro.WSLigaCampeones.security.entity;

import com.claro.WSLigaCampeones.security.enums.RolNombre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TBL_LIGA_SECURITY_ROL")
@NamedQuery(name="Rol.findByRolNombre", query="SELECT r FROM Rol r where r.rolNombre= :rolNombre")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_ROL") 
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="NOMBRE_ROL") 
    private RolNombre rolNombre;

    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
