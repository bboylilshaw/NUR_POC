package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

public class RegionalRepServiceImpl implements RegionalRepService {

    @Autowired private RequestDaoImpl requestDao;

    @Override
    public Request reviewRequest(String requestID) {
        Request request = requestDao.getRequestById(requestID);
        if (request.getRegionalRepProceed().equalsIgnoreCase("IN") && request.getCountryRepProceed().equalsIgnoreCase("AP") && request.getManagerProceed().equalsIgnoreCase("AP")) {
            return request;
        }
        return null;
    }

    @Override
    public void approveRequest(String requestID, String regionalRepEmail, String comments) {

    }

    @Override
    public void denyRequest(String requestID, String regionalRepEmail, String comments) {

    }

}
