package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.CommunityRequest;
import com.getjavajob.simplenet.common.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.GROUP_MEMBER;
import static com.getjavajob.simplenet.common.entity.Role.GROUP_MODERATOR;

@Repository
public class CommunityDAO extends AbstractDAO<Community> {

    public CommunityDAO() {
        setClazz(Community.class);
    }

    public CommunityRequest getRequest(long accountId, long communityId, boolean accepted) {
        return (CommunityRequest) sessionFactory.getCurrentSession().createQuery(
                "select cr " +
                        "from CommunityRequest  cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId " +
                        "and cr.accepted = :accepted")
                .setParameter("accountId", accountId)
                .setParameter("communityId", communityId)
                .setParameter("accepted", accepted)
                .uniqueResult();
    }


    public Account checkCommunityRole(long accountId, long communityId, Role role) {
        return (Account) sessionFactory.getCurrentSession().createQuery(
                "select cr.from " +
                        "from CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.role = :role " +
                        "and cr.community.id = :communityId"
        ).setParameter("accountId", accountId)
                .setParameter("role", role)
                .setParameter("communityId", communityId)
                .uniqueResult();
    }

    @SuppressWarnings(value = "unchecked")
    public List<Account> getRoleMembers(long communityId, Role role) {
        return sessionFactory.getCurrentSession().createQuery(
                "select cr.from " +
                        "from CommunityRequest cr " +
                        "where cr.community.id = :communityId " +
                        "and cr.role = :role"
        ).setParameter("communityId", communityId)
                .setParameter("role", role).list();
    }

    public void acceptCommunityRequest(long accountId, long communityId) {
        sessionFactory.getCurrentSession().createQuery(
                "update CommunityRequest cr " +
                        "set cr.accepted = true, " +
                        "cr.role = :role " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId"
        ).setParameter("role", GROUP_MEMBER)
                .setParameter("accountId", accountId)
                .setParameter("communityId", communityId)
                .executeUpdate();
    }

    public void makeModerator(long accountId, long communityId) {
        sessionFactory.getCurrentSession().createQuery(
                "update CommunityRequest cr " +
                        "set cr.role = :role " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId"
        ).setParameter("role", GROUP_MODERATOR)
                .setParameter("accountId", accountId)
                .setParameter("communityId", communityId)
                .executeUpdate();
    }
}
