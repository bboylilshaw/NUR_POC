package watson.user.service;

import watson.user.model.Request;

public interface ApproverService {

    public Request reviewRequest(String requestId) throws Exception;

}
