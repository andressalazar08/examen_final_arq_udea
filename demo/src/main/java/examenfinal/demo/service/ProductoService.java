package examenfinal.demo.service;

import examenfinal.demo.dto.ProductoInventarioDTO;
import examenfinal.demo.dto.ProductoRequestDTO;
import examenfinal.demo.model.Producto;
import examenfinal.demo.model.Sede;
import examenfinal.demo.repository.ProductoRepository;
import examenfinal.demo.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private SedeRepository sedeRepository;
    
    public List<ProductoInventarioDTO> obtenerInventarioPorSede(Long idSede) {
        List<Producto> productos = productoRepository.findBySedeId(idSede);
        return productos.stream()
                .map(producto -> new ProductoInventarioDTO(producto.getId(), producto.getNombre(), producto.getCantidad()))
                .collect(Collectors.toList());
    }
    
    public Producto crearProducto(ProductoRequestDTO productoRequest) {
        // Verificar que la sede existe
        Sede sede = sedeRepository.findById(productoRequest.getIdSede())
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con ID: " + productoRequest.getIdSede()));
        
        // Crear el producto
        Producto producto = new Producto();
        producto.setNombre(productoRequest.getNombre());
        producto.setDescripcion(productoRequest.getDescripcion());
        producto.setCantidad(productoRequest.getCantidad());
        producto.setSede(sede);
        
        return productoRepository.save(producto);
    }
}
