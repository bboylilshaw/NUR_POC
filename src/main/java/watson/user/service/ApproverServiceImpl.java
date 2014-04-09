package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

@Service("approverService")
public class ApproverServiceImpl implements ApproverService {

    @Autowired private RequestDaoImpl requestDao;

    @Override
    @Transactional(readOnly = true)
    public Request reviewRequest(String requestId) throws Exception{
        Request request = requestDao.getRequestById(requestId);
        if (request == null)
            throw new Exception("there is no request been found!");
        if (request.getStatus().equalsIgnoreCase(RequestStatus.EXPIRED))
            throw new Exception("request has expired!");
        if (request.getStatus().equalsIgnoreCase(RequestStatus.DENIED))
            throw new Exception("request has been denied!");
        return request;
    }

}
