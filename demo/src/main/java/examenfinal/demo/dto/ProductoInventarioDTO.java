package examenfinal.demo.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductoInventarioDTO extends RepresentationModel<ProductoInventarioDTO> {
    private Long id;
    private String producto;
    private Integer cantidad;
}
