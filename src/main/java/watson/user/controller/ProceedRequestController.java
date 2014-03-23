package watson.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import watson.user.service.ManagerServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProceedRequestController {

    private ManagerServiceImpl managerService;

    @RequestMapping(value = "/manager/proceed/{requestId}", method = RequestMethod.POST)
    public @ResponseBody String proceedRequest(@PathVariable String requestId, HttpServletRequest req){
        String managerEmail = "xiaoyao8823@gmail.com";
        String comments = req.getParameter("comments");
        if (req.getParameter("proceedAction").equalsIgnoreCase("AP")) {
            managerService.approveRequest(requestId, managerEmail, comments);
        } else if (req.getParameter("proceedAction").equalsIgnoreCase("DE")){
            managerService.denyRequest(requestId, managerEmail, comments);
        }
        return "done";
    }

    @Resource
    public void setManagerService(ManagerServiceImpl managerService) {
        this.managerService = managerService;
    }
}
