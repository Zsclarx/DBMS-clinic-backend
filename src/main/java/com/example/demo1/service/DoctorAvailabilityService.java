package com.example.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import com.example.demo1.repository.DoctorAvailabilityRepository;
import com.example.demo1.repository.TimeSlotRepository;
import com.example.demo1.repository.NotificationRepository;
import com.example.demo1.model.DoctorAvailability;
import com.example.demo1.model.TimeSlot;

@Service
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepository;
    private final TimeSlotRepository timeslotsRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public DoctorAvailabilityService(DoctorAvailabilityRepository availabilityRepository,
                                     TimeSlotRepository timeslotsRepository,
                                     NotificationRepository notificationRepository) {
        this.availabilityRepository = availabilityRepository;
        this.timeslotsRepository = timeslotsRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<DoctorAvailability> getAvailabilityByDate(int doctorId, Date date) {
        deleteOldAvailability();
        populateFutureAvailability();
        return availabilityRepository.getDoctorAvailabilityByDate(doctorId, date);
    }

    public List<DoctorAvailability> getAvailabilityForNextWeek(int doctorId) {
        deleteOldAvailability();
        populateFutureAvailability();
        return availabilityRepository.getDoctorAvailabilityForNextWeek(doctorId);
    }

    public void deleteOldAvailability() {
        availabilityRepository.deleteOldAvailability();
    }

    @Transactional // Ensure all inserts are treated as a single transaction
    public void populateFutureAvailability() {
        Date maxDate = availabilityRepository.getMaxDate();
        LocalDate startDate = maxDate != null ? maxDate.toLocalDate().plusDays(1) : LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(6);

        while (!startDate.isAfter(endDate)) {
            String workday = startDate.getDayOfWeek().name();
            List<TimeSlot> timeslots = timeslotsRepository.getTimeslotsByWorkday(workday);

            for (TimeSlot timeslot : timeslots) {
                availabilityRepository.insertAvailability(timeslot.getDoctorID(), timeslot.getTimeslot(), Date.valueOf(startDate), "Free");
            }
            startDate = startDate.plusDays(1);
        }
    }
}
