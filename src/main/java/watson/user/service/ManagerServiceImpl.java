package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.CountryRepDaoImpl;
import watson.user.dao.RegionalRepDaoImpl;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.CountryRep;
import watson.user.model.HPEmployee;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.HashMap;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired private RequestDaoImpl requestDao;
    @Autowired private CountryRepDaoImpl countryRepDao;
    @Autowired private RegionalRepDaoImpl regionalRepDao;
    @Autowired private LDAPServiceImpl ldapService;
    @Autowired private NotificationServiceImpl notificationService;

    @Override
    @Transactional(readOnly = true)
    public Request reviewRequest(String requestId){
        Request request = requestDao.getRequestById(requestId);
        if (request != null && request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            return request;
        }
        return null;
    }

    @Override
    @Transactional
    public void proceedRequest(String requestId, String proceedAction, String comments) throws Exception{
        Request request = this.reviewRequest(requestId);
        // this managerProceed is not "IN", which means this request has been proceeded
        if (!request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            throw new Exception("This request has been proceeded!");
        }

        if (proceedAction.equalsIgnoreCase(RequestStatus.APPROVED)) { //Manager approved the request

            if (this.skipCountryRep(request)) {//skip country rep, direct pass to regional rep
                //pass to regional rep directly by setting countryRepProceed as "SK" and regionalRepProceed as "IN"
                requestDao.approvedByManager(requestId, comments, true);
                //TODO send notifications
            } else {
                //pass to country rep by setting countryRepProceed as "IN"
                requestDao.approvedByManager(requestId, comments);
                CountryRep countryRep = this.getCountryRep(request);
                //TODO send notifications
                String toCountryRepEmail = "yao.xiao@hp.com";
                String ccEmail = "yao.xiao@hp.com";
                String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
                HashMap<String, String> model = new HashMap<String, String>();
                model.put("approver", "Approver");
                model.put("url", "http://localhost:8080/NUR_POC/countryRep/review/request/" + requestId);
                notificationService.sendEmailWithTemplate(toCountryRepEmail, ccEmail, templateName, model);
            }
        } else if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) { //Manager denied the request
            requestDao.deniedByManager(requestId, comments);
        }
    }

    private boolean skipCountryRep(Request request) {
        String requestWatsonInstance = request.getWatsonInstance();
        if (request.getDomainUserName().toUpperCase().startsWith("ASIAPACIFIC") && requestWatsonInstance.equalsIgnoreCase("apwatson"))
            return false;
        if (request.getDomainUserName().toUpperCase().startsWith("EMEA") && (requestWatsonInstance.equalsIgnoreCase("euwatson") || requestWatsonInstance.equalsIgnoreCase("eubwatson")))
            return false;
        if (request.getDomainUserName().toUpperCase().startsWith("AMERICAS") && (requestWatsonInstance.equalsIgnoreCase("nawatson") || requestWatsonInstance.equalsIgnoreCase("lawatson")))
            return false;
        return true;
    }

    @Override
    public CountryRep getCountryRep(Request request) {
        HPEmployee hpEmployee = ldapService.getEmployeeDataByDomainUserName(request.getDomainUserName());
        CountryRep countryRep = countryRepDao.getCountryRepByCountryCode(hpEmployee.getCountry());
        return countryRep;
    }

    @Override
    public RegionalRep getRegionalRep(String watsonInstance) {
        RegionalRep regionalRep = regionalRepDao.getRegionalRep(watsonInstance);
        return regionalRep;
    }

}
