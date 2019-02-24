package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.CommunityRequest;
import com.getjavajob.simplenet.common.entity.Role;
import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class CommunityDAOTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CommunityDAO communityDAO;

    @Before
    public void populateDB() {
        // Accounts
        Account dima = new Account();   // id=1
        dima.setFirstName("Dima");
        dima.setEmail("elimental@bk.ru");
        dima.setPassword("qwerty");
        entityManager.persist(dima);

        Account vasya = new Account();   // id=2
        vasya.setFirstName("Vasya");
        vasya.setEmail("vasya@bk.ru");
        vasya.setPassword("qwerty");
        entityManager.persist(vasya);

        Account petya = new Account();   // id=3
        petya.setFirstName("Petya");
        petya.setEmail("petya@bk.ru");
        petya.setPassword("qwerty");
        entityManager.persist(petya);

        // Dima creates own community
        Community community = new Community();
        community.setCreatorId(dima.getId());
        community.setName("Dima's community");
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setFrom(dima);
        communityRequest.setCommunity(community);
        communityRequest.setAccepted(true);
        communityRequest.setRole(GROUP_MODERATOR);
        community.getRequests().add(communityRequest);
        entityManager.persist(community);

        // Make request to dima's community from Vasya
        CommunityRequest communityRequest1 = new CommunityRequest();
        communityRequest1.setFrom(vasya);
        communityRequest1.setCommunity(community);
        community.getRequests().add(communityRequest1);
    }


    @Test
    public void getRequest() {
        assertNotNull(communityDAO.getRequest(1L, 1L, true));
        assertNull(communityDAO.getRequest(2L, 1L, true));
        assertNull(communityDAO.getRequest(3L, 1L, true));
    }

    @Test
    public void checkCommunityRole() {
        assertNotNull(communityDAO.checkCommunityRole(1L, 1L, GROUP_MODERATOR));
        assertNotNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_CANDIDATE));
        assertNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_MEMBER));
        assertNull(communityDAO.checkCommunityRole(3L, 1L, GROUP_MEMBER));

    }

    @Test
    public void getRoleMembers() {
        List<Account> moderators = communityDAO.getRoleMembers(1L, GROUP_MODERATOR);
        assertEquals(1, moderators.size());
        List<Account> candidates = communityDAO.getRoleMembers(1L, GROUP_CANDIDATE);
        assertEquals(1, candidates.size());
    }

    @Test
    public void acceptCommunityRequest() {
        assertNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_MEMBER));
        communityDAO.acceptCommunityRequest(2L, 1L);
        assertNotNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_MEMBER));
    }

    @Test
    public void makeModerator() {
        assertNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_MODERATOR));
        communityDAO.makeModerator(2L, 1L);
        assertNotNull(communityDAO.checkCommunityRole(2L, 1L, GROUP_MODERATOR));
    }
}