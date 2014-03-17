package watson.admin.commons;

import watson.admin.dao.HPUserDaoImpl;

import javax.annotation.Resource;

public class UserValidator {

    private static HPUserDaoImpl hpUserDao;

    @Resource
    public void setHpUserDao(HPUserDaoImpl hpUserDao) {
        this.hpUserDao = hpUserDao;
    }

    public static boolean checkUserExists(String domainUserName, String instance){
        if (hpUserDao.getHPUserByDomainUserName(domainUserName, instance) == null){
            return false;
        }
        return true;
    }

}
