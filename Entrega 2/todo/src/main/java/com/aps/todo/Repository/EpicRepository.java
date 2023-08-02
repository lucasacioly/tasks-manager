package com.aps.todo.Repository;

import com.aps.todo.daos.EpicDao;
import com.aps.todo.models.EpicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EpicRepository implements IEpicRepository{
    private static EpicRepository instance;

    @Autowired
    private EpicDao epicDao;

    public static synchronized EpicRepository getInstance(){
        if (instance == null) {
            instance = new EpicRepository();
        }
        return instance;
    };

    public List<EpicModel> getUserEpics (String userId){
        return this.epicDao.getUserEpics(userId);
    }

    public Optional<EpicModel> findById(Long id) {
        return this.epicDao.findById(id);
    }

    public EpicModel save(EpicModel epic) {
        return this.epicDao.save(epic);
    }


    public boolean existsById(Long id) {
        return this.epicDao.existsById(id);
    }

    public void deleteById(Long id) {
        this.epicDao.deleteById(id);
    }



}
