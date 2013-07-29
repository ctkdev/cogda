package com.cogda.domain

import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Mock([CompanyProfile, Company, CompanyType])
@TestMixin([DomainClassUnitTestMixin])
class CompanyProfileSpec extends Specification{

    Company rootCompany
    CompanyProfile companyProfile
    CompanyType companyType

    void setupSpec() {
        // Setup logic here
        def looseCompanyMock = mockFor(Company, true)
        looseCompanyMock.demand.retrieveRootCompany { Company.findByParentCompany(null) }
    }

    void setup(){
        companyType = new CompanyType()
        companyType.intCode = 0
        companyType.code = "MGA"
        companyType.description = "MGA"
        companyType.save(failOnError: true)
    }

    void cleanup() {
        // Tear down logic here
    }

    def "getRootCompanyProfile should return the root company's company profile"() {
        given:
        companyProfile = new CompanyProfile()
        companyProfile.company = rootCompany
        companyProfile.companyDescription = "lsllsls"
        companyProfile.published = true
        companyProfile.companyWebsite = "http://www.google.com"

        rootCompany = new Company()
        rootCompany.companyName = "Company"
        rootCompany.doingBusinessAs = "A Company"
        rootCompany.intCode = 0
        rootCompany.companyProfile = companyProfile
        rootCompany.companyType = companyType
        rootCompany.save(failOnError:true)
        companyProfile.company = rootCompany
        companyProfile.save(failOnError:true)

        5.times { Integer i ->

            Company c = new Company()
            c.companyName = "$i Company"
            c.doingBusinessAs = "$i Company"
            c.intCode = i
            c.parentCompany = rootCompany
            c.companyType = companyType

            CompanyProfile cp = new CompanyProfile()
            cp.company = c
            cp.companyDescription = "$i"
            cp.published = true
            cp.companyWebsite = "http://www.google.com"

            c.companyProfile = cp
            assert c.save()
            assert cp.save()

        }

        expect:
            CompanyProfile.rootCompanyProfile == companyProfile
            Company.findAllByParentCompany(rootCompany).size() == 5
    }
}
