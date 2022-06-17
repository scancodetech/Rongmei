package com.rongmei.security.jwt;

import com.rongmei.dao.account.EmployeesDao;
import com.rongmei.dao.account.UserDao;
import com.rongmei.entity.account.Employees;
import com.rongmei.entity.account.User;
import com.rongmei.publicdatas.account.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {
    private final UserDao userDao;

    private final JwtService jwtService;
    private final EmployeesDao employeesDao;
    @Autowired
    public JwtUserDetailsServiceImpl(UserDao userDao, JwtService jwtService, EmployeesDao employeesDao) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.employeesDao=employeesDao;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  通过查询数据库判断是员工登录还是用户登录，然后再给出权限（这样的话在用户注册的时候不能让他使用员工的用户名）
        Employees employees = employeesDao.findOneByUsername(username);
        if (employees==null){
            User user = userDao.findFirstByPhone(username);
            if (user!= null) {
                return jwtService.convertUserToJwtUser(user);
            }
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        else {
            return jwtService.convertUserToJwtEmployees(employees);
        }
    }
}
