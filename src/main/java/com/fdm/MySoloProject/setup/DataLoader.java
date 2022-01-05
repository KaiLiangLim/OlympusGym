package com.fdm.MySoloProject.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fdm.MySoloProject.dal.BookingRepository;
import com.fdm.MySoloProject.dal.MembershipPlansRepository;
import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.dal.TrainerRepository;
import com.fdm.MySoloProject.dal.UserRepository;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.Trainer;
import com.fdm.MySoloProject.model.User;


@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private PersonalTrainingRepository pTRepo;
	
	@Autowired
	private TrainerRepository trainerRepo;
	
	@Autowired
	private MembershipPlansRepository membershipPlanRepo;
	
	private Log log = LogFactory.getLog(DataLoader.class);
	
	


	@Override
	@Transactional //so user and products will be generated together
	@Modifying    // not applied on @service need to include this
	public void run(ApplicationArguments args) throws Exception {
	

		List<Trainer> trainers = new ArrayList<>();

		Trainer Mikey = new Trainer("Mikey", 1111);
		Trainer Aung = new Trainer("Aung", 2222);
		Trainer Angie = new Trainer("Angie", 3333);
		Trainer Johnny = new Trainer("Johnny", 4444);
		Trainer Rocky = new Trainer("Rocky", 5555);
		pTRepo.save(new PersonalTraining(200, "Boxing", "jab jab left right 1 2", Mikey, null));
		pTRepo.save(new PersonalTraining(150, "Mixed Martial Art", "jab kick hook grapple", Aung, null));
		pTRepo.save(new PersonalTraining(160, "Mixed Martial Art", "jab kick hook grapple", Angie, null));
		pTRepo.save(new PersonalTraining(250, "BodyBuilding", "You can't see me", Johnny, null));
		pTRepo.save(new PersonalTraining(350, "BodyBuilding", "Can you smell, what the rock is cooking", Rocky, null));
		trainers.add(Angie);
		trainers.add(Mikey);
		trainers.add(Aung);
		trainers.add(Johnny);
		trainers.add(Rocky);
		trainerRepo.saveAll(trainers);
		User asd = new User("asd", "asd", "asd", "asd", "asd@gmail.com", null, null);
		userRepo.save(asd);

		
		
		log.info("http://localhost:9999/SpringApp/");
		
	}

}
