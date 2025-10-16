package run;

import entities.Autor;
import entities.Categoria;
import entities.Libro;
import repository.dao.*;
import repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        IAutor autorDao = new AutorDao();
        ICategoria categoriaDao = new CategoriaDao();
        ILibro libroDao = new LibroDao();

        // Categorías
        Categoria ficcion = Categoria.builder().nombre("Ficción").build();
        Categoria ciencia = Categoria.builder().nombre("Ciencia").build();
        categoriaDao.guardar(ficcion);
        categoriaDao.guardar(ciencia);

        // Autores
        Autor a1 = Autor.builder()
                .nombre("Isabel Allende")
                .nacionalidad("Chile")
                .fechaNacimiento(LocalDate.of(1942, 8, 2))
                .build();

        Autor a2 = Autor.builder()
                .nombre("Carl Sagan")
                .nacionalidad("Estados Unidos")
                .fechaNacimiento(LocalDate.of(1934, 11, 9))
                .build();

        autorDao.guardar(a1);
        autorDao.guardar(a2);


        // Libros y relaciones
        Libro l1 = Libro.builder().titulo("La casa de los espíritus").anioPublicacion(1982).autor(a1).build();
        l1.addCategoria(ficcion);


        Libro l2 = Libro.builder().titulo("De amor y de sombra").anioPublicacion(1984).autor(a1).build();
        l2.addCategoria(ficcion);


        Libro l3 = Libro.builder().titulo("Cosmos").anioPublicacion(1980).autor(a2).build();
        l3.addCategoria(ciencia);


        libroDao.guardar(l1);
        libroDao.guardar(l2);
        libroDao.guardar(l3);

        // Consulta: libros con autor y categorías
        List<Libro> libros = libroDao.listarConAutorYCategorias();
        for (Libro l : libros) {
            String cats = l.getCategorias().stream()
                    .map(Categoria::getNombre)
                    .sorted()
                    .collect(Collectors.joining(", "));
            System.out.printf("Libro: %s | Autor: %s | Categorías: %s%n",
                    l.getTitulo(), l.getAutor().getNombre(), cats.isEmpty() ? "-" : cats);
        }
    }
}
