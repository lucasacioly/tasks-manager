package com.aps.todo.controlador;

import com.aps.todo.collection.EpicCollection;
import com.aps.todo.collection.UserCollection;
import com.aps.todo.models.EpicModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EpicControlador {

    private final EpicCollection epicCollection;
    private final UserCollection userCollection;

    @Autowired
    public EpicControlador(EpicCollection epicCollection, UserCollection userCollection) {
        this.epicCollection = epicCollection;
        this.userCollection = userCollection;
    }

    public ResponseEntity<List<EpicModel>> getAllEpics(String token) {
        var user = userCollection.validateUser(token);
        if (user != null){
            List<EpicModel> epics = epicCollection.getUserEpics(user.getId().toString());
            return new ResponseEntity<>(epics, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<EpicModel> getEpicById(String token, Long id) {
        var user = userCollection.validateUser(token);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        EpicModel epic = epicCollection.findById(id).orElse(null);

        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(epic, HttpStatus.OK);

    }


    public ResponseEntity<EpicModel> createEpic(String token, EpicModel epic) {
        var user = userCollection.validateUser(token);
        if (user != null){

            epic.setUserId(user.getId().toString());
            EpicModel createdEpic = epicCollection.save(epic);
            return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<EpicModel> updateEpic(String token, Long id, EpicModel epic) {
        var user = userCollection.validateUser(token);
        if (user != null){

            if (!epicCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            epic.setUserId(user.getId().toString());
            EpicModel updatedEpic = epicCollection.save(epic);
            return new ResponseEntity<>(updatedEpic, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Void> deleteEpic(String token, Long id) {
        var user = userCollection.validateUser(token);
        if (user != null){

            if (!epicCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            epicCollection.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
