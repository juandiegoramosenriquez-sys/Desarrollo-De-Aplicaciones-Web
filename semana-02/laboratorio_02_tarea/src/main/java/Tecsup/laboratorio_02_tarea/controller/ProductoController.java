package Tecsup.laboratorio_02_tarea.controller;

import Tecsup.laboratorio_02_tarea.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String verProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "productos";
    }
}