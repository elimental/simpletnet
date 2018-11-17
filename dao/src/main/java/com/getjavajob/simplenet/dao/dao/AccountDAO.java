package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.FriendRequest;
import com.getjavajob.simplenet.common.entity.PersonalMessage;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AccountDAO extends AbstractDAO<Account> {

    public AccountDAO() {
        setClazz(Account.class);
    }

    public Account getByEmail(String email) {
        return (Account) sessionFactory.getCurrentSession().createQuery(
                "select a " +
                        "from Account a " +
                        "where a.email like :email")
                .setParameter("email", email).uniqueResult();
    }

    public FriendRequest getFriendship(Long firstAccountId, Long secondAccountId, boolean accepted) {
        return (FriendRequest) sessionFactory.getCurrentSession().createQuery(
                "select f " +
                        "from FriendRequest f " +
                        "where f.from.id = :firstId " +
                        "and f.to.id = :secondId " +
                        "and f.accepted = :accepted " +
                        "or " +
                        "f.from.id = :secondId " +
                        "and f.to.id = :firstId " +
                        "and f.accepted = :accepted"
        ).setParameter("firstId", firstAccountId).
                setParameter("secondId", secondAccountId).
                setParameter("accepted", accepted).uniqueResult();
    }

    @SuppressWarnings(value = "unchecked")
    public Set<Long> getTalkersId(Long accountId) {
        Set<Long> talkers = new HashSet<>();
        talkers.addAll(sessionFactory.getCurrentSession().createQuery(
                "select distinct pm.to.id " +
                        "from PersonalMessage pm " +
                        "where pm.from.id = :account"
        ).setParameter("account", accountId).list());
        talkers.addAll(sessionFactory.getCurrentSession().createQuery(
                "select distinct pm.from.id " +
                        "from PersonalMessage pm " +
                        "where pm.to.id = :account"
        ).setParameter("account", accountId).list());
        return talkers;
    }

    @SuppressWarnings(value = "unchecked")
    public List<Community> getCommunities(Long accountId, boolean accepted) {
        return sessionFactory.getCurrentSession().createQuery(
                "select cr.community " +
                        "from CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.accepted = :accepted"
        ).setParameter("accountId", accountId)
                .setParameter("accepted", accepted)
                .list();
    }

    public void deleteFromCommunity(Long accountId, Long communityId) {
        sessionFactory.getCurrentSession().createQuery(
                "delete CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId"
        ).setParameter("accountId", accountId)
                .setParameter("communityId", communityId).executeUpdate();
    }

    @SuppressWarnings(value = "unchecked")
    public List<Account> getFriends(Long accountId) {
        List<Account> friends = sessionFactory.getCurrentSession().createQuery(
                "select fr.to " +
                        "from FriendRequest fr " +
                        "where fr.from.id = :accountId " +
                        "and fr.accepted = true"
        ).setParameter("accountId", accountId).list();
        friends.addAll(sessionFactory.getCurrentSession().createQuery(
                "select fr.from " +
                        "from FriendRequest fr " +
                        "where fr.to.id = :accountId " +
                        "and fr.accepted = true"
        ).setParameter("accountId", accountId).list());
        return friends;
    }

    @SuppressWarnings(value = "unchecked")
    public List<Account> getRequestedFriends(long accountId) {
        return sessionFactory.getCurrentSession().createQuery(
                "select fr.to " +
                        "from FriendRequest fr " +
                        "where fr.from.id = :accountId " +
                        "and fr.accepted = false "
        ).setParameter("accountId", accountId).list();
    }

    @SuppressWarnings(value = "unchecked")
    public List<Account> getRequestFromFriends(long accountId) {
        return sessionFactory.getCurrentSession().createQuery(
                "select fr.from " +
                        "from FriendRequest fr " +
                        "where fr.to.id = :accountId " +
                        "and fr.accepted = false"
        ).setParameter("accountId", accountId).list();
    }

    public void deleteFriend(long firstAccountId, long secondAccountId) {
        sessionFactory.getCurrentSession().createQuery(
                "delete FriendRequest fr " +
                        "where fr.from.id = :firstAccount " +
                        "and fr.to.id = :secondAccount " +
                        "or " +
                        "fr.to.id = :firstAccount " +
                        "and fr.from.id = :secondAccount"
        ).setParameter("firstAccount", firstAccountId)
                .setParameter("secondAccount", secondAccountId)
                .executeUpdate();
    }

    public void acceptFriend(long whoAcceptsId, long whoAcceptedId) {
        sessionFactory.getCurrentSession().createQuery(
                "update FriendRequest fr " +
                        "set fr.accepted = true " +
                        "where fr.from.id = :whoAccepted " +
                        "and fr.to.id = :whoAccepts"
        ).setParameter("whoAccepts", whoAcceptsId)
                .setParameter("whoAccepted", whoAcceptedId)
                .executeUpdate();
    }

    @SuppressWarnings(value = "unchecked")
    public List<PersonalMessage> getPersonalMessages(long firstAccountId, long secondAccountId) {
        return sessionFactory.getCurrentSession().createQuery(
                "select pm " +
                        "from PersonalMessage pm " +
                        "where pm.from.id = :firstAccount " +
                        "and pm.to.id = :secondAccount " +
                        "or " +
                        "pm.to.id = :firstAccount " +
                        "and pm.from.id = :secondAccount"
        ).setParameter("firstAccount", firstAccountId)
                .setParameter("secondAccount", secondAccountId)
                .list();
    }
}
