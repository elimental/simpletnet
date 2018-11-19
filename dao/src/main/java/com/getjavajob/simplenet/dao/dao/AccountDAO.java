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
        List accounts = entityManager.createQuery(
                "select a " +
                        "from Account a " +
                        "where a.email like :email")
                .setParameter("email", email).getResultList();
        return accounts.isEmpty() ? null : (Account) accounts.get(0);
    }

    public FriendRequest getFriendship(Long firstAccountId, Long secondAccountId, boolean accepted) {
        List requests = entityManager.createQuery(
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
                setParameter("accepted", accepted).getResultList();
        return requests.isEmpty() ? null : (FriendRequest) requests.get(0);
    }

    public Set<Long> getTalkersId(Long accountId) {
        Set<Long> talkers = new HashSet<>();
        talkers.addAll(entityManager.createQuery(
                "select distinct pm.to.id " +
                        "from PersonalMessage pm " +
                        "where pm.from.id = :account", Long.class
        ).setParameter("account", accountId).getResultList());
        talkers.addAll(entityManager.createQuery(
                "select distinct pm.from.id " +
                        "from PersonalMessage pm " +
                        "where pm.to.id = :account", Long.class
        ).setParameter("account", accountId).getResultList());
        return talkers;
    }

    public List<Community> getCommunities(Long accountId, boolean accepted) {
        return entityManager.createQuery(
                "select cr.community " +
                        "from CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.accepted = :accepted", Community.class
        ).setParameter("accountId", accountId)
                .setParameter("accepted", accepted)
                .getResultList();
    }

    public void deleteFromCommunity(Long accountId, Long communityId) {
        entityManager.createQuery(
                "delete from CommunityRequest cr " +
                        "where cr.from.id = :accountId " +
                        "and cr.community.id = :communityId"
        ).setParameter("accountId", accountId)
                .setParameter("communityId", communityId).executeUpdate();
    }

    public List<Account> getFriends(Long accountId) {
        List<Account> friends = entityManager.createQuery(
                "select fr.to " +
                        "from FriendRequest fr " +
                        "where fr.from.id = :accountId " +
                        "and fr.accepted = true", Account.class
        ).setParameter("accountId", accountId).getResultList();
        friends.addAll(entityManager.createQuery(
                "select fr.from " +
                        "from FriendRequest fr " +
                        "where fr.to.id = :accountId " +
                        "and fr.accepted = true", Account.class
        ).setParameter("accountId", accountId).getResultList());
        return friends;
    }

    public List<Account> getRequestedFriends(long accountId) {
        return entityManager.createQuery(
                "select fr.to " +
                        "from FriendRequest fr " +
                        "where fr.from.id = :accountId " +
                        "and fr.accepted = false ", Account.class
        ).setParameter("accountId", accountId).getResultList();
    }

    public List<Account> getRequestFromFriends(long accountId) {
        return entityManager.createQuery(
                "select fr.from " +
                        "from FriendRequest fr " +
                        "where fr.to.id = :accountId " +
                        "and fr.accepted = false", Account.class
        ).setParameter("accountId", accountId).getResultList();
    }

    public void deleteFriend(long firstAccountId, long secondAccountId) {
        entityManager.createQuery(
                "delete from FriendRequest fr " +
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
        entityManager.createQuery(
                "update FriendRequest fr " +
                        "set fr.accepted = true " +
                        "where fr.from.id = :whoAccepted " +
                        "and fr.to.id = :whoAccepts"
        ).setParameter("whoAccepts", whoAcceptsId)
                .setParameter("whoAccepted", whoAcceptedId)
                .executeUpdate();
    }

    public List<PersonalMessage> getPersonalMessages(long firstAccountId, long secondAccountId) {
        return entityManager.createQuery(
                "select pm " +
                        "from PersonalMessage pm " +
                        "where pm.from.id = :firstAccount " +
                        "and pm.to.id = :secondAccount " +
                        "or " +
                        "pm.to.id = :firstAccount " +
                        "and pm.from.id = :secondAccount", PersonalMessage.class
        ).setParameter("firstAccount", firstAccountId)
                .setParameter("secondAccount", secondAccountId)
                .getResultList();
    }
}
