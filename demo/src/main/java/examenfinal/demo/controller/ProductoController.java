package examenfinal.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
     * @param apiVersion Versión del API mediante Custom Request Header
     * @return Lista de productos con nombre y cantidad usando HATEOAS
     */
    @GetMapping(headers = "X-API-VERSION=1")
    public ResponseEntity<CollectionModel<ProductoInventarioDTO>> obtenerInventarioPorSedeV1(
            @RequestParam Long idSede,
            @RequestHeader(value = "X-API-VERSION", defaultValue = "1") String apiVersion) {
        List<ProductoInventarioDTO> inventario = productoService.obtenerInventarioPorSede(idSede);
        
        if (inventario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Agregar links HATEOAS a cada producto
        List<ProductoInventarioDTO> inventarioConLinks = inventario.stream()
            .map(producto -> {
                // Link al producto individual (self)
                producto.add(linkTo(methodOn(ProductoController.class)
                    .obtenerInventarioPorSedeV1(idSede, apiVersion))
                    .withSelfRel());
                return producto;
            })
            .collect(Collectors.toList());
        
        // Crear CollectionModel con links
        CollectionModel<ProductoInventarioDTO> collectionModel = CollectionModel.of(inventarioConLinks);
        
        // Agregar link a la colección
        Link selfLink = linkTo(methodOn(ProductoController.class)
            .obtenerInventarioPorSedeV1(idSede, apiVersion))
            .withSelfRel();
        
        // Link para crear nuevo producto
        Link createLink = linkTo(methodOn(ProductoController.class)
            .crearProductoV1(null, apiVersion))
            .withRel("create");
        
        collectionModel.add(selfLink, createLink);
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    /**
     * Método POST que permite ingresar la información de productos del almacén
     * @param productoRequest DTO con datos del producto (nombre, descripción, cantidad, idSede)
     * @param apiVersion Versión del API mediante Custom Request Header
     * @return Producto creado
     */
    @PostMapping(headers = "X-API-VERSION=1")
    public ResponseEntity<Producto> crearProductoV1(
            @RequestBody ProductoRequestDTO productoRequest,
            @RequestHeader(value = "X-API-VERSION", defaultValue = "1") String apiVersion) {
        try {
            Producto nuevoProducto = productoService.crearProducto(productoRequest);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
