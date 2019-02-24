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
        List requests = entityManager.createQuery(
                "select cr " +
                        "from CommunityRequest  cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId " +
                        "and cr.accepted = :accepted")
                .setParameter("accountId", accountId)
                .setParameter("communityId", communityId)
                .setParameter("accepted", accepted)
                .getResultList();
        return requests.isEmpty() ? null : (CommunityRequest) requests.get(0);
    }

    public Account checkCommunityRole(long accountId, long communityId, Role role) {
        List accounts = entityManager.createQuery(
                "select cr.from " +
                        "from CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.role = :role " +
                        "and cr.community.id = :communityId"
        ).setParameter("accountId", accountId)
                .setParameter("role", role)
                .setParameter("communityId", communityId)
                .getResultList();
        return accounts.isEmpty() ? null : (Account) accounts.get(0);
    }

    public List<Account> getRoleMembers(long communityId, Role role) {
        return entityManager.createQuery(
                "select cr.from " +
                        "from CommunityRequest cr " +
                        "where cr.community.id = :communityId " +
                        "and cr.role = :role", Account.class
        ).setParameter("communityId", communityId)
                .setParameter("role", role).getResultList();
    }

    public void acceptCommunityRequest(long accountId, long communityId) {
        entityManager.createQuery(
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
        entityManager.createQuery(
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
