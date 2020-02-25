package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.TableImageDBO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.utils.BarUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TableConverter extends AbstractConverter<TableDBO, TableDTO> {
	TableConverter() {
		super(new String[]{"images"});
	}

	@Override
	protected TableDTO constructDto() {
		return new TableDTO();
	}

	@Override
	protected TableDBO constructDbo() {
		return new TableDBO();
	}

	@Override
	public TableDTO convertToDto(final TableDBO tableDBO) {
		final TableDTO tableDTO = super.convertToDto(tableDBO);

		tableDTO.setImages(BarUtils.imagesFromDboToDto(tableDBO.getImages()));

		return tableDTO;
	}

	@Override
	public TableDBO convertToDbo(final TableDTO tableDTO) {
		final TableDBO tableDBO = super.convertToDbo(tableDTO);

		tableDBO.setImages(tableDTO.getImages()
				.stream().map(imageURL -> new TableImageDBO(tableDBO, imageURL)).collect(Collectors.toList()));

		return tableDBO;
	}
}