package gp.training.kim.bar.dbo;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "table")
public class TableDBO extends AbstractBarEntity {
    private String name;

    private Integer capacity;
}