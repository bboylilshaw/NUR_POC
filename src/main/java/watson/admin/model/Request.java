package watson.admin.model;

import java.util.Date;
import java.util.UUID;

public class Request {
    private UUID requestID;
    private String domainUserName;
    private String instance;
    private int employeeID;
    private String employeeEmail;
    private String requestDate;
    private String comments;
    private String managerProceed;
    private Date managerProceedDate;
    private String managerComments;
    private String countryRepProceed;
    private Date countryRepProceedDate;
    private String countryRepComments;
    private String regionalRepProceed;
    private Date regionalRepProceedDate;
    private String regionalRepComments;
}
