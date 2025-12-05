package examenfinal.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import examenfinal.demo.dto.ProductoInventarioDTO;
import examenfinal.demo.dto.ProductoRequestDTO;
import examenfinal.demo.model.Producto;
import examenfinal.demo.service.ProductoService;

@RestController
@RequestMapping("/api/inventario")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    /**
     * Método GET que consulta el inventario de productos de un almacén determinado
     * @param idSede ID de la sede del almacén
     * @return Lista de productos con nombre y cantidad
     */
    @GetMapping
    public ResponseEntity<List<ProductoInventarioDTO>> obtenerInventarioPorSede(@RequestParam Long idSede) {
        List<ProductoInventarioDTO> inventario = productoService.obtenerInventarioPorSede(idSede);
        
        if (inventario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }
    
    /**
     * Método POST que permite ingresar la información de productos del almacén
     * @param productoRequest DTO con datos del producto (nombre, descripción, cantidad, idSede)
     * @return Producto creado
     */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoRequestDTO productoRequest) {
        try {
            Producto nuevoProducto = productoService.crearProducto(productoRequest);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
