package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.TableDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableDBO, Long> {
	String GET_NOT_RESERVED_PRIVATE_TABLES = "select t.* from tables t " +
			"left outer join orders o on o.table_id = t.id and o.start < :end and o.end > :start " +
			"where t.is_private and t.capacity >= :capacity and o.id is null";

	String GET_NOT_RESERVED_PUBLIC_TABLES = "select t.* from tables t " +
			"where not t.is_private and " +
			"(t.capacity - (select count(0) from orders o where o.start < :end and o.end > :start and o.table_id = t.id)) >= :capacity";

	@Query(value = GET_NOT_RESERVED_PRIVATE_TABLES, nativeQuery = true)
	List<TableDBO> getNotReservedPrivateTablesForTime(@Param("capacity") Integer capacity,
													  @Param("start") LocalDateTime start,
													  @Param("end") LocalDateTime end);

	@Query(value = GET_NOT_RESERVED_PUBLIC_TABLES, nativeQuery = true)
	List<TableDBO> getPublicTablesWithEnoughPlacesForTime(@Param("capacity") Integer capacity,
														  @Param("start") LocalDateTime start,
														  @Param("end") LocalDateTime end);
}