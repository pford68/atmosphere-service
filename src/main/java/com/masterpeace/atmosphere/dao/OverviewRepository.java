package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.Overview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Implemented by classes that access the Overview database view.
 */
public interface OverviewRepository extends CrudRepository<Overview, Long>{

    @Query("select o from Overview o where o.userid = :userId")
    Overview findByUserId(@Param("userId") long userId);
}
