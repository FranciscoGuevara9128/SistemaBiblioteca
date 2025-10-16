package repository;

import entities.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoria {
    Categoria guardar(Categoria c);
    Optional<Categoria> buscarPorId(Long id);
    List<Categoria> listar();
    Categoria actualizar(Categoria c);
    void eliminar(Long id);
}
