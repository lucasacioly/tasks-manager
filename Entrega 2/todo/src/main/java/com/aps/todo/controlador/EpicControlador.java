package com.aps.todo.controlador;

import com.aps.todo.models.EpicModel;
import com.aps.todo.repositories.EpicRepository;
import com.aps.todo.repositories.EpicRepositoryFactory;
import com.aps.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EpicControlador {

    private final EpicRepository epicRepository;
    private final UserRepository userRepository;

    @Autowired
    public EpicControlador(EpicRepositoryFactory repositoryFactory, UserRepository userRepository) {
        this.epicRepository = repositoryFactory.createEpicRepository();
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<EpicModel>> getAllEpics(String token) {
        var user = userRepository.validateUser(token);
        if (user != null){
            List<EpicModel> epics = epicRepository.getUserEpics(user.getId().toString());
            return new ResponseEntity<>(epics, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<EpicModel> getEpicById(String token, Long id) {
        var user = userRepository.validateUser(token);

        if (user != null){
            EpicModel epic = epicRepository.findById(id).orElse(null);

            if (epic == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(epic, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<EpicModel> createEpic(String token, EpicModel epic) {
        var user = userRepository.validateUser(token);
        if (user != null){

            EpicModel createdEpic = epicRepository.save(epic);
            return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<EpicModel> updateEpic(String token, Long id, EpicModel epic) {
        var user = userRepository.validateUser(token);
        if (user != null){

            if (!epicRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            EpicModel updatedEpic = epicRepository.save(epic);
            return new ResponseEntity<>(updatedEpic, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Void> deleteEpic(String token, Long id) {
        var user = userRepository.validateUser(token);
        if (user != null){

            if (!epicRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            epicRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
