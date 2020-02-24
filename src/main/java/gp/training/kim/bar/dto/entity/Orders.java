package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Orders {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long tableOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long userOrder;
}
