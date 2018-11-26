package com.getjavajob.simplenet.dao.repositories;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.FriendRequest;
import com.getjavajob.simplenet.common.entity.PersonalMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByEmail(String email);

    @Query("select fr.to " +
            "from FriendRequest fr " +
            "where fr.from.id = :accountId  " +
            "and fr.accepted = true")
    List<Account> getFriendsFrom(@Param("accountId") Long accountId);

    @Query("select fr.from " +
            "from FriendRequest fr " +
            "where fr.to.id = :accountId  " +
            "and fr.accepted = true")
    List<Account> getFriendsTo(@Param("accountId") Long accountId);

    @Query("select fr.to " +
            "from FriendRequest fr " +
            "where fr.from.id = :accountId " +
            "and fr.accepted = false")
    List<Account> getRequestedFriends(@Param("accountId") Long accountId);

    @Query("select fr.from " +
            "from FriendRequest fr " +
            "where fr.to.id = :accountId " +
            "and fr.accepted = false")
    List<Account> getRequestFromFriends(@Param("accountId") Long accountId);

    @Query("select f " +
            "from FriendRequest f " +
            "where f.from.id = :firstId " +
            "and f.to.id = :secondId " +
            "and f.accepted = :accepted " +
            "or " +
            "f.from.id = :secondId " +
            "and f.to.id = :firstId " +
            "and f.accepted = :accepted")
    List<FriendRequest> getFriendship(@Param("firstId") Long firstId,
                                      @Param("secondId") Long secondId,
                                      @Param("accepted") boolean accepted);

    @Query("select distinct pm.from.id " +
            "from PersonalMessage pm " +
            "where pm.to.id = :accountId")
    Set<Long> getTalkersIdFrom(@Param("accountId") Long accountId);

    @Query("select distinct pm.to.id " +
            "from PersonalMessage pm " +
            "where pm.from.id = :accountId")
    Set<Long> getTalkersIdTo(@Param("accountId") Long accountId);

    @Modifying
    @Query("delete from CommunityRequest cr " +
            "where cr.from.id = :accountId " +
            "and cr.community.id = :communityId")
    void deleteFromCommunity(@Param("accountId") Long accountId,
                             @Param("communityId") Long communityId);

    @Modifying
    @Query("delete from FriendRequest fr " +
            "where fr.from.id = :firstAccountId " +
            "and fr.to.id = :secondAccountId " +
            "or " +
            "fr.to.id = :firstAccountId " +
            "and fr.from.id = :secondAccountId")
    void deleteFriend(@Param("firstAccountId") Long firstAccountId,
                      @Param("secondAccountId") Long secondAccountId);

    @Modifying
    @Query("update FriendRequest fr " +
            "set fr.accepted = true " +
            "where fr.from.id = :whoAcceptedId " +
            "and fr.to.id = :whoAcceptsId")
    void acceptFriend(@Param("whoAcceptsId") Long whoAcceptsId,
                      @Param("whoAcceptedId") Long whoAcceptedId);

    @Query("select pm " +
            "from PersonalMessage pm " +
            "where pm.from.id = :firstAccountId " +
            "and pm.to.id = :secondAccountId " +
            "or " +
            "pm.to.id = :firstAccountId " +
            "and pm.from.id = :secondAccountId")
    List<PersonalMessage> getPersonalMessages(@Param("firstAccountId") Long firstAccountId,
                                              @Param("secondAccountId") Long secondAccountId);

    @Query("select cr.community " +
            "from CommunityRequest cr " +
            "where cr.from.id = :accountId " +
            "and cr.accepted = :accepted")
    List<Community> getCommunities(@Param("accountId") Long accountId,
                                   @Param("accepted") boolean accepted);
}
