package fetalist.demo.service;

import fetalist.demo.model.Friend;
import fetalist.demo.model.Token;

import java.util.List;

public interface FriendService {

    boolean requestFriend(long idUserToAdd, Token t);

    List<Friend> getAllFriend(Token t);

    boolean respondFriendRequest(Token t, long idFriendInviteToRespond, boolean response);

    boolean deleteFriend(Token t, long idFriendToRemove);
}
