package Tecsup.laboratorio_02_tarea.service;

import Tecsup.laboratorio_02_tarea.model.Producto;
import Tecsup.laboratorio_02_tarea.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final IProductoRepository productoRepository;

    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
}