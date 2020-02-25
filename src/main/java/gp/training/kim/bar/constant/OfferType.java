package gp.training.kim.bar.constant;

public enum OfferType {
	BEER("beer"),
	FOOD("food");

	private final String name;

	OfferType(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
