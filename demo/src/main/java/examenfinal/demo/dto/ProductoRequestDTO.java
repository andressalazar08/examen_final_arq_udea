package examenfinal.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Long idSede;
}
