package com.azuga.museum.controller;

import com.azuga.museum.model.Museum;
import com.azuga.museum.service.MuseumService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myresource")
public class MuseumController {

    public static final Logger logger = LogManager.getLogger(MuseumController.class.getName());

    @GetMapping("/demo")
    public String demo(){
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        logger.info("responding to the request took {} ms to complete", (end - start));
        return "hello from demo";
    }
    @Autowired
    MuseumService service;

    @GetMapping("/read")
    public ResponseEntity<List<Museum>> read(){
        long start = System.currentTimeMillis();
        List<Museum> list =service.findAll();
        if(list!=null) {
            long end = System.currentTimeMillis();
            logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        long end = System.currentTimeMillis();
        logger.error("An error occurred while working with database");
        logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Museum> readByID(@PathVariable("id") Integer id){
        long start = System.currentTimeMillis();
        if(id!=null) {
            Museum museum = service.findByObjectID(id);
            if (museum != null) {
                long end = System.currentTimeMillis();
                logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
                return new ResponseEntity<>(museum, HttpStatus.OK);
            }
            long end = System.currentTimeMillis();
            logger.error("An error occurred while working with database");
            logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long end = System.currentTimeMillis();
        logger.error("An error occurred while working with database");
        logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @PostMapping("/insert")
    public ResponseEntity<Museum> save(@RequestBody Museum museum){
        long start = System.currentTimeMillis();
        if(museum!=null){
            Museum museum1 = service.save(museum);
            if(museum1!=null) {
                long end = System.currentTimeMillis();
                logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
                return new ResponseEntity<>(museum1, HttpStatus.OK);
            }
            long end = System.currentTimeMillis();
            logger.error("An error occurred while working with database");
            logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long end = System.currentTimeMillis();
        logger.info("responding to the endpoint i.e, /insert took {} ms to complete", (end - start));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Museum> update(@PathVariable("id") Integer objectID,@RequestBody Museum museum){
        long start = System.currentTimeMillis();
        if(objectID != null && museum != null){
            Museum museum1 = service.update(objectID,museum);
            if(museum1 != null) {
                long end = System.currentTimeMillis();
                logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
                return new ResponseEntity<>(museum1, HttpStatus.OK);
            }
            long end = System.currentTimeMillis();
            logger.error("An error occurred while working with database");
            logger.info("responding to the endpoint i.e, /read/id took {} ms to complete", (end - start));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long end = System.currentTimeMillis();
        logger.error("An error occurred while working with database");
        logger.info("responding to the endpoint i.e, /update/id took {} ms to complete", (end - start));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int objectID){
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        logger.info("responding to the endpoint i.e, /delete/id took {} ms to complete", (end - start));
        return new ResponseEntity<>(service.delete(objectID),HttpStatus.OK);
    }

}
