package gp.training.kim.bar.mockData;

import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.TableImageDBO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	public final static Map<Long, TableDBO> tables = new HashMap<>() {{
		long imgId = 1;
		for (long index = 1; index < 7; index++) {
			final TableDBO table = new TableDBO();
			table.setId(index);
			table.setPrivate(index != 1);
			table.setCapacity((int) (3 + (index % 4)));
			table.setName("Table " + index);
			table.setDescription("Table " + index + " description");

			final List<TableImageDBO> images = new ArrayList<>();
			table.setImages(images);

			for (long j = 0; j < index % 3; j++) {
				final TableImageDBO image = new TableImageDBO();
				image.setId(imgId);
				image.setTable(table);
				image.setImageURL("images/table/" + index + "/" + j + ".jpg");
				images.add(image);
				imgId++;
			}

			put(index, table);
		}
	}};
}
