package com.cogda.multitenant

import com.cogda.domain.UserProfile
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountContact
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountContact {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * If the AccountContact is on Cogda then they will
     * have a UserProfile on Cogda.
     * If the AccountContact is not on Cogda then they
     * will have to have the information added manually by
     * the user.
     */
    UserProfile userProfile

    /**
     * First name
     */
    String firstName

    /**
     * Middle name
     */
    String middleName

    /**
     * Last name
     */
    String lastName

    /**
     * Primary contact
     */
    Boolean primaryContact

    static hasMany = [accountContactAddresses:AccountContactAddress,
            accountContactEmailAddresses:AccountContactEmailAddress,
            accountContactPhoneNumbers:AccountContactPhoneNumber]

    static transients = ["fullName","primaryEmailAddress","primaryPhoneNumber" ]

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [account:Account]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.


    static constraints = {
        userProfile(nullable: true)
        firstName(nullable:false, blank:false)
        middleName(nullable:true)
        lastName(nullable:false, blank:false)
        primaryContact(nullable:true)
    }

    /**
     * Gets the full name - lastName, firstName
     * @return String
     */
    String getFullName(){
        return "${lastName}, ${firstName} ${middleName}"
    }

    /**
     * Retrieves the primary AccountContactEmailAddress
     * @return String emailAddress
     */
    public String getPrimaryAccountEmailAddress(){
        AccountContactEmailAddress accountContactEmailAddress = this.accountContactEmailAddresses?.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return accountContactEmailAddress.emailAddress
    }

    /**
     * Retrieves the primary AccountContactPhoneNumber
     * @return String phoneNumber
     */
    public String getPrimaryAccountPhoneNumber(){
        AccountContactPhoneNumber accountContactPhoneNumber = this.accountContactPhoneNumbers?.find {
            it.primaryPhoneNumber == Boolean.TRUE
        }
        return accountContactPhoneNumber.phoneNumber
    }


}
