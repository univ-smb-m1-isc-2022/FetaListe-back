package fetalist.demo.service;

import fetalist.demo.model.Token;

public interface SMSService {
    String shareSList(Token t, long idUserToSend, long idSLToShare);
}
