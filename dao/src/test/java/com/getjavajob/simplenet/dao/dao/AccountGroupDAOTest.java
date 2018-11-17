//package com.getjavajob.simplenet.dao.dao;
//
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
//import static com.getjavajob.simplenet.common.entity.Role.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
//@Sql(value = {"classpath:drop_db.sql", "classpath:create_db.sql", "classpath:populate_db.sql"},
//        executionPhase = BEFORE_TEST_METHOD)
//public class AccountGroupDAOTest {
//
//    @Autowired
//    private AccountGroupDAO accountGroupDAO;
//
//    @Test
//    public void getUserGroupsId() {
//        List<Integer> expected = new ArrayList<>();
//        expected.add(2);
//        expected.add(4);
//        int userId = 1;
//        List<Integer> actual = accountGroupDAO.getUserGroupsId(userId);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void addGroup() {
//        List<Integer> expected = new ArrayList<>();
//        expected.add(2);
//        expected.add(4);
//        expected.add(5);
//        int userId = 1;
//        int groupId = 5;
//        accountGroupDAO.addGroup(userId, groupId);
//        List<Integer> actual = accountGroupDAO.getUserGroupsId(userId);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void sendGroupRequest() {
//        Integer expectedRole = GROUP_CANDIDATE;
//        accountGroupDAO.sendGroupRequest(1, 6);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(1, 6);
//        assertEquals(expectedRole, actualRole);
//    }
//
//    @Test
//    public void acceptGroupRequest() {
//        Integer exceptedRole = GROUP_MEMBER;
//        accountGroupDAO.sendGroupRequest(1, 6);
//        accountGroupDAO.acceptGroupRequest(1, 6);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(1, 6);
//        assertEquals(exceptedRole, actualRole);
//    }
//
//    @Test
//    public void rejectGroupRequest() {
//        Integer exceptedRole = 0;
//        accountGroupDAO.sendGroupRequest(1, 6);
//        accountGroupDAO.rejectGroupRequest(1, 6);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(1, 6);
//        assertEquals(exceptedRole, actualRole);
//    }
//
//    @Test
//    public void getUserRoleInGroup() {
//        Integer expectedRole = GROUP_MODERATOR;
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(1, 4);
//        assertEquals(expectedRole, actualRole);
//    }
//
//    @Test
//    public void deleteUser() {
//        accountGroupDAO.deleteUser(1);
//        List<Integer> userGroupsId = accountGroupDAO.getUserGroupsId(1);
//        assertTrue(userGroupsId.isEmpty());
//    }
//
//    @Test
//    public void deleteGroup() {
//        Integer expectedRole = 0;
//        accountGroupDAO.deleteGroup(4);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(1, 4);
//        assertEquals(expectedRole, actualRole);
//    }
//
//    @Test
//    public void getCandidatesId() {
//        List<Integer> expectedCandidates = new ArrayList<>();
//        expectedCandidates.add(20);
//        expectedCandidates.add(21);
//        accountGroupDAO.sendGroupRequest(20, 4);
//        accountGroupDAO.sendGroupRequest(21, 4);
//        List<Integer> actualCandidates = accountGroupDAO.getCandidatesIds(4);
//        assertEquals(expectedCandidates, actualCandidates);
//    }
//
//    @Test
//    public void getMembersId() {
//        List<Integer> expectedMembers = new ArrayList<>();
//        expectedMembers.add(1);
//        expectedMembers.add(2);
//        List<Integer> actualMembers = accountGroupDAO.getMembersId(4);
//        assertEquals(expectedMembers, actualMembers);
//    }
//
//    @Test
//    public void makeModerator() {
//        Integer expectedRole = GROUP_MODERATOR;
//        accountGroupDAO.makeModerator(2, 4);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(2, 4);
//        assertEquals(expectedRole, actualRole);
//    }
//
//    @Test
//    public void deleteFromGroup() {
//        Integer expectedRole = 0;
//        accountGroupDAO.deleteFromGroup(2, 4);
//        Integer actualRole = accountGroupDAO.getUserRoleInGroup(2, 4);
//        assertEquals(expectedRole, actualRole);
//    }
//}