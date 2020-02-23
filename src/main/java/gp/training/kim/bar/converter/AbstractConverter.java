package gp.training.kim.bar.converter;

import org.springframework.beans.BeanUtils;

public abstract class AbstractConverter<Dbo, Dto> {

	private String[] ignoreProperties = new String[0];

	public AbstractConverter() {}

	public AbstractConverter(final String[] ignoreProperties) {
		this.ignoreProperties = ignoreProperties;
	}

	protected String[] getIgnoreProperties() {
		return ignoreProperties;
	}

	public Dto convertToDto(final Dbo dbo) {
		if (dbo == null) {
			return null;
		}

		final Dto dto = constructDto();

		BeanUtils.copyProperties(dbo, dto, ignoreProperties);

		return dto;
	};

	public Dbo convertToDbo(final Dto dto) {
		if (dto == null) {
			return null;
		}

		final Dbo dbo = constructDbo();

		BeanUtils.copyProperties(dto, dbo, ignoreProperties);

		return dbo;
	};

	protected abstract Dto constructDto();

	protected abstract Dbo constructDbo();
}