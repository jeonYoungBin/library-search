package org.search.repository;

import org.search.controller.response.StatResponse;
import org.search.entity.DailyStat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
    Long countByQueryAndEventDateTimeBetween(String query, LocalDateTime eventDateTime, LocalDateTime eventDateTime2);

    // group by order by count
    @Query("select new org.search.controller.response.StatResponse(ds.query, count(ds.query)) " +
            "from DailyStat ds " +
            "group by ds.query order by count(ds.query) desc")
    List<StatResponse> findTopQuery(Pageable pageable);
}
