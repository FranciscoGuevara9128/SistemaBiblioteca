package repository;

import entities.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutor {
    Autor guardar(Autor a);
    Optional<Autor> buscarPorId(Long id);
    List<Autor> listar();
    Autor actualizar(Autor a);
    void eliminar(Long id);
}
