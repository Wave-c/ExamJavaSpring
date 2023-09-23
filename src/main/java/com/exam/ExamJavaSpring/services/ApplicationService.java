package com.exam.ExamJavaSpring.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.entyties.communications.Application;
import com.exam.ExamJavaSpring.repositories.ApplicationRepository;

import jakarta.transaction.Transactional;

@Service
public class ApplicationService 
{
    private ApplicationRepository applicationRepository;

    @Autowired
    public void setApplicationRepository(ApplicationRepository applicationRepository)
    {
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public int saveApplication(Room room, String buyerId)
    {
        try(Application application = new Application(UUID.randomUUID().toString(), room.getId(), buyerId, room.getUserId())) 
        {
            applicationRepository.save(application);
            return 0;
        } 
        catch (Exception e) 
        {
            return -1;
        }
    }

    @Transactional
    public List<Application> findApplicationBySellerId(String sellerId)
    {
        List<Application> applicationList = applicationRepository.findAll();
        for(int i = 0; i < applicationList.size(); i++)
        {
            if(!applicationList.get(i).getSellerId().equals(sellerId))
            {
                applicationList.remove(i);
                i--;
            }
        }
        return applicationList;
    }

    @Transactional
    public int deleteApplicationById(String id)
    {
        applicationRepository.deleteById(id);
        return 0;
    }

    @Transactional
    public Application findApplicationById(String id)
    {
        return applicationRepository.findById(id).get();
    }
}
