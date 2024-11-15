package com.aluracursos.challengeliteralura.main;

import com.aluracursos.challengeliteralura.model.*;
import com.aluracursos.challengeliteralura.repository.AutorRepository;
import com.aluracursos.challengeliteralura.repository.LibroRepository;
import com.aluracursos.challengeliteralura.service.ConsumoAPI;
import com.aluracursos.challengeliteralura.service.ConvierteDatos;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;


public class Main {
    private String mensajeError = "Opción incorrecta. Intente nuevamente.";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner lectura = new Scanner(System.in);
    private ConvierteDatos conversor = new ConvierteDatos();
    private String json;
    private DatosLibro datosLibro;
    private Libro libro;
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private Integer opcion = 9;

    @Autowired
    private EntityManager entityManager;

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void menu() {
        while (opcion != 0) {
            System.out.println(
                    """
                            ********************* Menú ***************************
                            *                                                    *
                            *   1.- Buscar libro por título                      *
                            *   2.- Listar libros registrados                    *
                            *   3.- Listar autores registrados                   *
                            *   4.- Listar autores vivos en un determinado año   *
                            *   5.- Listar libros por idioma                     *
                            *   6.- Listar top 5 de libros registrados           *
                            *   7.- Buscar libro registrado por título           *
                            *   8.- Buscar autor por nombre                      *
                            *   9.- Listar autores vivos entre años dados        *
                            *   10.- Mostrar estadísticas de libros registrados  *
                            *                                                    *
                            *   0.- Salir                                        *
                            *                                                    *
                            ******************************************************
                            Ingrese el número de la opción deseada:
                            """
            );
            try {
                opcion = lectura.nextInt();
            } catch (Exception e) {
                System.out.println(mensajeError);
            }
            lectura.nextLine();
                switch (opcion) {
                    case 1:
                        registrarLibroBuscado();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresPorAnho();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        listarTop5Libros();
                        break;
                    case 7:
                        libroPorTitulo();
                        break;
                    case 8:
                        buscarAutorPorNombre();
                        break;
                    case 9:
                        listarAutoresVivosEntreAnhos();
                        break;
                    case 10:
                        mostrarEstadisticasLibrosRegistrados();
                        break;
                    case 0:
                        System.out.println("La app está finalizando su ejecución.");
                        break;
                    default:
                        System.out.println(mensajeError);
                }

        }
    }


    public DatosLibro getDatosLibro() {
        System.out.println("Escribe el título del libro que buscas: ");
        var titulo = lectura.nextLine();
        String json;
        DatosLibro datos;
        try {
            json = consumoAPI.obtenerDatos(titulo.replace(" ", "+"));
            datos = conversor.obtenerDatos(json, DatosLibro.class);
            return datos;
        } catch (Exception e) {
            System.out.println(mensajeError);
        }
        return null;
    }



    public void registrarLibroBuscado() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función busca el libro dentro de la API     ||
                        ||  de Gutendex, toma los datos para crear una       ||
                        ||  instancia de la clase Libro y de la clase Autor, ||
                        ||  y almacena los datos de dichas clases en la DB.  ||
                        |=====================================================|
                        """
        );
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro != null) {
            Optional<Autor> nombreAutor = autorRepositorio.findByNombre(datosLibro.autores().get(0).nombre());

            Libro libro = new Libro();
            libro.setTitulo(datosLibro.titulo());
            libro.setIdioma(Idioma.fromString(datosLibro.idioma().get(0)));
            libro.setCantidadDeDescargas(datosLibro.cantidadDeDescargas());

            if (nombreAutor.isPresent()) {
                // Autor existente: asignar al libro
                Autor autorExistente = nombreAutor.get();
                libro.setAutor(autorExistente);
            } else {
                // Nuevo autor: guardar antes de asignarlo al libro
                Autor nuevoAutor = new Autor(datosLibro.autores().get(0));
                nuevoAutor = autorRepositorio.save(nuevoAutor);
                libro.setAutor(nuevoAutor);
            }
            try {
                libroRepositorio.save(libro);
                System.out.println(datosLibro);
                System.out.println(" ");
            } catch (Exception e) {
                System.out.println("Libro ya registrado.");
            }
        } else {
            System.out.println(mensajeError);
        }
    }


    public void listarLibrosRegistrados(){
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista de libros        ||
                        ||               registrados en la DB.               ||
                        |=====================================================|
                        """
        );
        libros = libroRepositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
        System.out.println(" ");
    }

    public void listarAutoresRegistrados(){
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista de autores       ||
                        ||  registrados en la DB junto con una lista por     ||
                        ||  cada autor de sus libros registrados en la DB.   ||
                        |=====================================================|
                        """
        );
        autores = autorRepositorio.findAll();
        System.out.println("Autores registrados:");
        for (int i = 0; i < autores.size(); i++){
            List<Libro> librosPorAutor = libroRepositorio.findLibrosByAutorId(autores.get(i).getId());
            System.out.println("\n" + autores.get(i).toString());
            System.out.println("Libros registrados: ");
            librosPorAutor.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
        System.out.println(" ");
    }

    private void listarAutoresPorAnho() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista de autores       ||
                        ||  registrados en la DB que hayan estado vivos en   ||
                        ||  el año ingresado, junto con su edad ese año, su  ||
                        ||  fecha de defunción y una lista por cada autor de ||
                        ||  sus libros registrados en la DB.                 ||
                        |=====================================================|
                        """
        );
        System.out.println("Ingrese el año en el cual busca autores: ");
        var anho = lectura.nextInt();
        lectura.nextLine();
        List<Autor> autoresVivos = autorRepositorio.listarAutoresPorAnho(anho);
        System.out.println("Autores vivos en el " + anho + ": ");
        autoresVivos.forEach(a -> System.out.println("\nAutor: " + a.getNombre() + "\nEdad: " + (anho - a.getNacimiento())
                                + "\nFecha de defunción: " + a.getDefuncion() + "\n" + libroRepositorio
                                                             .findLibrosByAutorId(a.getId())
                                                     + "\n**************************************"));
    }

    private void listarLibrosPorIdioma() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista de libros        ||
                        ||  registrados en la DB filtrados por su idioma.    ||
                        |=====================================================|
                        """
        );
        System.out.println("Ingrese las siglas de la opción deseada: ");
        System.out.println(
                """
                        es - Español
                        en - Inglés
                        fr - Francés
                        ja - Japonés
                        it - Italiano
                        ru - Ruso
                        br - Portugués(Brasil)
                        """
        );
        var idioma = lectura.nextLine();
        try {

            List<Libro> librosPorIdioma = libroRepositorio.findByIdioma(Idioma.fromString(idioma));
            if (!librosPorIdioma.isEmpty()) {
                System.out.println("\n******* Libros en " + Idioma.fromString(idioma).toString() + " *******");
                librosPorIdioma.forEach(l -> System.out.println("\n" + l.toString()));
            } else {
                System.out.println("No se han encontrado libros en ese idioma.");
            }
        } catch (Exception e) {
            System.out.println(mensajeError);
        }
    }

    private void listarTop5Libros() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista con los 5 libros ||
                        ||  registrados en la DB con mayor cantidad de       ||
                        ||  descargas, ordenados de mayor a menor.           ||
                        |=====================================================|
                        """
        );
        List<Libro> top5 = libroRepositorio.findTopCinco();
        System.out.println("***** Top 5 Libros Registrados *****");
        top5.forEach(l -> System.out.println("\n" + l.toString()));
    }

    private void libroPorTitulo() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve los datos del libro        ||
                        ||  registrado en la DB, buscando según el título.   ||
                        |=====================================================|
                        """
        );
        System.out.println("Ingrese el título del libro que busca: ");
        var busqueda = lectura.nextLine();
        Optional<Libro> libroBuscado = libroRepositorio.findByTitulo(busqueda);
        if (libroBuscado.isPresent()){
            System.out.println(libroBuscado);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void buscarAutorPorNombre() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve los datos del autor        ||
                        ||  registrado en la DB, buscado según el nombre     ||
                        ||  junto con una lista de sus libros registrados.   ||
                        |=====================================================|
                        """
        );
        System.out.println("Ingrese el nombre del autor que busca: ");
        var busqueda = lectura.nextLine();
        Optional<Autor> autorBuscado = autorRepositorio.findByNombre(busqueda);
        if (autorBuscado.isPresent()){
            System.out.println("El autor que busca es: ");
            System.out.println(autorBuscado.get().toString());
            List<Libro> librosPorAutor = libroRepositorio.findLibrosByAutorId(autorBuscado.get().getId());
            System.out.println("Libros registrados: ");
            librosPorAutor.forEach(l -> System.out.println("- " + l.getTitulo()));
        } else {
            System.out.println("Autor no encontrado.");
        }
    }

    private void listarAutoresVivosEntreAnhos() {
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve una lista de autores       ||
                        ||  que hayan estado vivos entre los años ingresados.||
                        ||  Lo cual no significa que hayan nacido y muerto   ||
                        ||  exclusivamente dentro de esos años.              ||
                        |=====================================================|
                        """
        );
        System.out.println("Ingrese el año a partir del cual busca: ");
        var min = lectura.nextInt();
        lectura.nextLine();
        System.out.println("Ingrese el año hasta el cual busca: ");
        var max = lectura.nextInt();
        lectura.nextLine();
        List<Autor> autoresEncontrados = autorRepositorio.listarAutoresVivosEntreAnhos(min, max);
        if (autoresEncontrados.size() > 0){
            autoresEncontrados.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores vivos dentro de los parámetros ingresados.");
        }
    }

    public void mostrarEstadisticasLibrosRegistrados(){
        System.out.println(
                """
                        |=====================================================|
                        ||  Esta función devuelve datos estadísticos sobre   ||
                        ||  descargas de los libros registrados en la DB.    ||
                        ||  Máximo, mínimo, total(cuenta) y promedio.        ||
                        |=====================================================|
                        """);
        libros = libroRepositorio.findAll();
        DoubleSummaryStatistics est = libros.stream()
                .filter(l -> l.getCantidadDeDescargas() > 0.0)
                .collect(Collectors.summarizingDouble(Libro::getCantidadDeDescargas));
        System.out.println("* Máximo de descargas de un libro: " + est.getMax());
        System.out.println("* Mínimo de descargas de un libro: " + est.getMin());
        System.out.println("* Total de descargas entre " + est.getCount() + " libros: " + est.getSum());
        System.out.println("* Promedio de descargas de libros: " + Math.round(est.getAverage()));
    }

}
