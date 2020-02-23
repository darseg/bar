package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.TableImageDBO;
import gp.training.kim.bar.dbo.superclass.AbstractImage;
import gp.training.kim.bar.dto.TableDTO;
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
	public TableDTO convertToDto(TableDBO tableDBO) {
		final TableDTO tableDTO = super.convertToDto(tableDBO);

		tableDTO.setImages(tableDBO.getImages().stream().map(AbstractImage::getImageURL).collect(Collectors.toList()));

		return tableDTO;
	}

	@Override
	public TableDBO convertToDbo(TableDTO tableDTO) {
		final TableDBO tableDBO = super.convertToDbo(tableDTO);

		tableDBO.setImages(tableDTO.getImages().stream().map(imageURL -> {
			final TableImageDBO tableImageDBO = new TableImageDBO();
			tableImageDBO.setImageURL(imageURL);

			return  tableImageDBO;
		}).collect(Collectors.toList()));

		return tableDBO;
	}
}