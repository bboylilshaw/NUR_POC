package watson.user.service;

import watson.user.model.HPEmployee;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LDAPServiceImpl implements LDAPService {

    private String ldapURL;
    private String ldapAccount;
    private String ldapPassword;
    private String initialContextFactory;
    private String trustStore;

    private LdapContext getNewLdapContext(){

        LdapContext ldapContext = null;

        System.setProperty("javax.net.ssl.trustStore", this.trustStore);
        //System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, this.initialContextFactory);
        env.put(Context.PROVIDER_URL, this.ldapURL);
        env.put(Context.SECURITY_PRINCIPAL, this.ldapAccount);
        env.put(Context.SECURITY_CREDENTIALS, this.ldapPassword);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        try {
            ldapContext = new InitialLdapContext(env, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ldapContext;
    }

    @Override
    public boolean authenticateUser(String email, String password) {

        boolean flag = false;

        String userPrincipal = "uid=" + email.trim() + ",ou=People,o=hp.com";
        System.setProperty("javax.net.ssl.trustStore", this.trustStore);
        //System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, this.initialContextFactory);
        env.put(Context.PROVIDER_URL, this.ldapURL);
        env.put(Context.SECURITY_PRINCIPAL, userPrincipal);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        try {
            new InitialLdapContext(env, null);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public HPEmployee getEmployeeDataByEmail(String email) {
        HPEmployee employee = null;
        LdapContext ldapContext = this.getNewLdapContext();
        try {
            String searchAttr = "mail=" + email.trim();

            employee = this.getEmployeeData(searchAttr, ldapContext);

            if (employee != null) {
                String managerEmployeeId = employee.getManagerEmployeeId();
                String searchAttrMgr = "employeeNumber=" + managerEmployeeId;

                HPEmployee manager = getEmployeeData(searchAttrMgr, ldapContext);

                if (manager != null) {
                    employee.setManagerDomainUserName(manager.getDomainUserName());
                    employee.setManagerName(manager.getName());
                    employee.setManagerEmail(manager.getEmail());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public HPEmployee getEmployeeDataByDomainUserName(String domainUserName) {
        HPEmployee employee = null;
        LdapContext ldapContext = this.getNewLdapContext();
        try {
            String searchAttr = "ntuserdomainid=" + domainUserName.trim().replace("\\", ":");

            employee = this.getEmployeeData(searchAttr, ldapContext);

            if (employee != null) {
                String managerEmployeeId = employee.getManagerEmployeeId();
                String searchAttrMgr = "employeeNumber=" + managerEmployeeId;

                HPEmployee manager = getEmployeeData(searchAttrMgr, ldapContext);

                if (manager != null) {
                    employee.setManagerDomainUserName(manager.getDomainUserName());
                    employee.setManagerName(manager.getName());
                    employee.setManagerEmail(manager.getEmail());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<HPEmployee> getEmployeeData(List<String> domainUserNames) {
        List<HPEmployee> employees = new ArrayList<HPEmployee>();
        for (String domainUserName : domainUserNames) {
            HPEmployee employee = this.getEmployeeDataByDomainUserName(domainUserName);
            employees.add(employee);
        }
        return employees;
    }

    private HPEmployee getEmployeeData(String searchAttr, LdapContext ldapContext) throws Exception{
        String searchBase = "ou=People, o=hp.com";
        String[] retAttrs = {"ntuserdomainid", "employeeNumber", "cn", "givenName", "sn", "hpStatus", "mail", "hpJobFunction", "hpJobFamily", "c", "ou", "managerEmployeeNumber"};

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        searchControls.setReturningAttributes(retAttrs);
        HPEmployee employee = null;
        try {
            NamingEnumeration results = ldapContext.search(searchBase, searchAttr, searchControls);

            while (results != null && results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attrs = searchResult.getAttributes();
                employee = getHPEmployee(attrs);
                String hpStatus = employee.getHpStatus();
                //Multiple entries for the person may exist in ED. If so, return the first entry
                //with hpStatus of 'active' if one exists, else return the last entry
                if ((hpStatus != null) && hpStatus.equalsIgnoreCase("Active"))
                    return employee;
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return employee;
    }

    private HPEmployee getHPEmployee(Attributes attrs) throws NamingException {
        Attribute attr;
        attr = attrs.get("ntuserdomainid");
        String ntuserdomainid = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("employeeNumber");
        String employeeId = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("cn");
        String name = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("givenName");
        String firstName = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("sn");
        String lastName = (attrs == null) ? null :attr.get().toString();
        attr = attrs.get("mail");
        String email = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("hpJobFunction");
        String jobFunction = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("hpJobFamily");
        String jobFamily = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("c");
        String country = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("ou");
        String orgUnit = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("hpStatus");
        String hpStatus = (attr == null) ? null : attr.get().toString();
        attr = attrs.get("managerEmployeeNumber");
        String mgrId = (attr == null) ? null : attr.get().toString();

        return new HPEmployee(ntuserdomainid, employeeId, name, firstName, lastName, email, jobFunction + " - " + jobFamily, country, orgUnit, hpStatus, mgrId);
    }

    public void setLdapURL(String ldapURL) {
        this.ldapURL = ldapURL;
    }

    public void setLdapAccount(String ldapAccount) {
        this.ldapAccount = ldapAccount;
    }

    public void setLdapPassword(String ldapPassword) {
        this.ldapPassword = ldapPassword;
    }

    public void setInitialContextFactory(String initialContextFactory) {
        this.initialContextFactory = initialContextFactory;
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

}
