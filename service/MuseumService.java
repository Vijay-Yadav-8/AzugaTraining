package com.azuga.museum.service;


import com.azuga.museum.model.Museum;

import java.util.List;

/**
 * This interface is used to specify the rules on the MuseumServiceImpl Class
 */
public interface MuseumService {
    List<Museum> findAll();
    Museum save(Museum museum);
    Museum update(int objectID,Museum museum);
    String delete(int objectID);

    Museum findByObjectID(int id);
}
