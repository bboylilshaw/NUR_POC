package watson.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import watson.user.service.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RequestAccessController {

    private UserServiceImpl userService;

    @Resource
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/access/request", method = RequestMethod.POST)
    public
    @ResponseBody
    String requestAccess(HttpServletRequest request) {

        String domainUserName = request.getParameter("domainUserName");
        String instance = request.getParameter("instance");
        String comments = request.getParameter("comments");

        userService.requestAccess(domainUserName, instance, comments);
        return "request submitted!";
    }

}