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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "API para gestión de inventario de productos por sede")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @Operation(
        summary = "Consultar inventario por sede",
        description = "Obtiene el listado completo de productos con sus cantidades disponibles en una sede específica del almacén"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoInventarioDTO.class))),
        @ApiResponse(responseCode = "204", description = "No hay productos en el inventario de la sede especificada", 
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ProductoInventarioDTO>> obtenerInventarioPorSede(
        @Parameter(description = "ID de la sede del almacén", required = true, example = "1")
        @RequestParam Long idSede) {
        List<ProductoInventarioDTO> inventario = productoService.obtenerInventarioPorSede(idSede);
        
        if (inventario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Crear nuevo producto",
        description = "Permite ingresar la información de un nuevo producto al inventario del almacén junto con su cantidad inicial en stock"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o sede no encontrada", 
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del producto a crear (nombre, descripción, cantidad inicial y sede)",
            required = true,
            content = @Content(schema = @Schema(implementation = ProductoRequestDTO.class))
        )
        @RequestBody ProductoRequestDTO productoRequest) {
        try {
            Producto nuevoProducto = productoService.crearProducto(productoRequest);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
