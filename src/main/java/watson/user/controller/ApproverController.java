package watson.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import watson.user.commons.RequestStatus;
import watson.user.model.Request;
import watson.user.service.ApproverServiceImpl;
import watson.user.service.CountryRepServiceImpl;
import watson.user.service.ManagerServiceImpl;
import watson.user.service.RegionalRepServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApproverController {

    @Autowired private ApproverServiceImpl approverService;
    @Autowired private ManagerServiceImpl managerService;
    @Autowired private CountryRepServiceImpl countryRepService;
    @Autowired private RegionalRepServiceImpl regionalRepService;


    @RequestMapping(value = "/review/{requestId}", method = RequestMethod.GET)
    public String reviewRequest(@PathVariable String requestId, ModelMap modelMap) {
        try {
            Request request = approverService.reviewRequest(requestId);
            modelMap.addAttribute("request", request);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute("errMsg", e.getMessage());
            return "error";
        }
        return "reviewRequest";
    }

    @RequestMapping(value = "/proceed/{requestId}", method = RequestMethod.POST)
    public String proceedRequest(@PathVariable String requestId, HttpServletRequest req, ModelMap modelMap){
        String proceedAction = req.getParameter("proceedAction");
        String comments = req.getParameter("comments");
        try {
            Request request = approverService.reviewRequest(requestId);
            if (request.getStatus().equalsIgnoreCase(RequestStatus.PENDING_MANAGER))
                managerService.proceedRequest(requestId, proceedAction, comments);
            if (request.getStatus().equalsIgnoreCase(RequestStatus.PENDING_COUNTRY_REP))
                countryRepService.proceedRequest(requestId, proceedAction, comments);
            if (request.getStatus().equalsIgnoreCase(RequestStatus.PENDING_REGIONAL_REP))
                regionalRepService.proceedRequest(requestId, proceedAction, comments);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute("errMsg", e.getMessage());
            return "error";
        }
        return "redirect:/home";
    }

}
