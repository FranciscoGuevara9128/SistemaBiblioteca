package repository.dao;

import entities.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibro {
    Libro guardar(Libro l);
    Optional<Libro> buscarPorId(Long id);
    List<Libro> listar();
    Libro actualizar(Libro l);
    void eliminar(Long id);

    List<Libro> listarConAutorYCategorias();
}
