package com.aluracursos.challengeliteralura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer nacimiento;
    private Integer defuncion;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros = new ArrayList<>();


    public Autor() {
    }



    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();;
        this.nacimiento = datosAutor.nacimiento();
        this.defuncion = datosAutor.defuncion();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    public Long getId() {
        return Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(Integer defuncion) {
        this.defuncion = defuncion;
    }

    @Override
    public String toString() {
        return
                "\nAutor: " + nombre +
                "\nAño de nacimiento: " + nacimiento +
                "\nAño de defunción: " + defuncion;
    }
}
