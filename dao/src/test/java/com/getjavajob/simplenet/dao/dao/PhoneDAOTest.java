//package com.getjavajob.simplenet.dao.dao;
//
//import com.getjavajob.simplenet.common.entity.Phone;
//import com.getjavajob.simplenet.dao.config.DAOConfig;
//import com.getjavajob.simplenet.dao.config.DAOTestConfig;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
//@Sql(value = {"classpath:drop_db.sql", "classpath:create_db.sql", "classpath:populate_db.sql"},
//        executionPhase = BEFORE_TEST_METHOD)
//public class PhoneDAOTest {
//
//    @Autowired
//    private PhoneDAO phoneDAO;
//
//    @Test
//    public void getPhonesByOwnerId() {
//        List<Phone> expectedPhones = new ArrayList<>();
//        Phone expectedPhone1 = new Phone();
//        expectedPhone1.setNumber("5555555");
//        expectedPhone1.setOwner(1);
//        expectedPhone1.setType(1);
//        expectedPhones.add(expectedPhone1);
//
//        Phone expectedPhone2 = new Phone();
//        expectedPhone2.setNumber("6666666");
//        expectedPhone2.setOwner(1);
//        expectedPhone2.setType(2);
//        expectedPhones.add(expectedPhone2);
//
//        List<Phone> actualPhones = phoneDAO.getPhonesByOwnerId(1);
//        assertEquals(expectedPhones, actualPhones);
//    }
//
//    @Test
//    public void add() {
//        Phone expected = new Phone();
//        expected.setNumber("8888888");
//        expected.setOwner(1);
//        expected.setType(2);
//
//        phoneDAO.add(expected);
//        List<Phone> phones = phoneDAO.getPhonesByOwnerId(1);
//        assertTrue(phones.contains(expected));
//    }
//
//    @Test
//    public void deleteByOwnerId() {
//        phoneDAO.deleteByOwnerId(1);
//        List<Phone> phones = phoneDAO.getPhonesByOwnerId(1);
//        assertTrue(phones.isEmpty());
//    }
//}