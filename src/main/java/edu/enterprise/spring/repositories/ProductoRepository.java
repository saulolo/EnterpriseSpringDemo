package edu.enterprise.spring.repositories;

import edu.enterprise.spring.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//[73]. Repositorio de la entidad Producto.
/**
 * Repositorio de la entidad Producto para poder realizar las operaciones CRUD.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

// Creo una capa de Impl dentro de la capa de servicios donde se implementa la interfaz IProductoService ==> [74].
