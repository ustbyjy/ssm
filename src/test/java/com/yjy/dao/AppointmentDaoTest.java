package com.yjy.dao;

import com.yjy.BaseTest;
import com.yjy.entity.Appointment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class AppointmentDaoTest extends BaseTest {

    @Autowired
    private AppointmentDao appointmentDao;

    @Test
    public void testInsertAppointment() throws Exception {
        long bookId = 1000;
        long studentId = 12345678910L;
        int insert = appointmentDao.insertAppointment(bookId, studentId);
        System.out.println("insert=" + insert);
    }

    @Test
    public void testBatchInsertAppointment() {
        long[] bookIds = {1000, 1001, 1002, 1003};
        for (int i = 27000; i < 100000; i++) {
            appointmentDao.insertAppointment(bookIds[Math.abs(new Random().nextInt()) % 4], i);
        }
    }

    @Test
    public void testQueryByKeyWithBook() throws Exception {
        long bookId = 1000;
        long studentId = 12345678910L;
        Appointment appointment = appointmentDao.queryByKeyWithBook(bookId, studentId);
        System.out.println(appointment);
        System.out.println(appointment.getBook());
    }

}
