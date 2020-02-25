package gp.training.kim.bar.utils;

import gp.training.kim.bar.dbo.superclass.AbstractImage;

import java.util.List;
import java.util.stream.Collectors;

public class BarUtils {
	public static List<String> imagesFromDboToDto(final List<? extends AbstractImage> imagesFromDbo) {
		return imagesFromDbo.stream().map(AbstractImage::getImageURL).collect(Collectors.toList());
	}
}
