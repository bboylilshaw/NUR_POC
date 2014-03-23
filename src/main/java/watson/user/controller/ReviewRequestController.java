package watson.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import watson.user.model.Request;
import watson.user.service.ManagerServiceImpl;

import javax.annotation.Resource;

@Controller
public class ReviewRequestController {

    private ManagerServiceImpl managerService;
//    private CountryRepServiceImpl countryRepService;
//    private RegionalRepServiceImpl regionalRepService;

    @RequestMapping(value = "/manager/review/request/{requestId}", method = RequestMethod.GET)
    public String reviewRequestByManager(@PathVariable String requestId, ModelMap model) {
        Request request = managerService.reviewRequest(requestId);
        model.addAttribute("domainUserName", request.getDomainUserName());
        model.addAttribute("email", request.getEmployeeEmail());
        model.addAttribute("instance", request.getInstance());
        model.addAttribute("comments", request.getComments());
        model.addAttribute("date", request.getRequestDate());
        return "reviewRequest";
    }

    @Resource
    public void setManagerService(ManagerServiceImpl managerService) {
        this.managerService = managerService;
    }
}
