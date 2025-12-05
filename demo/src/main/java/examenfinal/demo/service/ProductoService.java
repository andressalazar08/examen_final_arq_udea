package examenfinal.demo.service;

import examenfinal.demo.dto.ProductoInventarioDTO;
import examenfinal.demo.model.Producto;
import examenfinal.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<ProductoInventarioDTO> obtenerInventarioPorSede(Long idSede) {
        List<Producto> productos = productoRepository.findBySedeId(idSede);
        return productos.stream()
                .map(producto -> new ProductoInventarioDTO(producto.getId(), producto.getNombre(), producto.getCantidad()))
                .collect(Collectors.toList());
    }
}
