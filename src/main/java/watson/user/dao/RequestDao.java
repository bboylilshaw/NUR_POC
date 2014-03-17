package watson.user.dao;

import watson.user.model.Request;

public interface RequestDao {

    public Request getRequestByID(String requestID);

    public String proceedByManager(String requestID, String managerEmail, String proceedAction, String comments);

}
