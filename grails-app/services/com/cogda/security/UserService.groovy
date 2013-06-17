package com.cogda.security

import com.cogda.domain.UserProfile
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.multitenant.CustomerAccount

/**
 * UserService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserService {

    static transactional = true

    /**
     * Creates a User domain class object in the database.
     * @param user
     * @return
     */
    def create(User user) {
        user.save() ?: log.error("Error saving User errors -> ${user.errors}")
        return user
    }

    /**
     * Creates a new User with the specified roles.
     * @param user
     * @param roles
     * @return
     */
    def create(User user, List<Role> roles){
        create(user)
        if(!user.hasErrors()){
            roles.each { Role role ->
                UserRole.create(user, role)
            }
        }else{
            log.error ("Unable to apply roles to User due to validation errors on User errors -> ${user.errors}")
        }
    }

    def createWithStringRoles(User user, List<String> roles){
        create(user)

        roles.each { String authority ->
            UserRole.create(user, Role.findByAuthority(authority))
        }
    }

    /**
     * Checks for an available Username across all of the
     * Users stored in the system.
     * @param username
     * @return boolean
     */
    def availableUsername(String username){
        Boolean available = Boolean.TRUE
        CustomerAccount.withoutTenantRestriction{
            if(User.findByUsername(username)){
                available = Boolean.FALSE
            }
        }

        if(!available){
            return Boolean.FALSE
        }

        // Now check Registration
        if(Registration.findByUsername(username)){
            return Boolean.FALSE
        }

        return Boolean.TRUE
    }

    /**
     * Creates a user based on the passed in parameters.
     * encodePassword boolean if set to true
     * @param username
     * @param password
     * @param encodePassword
     * @return
     */
    def createUser(String username, String password, boolean encodePassword = false){
        // Create the user
        User user = new User()
        user.username = username
        user.password = password
        user.enabled = true
        user.accountExpired = false
        user.accountLocked = false
        user.passwordExpired = false
        user.encodePassword = false  // do not allow the password to be re-encoded
        user.save() ?: log.error ("Error saving User errors -> ${user.errors}")
        return user
    }
}
