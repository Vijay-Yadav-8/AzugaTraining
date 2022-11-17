package com.azuga.museum.dao;

import com.azuga.museum.model.Museum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to specify the spring data Jpa rules.
 */
@Repository
public interface MuseumDao extends JpaRepository<Museum,Integer> {
    Museum findByObjectID(int objectID);
}
