package gp.training.kim.bar.converter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractConverter<Dbo, Dto> {
	
	public abstract Dto convertToDto(final Dbo dbo);
	
	public abstract Dbo convertToDbo(final Dto dto);
}