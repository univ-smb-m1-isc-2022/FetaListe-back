package fetalist.demo.service;

import fetalist.demo.model.Friend;
import fetalist.demo.model.Token;
import fetalist.demo.model.Users;
import fetalist.demo.repository.FriendRepository;
import fetalist.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

    UsersRepository userRepository;
    FriendRepository friendRepository;
    @Override
    public boolean requestFriend(long idUserToAdd, Token t) {
        Users userToAdd = userRepository.findById(idUserToAdd).orElse(null);
        if (userToAdd == null  || userToAdd.equals(t.getUsers())) {
            return false;
        }
        Friend friendRequest = Friend.builder().user1(t.getUsers()).user2(userToAdd).build();
        Friend friendRequestReversed = Friend.builder().user1(userToAdd).user2(t.getUsers()).build();
        if (friendRepository.exists(Example.of(friendRequest)) || friendRepository.exists(Example.of(friendRequestReversed))) {
            return false;
        }
        friendRequest.setStatus("PENDING");
        friendRepository.save(friendRequest);
        return true;
    }

    @Override
    public List<Friend> getAllFriend(Token t) {
        List<Friend> leftSide = friendRepository.findAll(Example.of(Friend.builder().user1(t.getUsers()).build()));
        leftSide.addAll(friendRepository.findAll(Example.of(Friend.builder().user2(t.getUsers()).build())));
        return leftSide;
    }

    @Override
    public boolean respondFriendRequest(Token t, long idFriendInviteToRespond, boolean response) {
        Users userInvited = userRepository.findById(idFriendInviteToRespond).orElse(null);
        if (userInvited == null) {
            return false;
        }
        Friend fr = friendRepository.findBy(Example.of(Friend.builder().user1(t.getUsers()).user2(userInvited).build()), FluentQuery.FetchableFluentQuery::first).orElse(null);
        if (fr == null || !Objects.equals(fr.getStatus(), "PENDING")) return false;
        if (response) {
            fr.setStatus("ACCEPTED");
            friendRepository.save(fr);
        } else {
            friendRepository.delete(fr);
        }
        return true;
    }

    @Override
    public boolean deleteFriend(Token t, long idFriendToRemove) {
        Users friend = userRepository.findById(idFriendToRemove).orElse(null);
        if (friend == null) {
            return false;
        }
        Friend fr = friendRepository.findOne(Example.of(Friend.builder().user1(t.getUsers()).user2(friend).build())).orElse(null);
        Friend reverseFr = friendRepository.findOne(Example.of(Friend.builder().user2(t.getUsers()).user1(friend).build())).orElse(null);
        if (fr == null && reverseFr == null) {
            return false;
        }
        friendRepository.delete(fr == null ? reverseFr : fr);
        return true;
    }
}
