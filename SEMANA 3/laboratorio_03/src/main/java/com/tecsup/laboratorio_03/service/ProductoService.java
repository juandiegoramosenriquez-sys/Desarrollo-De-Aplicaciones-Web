package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Producto;
import com.tecsup.laboratorio_03.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository pr;

    //Listar
    public List<Producto> listar() {
        return pr.findAll();
    }

    //Guardar
    public Producto guardar(Producto p) {
        return pr.save(p);
    }

    //Actualizar

    //Buscar
    public Producto obtener(Long id) {
        return pr.findById(id).orElse(null);
    }

    //Eliminar
    public void eliminar(Long id) {
        pr.deleteById(id);
    }

}
