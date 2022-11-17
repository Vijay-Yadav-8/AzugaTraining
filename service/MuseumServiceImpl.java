package com.azuga.museum.service;

import com.azuga.museum.dao.MuseumDao;
import com.azuga.museum.model.Museum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * This class is used as service layer between Dao Layer and Controller Layer
 */
@Service
@Transactional
public class MuseumServiceImpl implements MuseumService {

    public static final Logger logger = LogManager.getLogger(MuseumServiceImpl.class.getName());
    @Autowired
    MuseumDao dao;

    /**
     * This method is used to fetch all the data from the database table
     * @return - returns the data in the table as a list.
     */
    @Override
    public List<Museum> findAll() {
        long start = System.currentTimeMillis();
        List<Museum> list = dao.findAll();
        if (!list.isEmpty()){
            return list;
        }
        long end = System.currentTimeMillis();
        logger.info("Fetching the data from the database took {} ms to complete", (end - start));
        return null;
    }

    /**
     * This method is used to save the data into the database table
     * @return - returns the data in the table as an Object.
     */
    @Override
    public Museum save(Museum museum) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        logger.info("insertion of data into the database took {} ms to complete", (end - start));
        return dao.save(museum);
    }

    /**
     * This method is used to Update the data in the database table
     * @return - returns the updated data in the table as an Object.
     */
    @Override
    public Museum update(int objectID, Museum museum) {
        long start = System.currentTimeMillis();
        Optional<Museum> optionalMuseum = dao.findById(objectID);
        if(optionalMuseum.isPresent()){
            dao.save(museum);
            return museum;
        }
        long end = System.currentTimeMillis();
        logger.info("updation of data into the database took {} ms to complete", (end - start));
        return null;
    }

    /**
     * This method is used to delete the data from the database table
     * @return - returns the string confirming the deletion process.
     */
    @Override
    public String delete(int objectID) {
        long start = System.currentTimeMillis();
        Optional<Museum> optionalMuseum = dao.findById(objectID);
        if(optionalMuseum.isPresent()){
            dao.deleteById(objectID);
            return "message: deleted Successfully";
        }
        long end = System.currentTimeMillis();
        logger.info("deletion of data in the database took {} ms to complete", (end - start));
        return "message:Data with Id does not exist";

    }

    /**
     * This method is used to fetch the data of a particular data from the database table
     * @param objectID -id to fetch the record.
     * @return - returns the data in the table as an object.
     */
    @Override
    public Museum findByObjectID(int objectID) {
        long start = System.currentTimeMillis();
        if(dao.findById(objectID).isPresent()){
            return dao.findByObjectID(objectID);
        }
        long end = System.currentTimeMillis();
        logger.info("deletion of data in the database took {} ms to complete", (end - start));
        return null;
    }

}