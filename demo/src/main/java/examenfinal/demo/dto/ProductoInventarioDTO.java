package examenfinal.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioDTO {
    private Long id;
    private String producto;
    private Integer cantidad;
}
