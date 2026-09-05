package Tecsup.laboratorio_02_tarea.repository;

import Tecsup.laboratorio_02_tarea.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}