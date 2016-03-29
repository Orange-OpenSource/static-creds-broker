/*
 *
 *  * Copyright (C) 2015 Orange
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package com.orange.model;

import org.junit.Assert;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

/**
 * Created by sbortolussi on 08/03/2016.
 */
public class ParsedCredentialsRepositoryTest {

    public static final String API_DIRECTORY_SERVICE = "API_DIRECTORY";
    public static final String DEV_PLAN = "dev";
    public static final String PROD_PLAN = "prod";
    public static final String DUMMY_PLAN = "dummy";

    public static final ServicePlanName SERVICE_PLAN_DEV= new ServicePlanName(API_DIRECTORY_SERVICE, DEV_PLAN);
    public static final ServicePlanName SERVICE_PLAN_PROD= new ServicePlanName(API_DIRECTORY_SERVICE, PROD_PLAN);
    public static final ServicePlanName SERVICE_PLAN_DUMMY= new ServicePlanName(API_DIRECTORY_SERVICE, DUMMY_PLAN);

    @Test
    public void should_get_credentials_that_have_been_set_for_associated_service_plan() throws Exception {
        ParsedCredentialsRepository credentialsRepository = new ParsedCredentialsRepository();
        //given credentials have been set for dev plan of service API_DIRECTORY
        credentialsRepository.save(API_DIRECTORY_SERVICE, DEV_PLAN,"CREDENTIALS_URI","http://mydev-api.org");
        credentialsRepository.save(API_DIRECTORY_SERVICE, DEV_PLAN,"CREDENTIALS_ACCESS_KEY","devAZERTY");
        //given credentials have been set for prod plan of service API_DIRECTORY
        credentialsRepository.save(API_DIRECTORY_SERVICE, PROD_PLAN,"CREDENTIALS_URI","http://myprod-api.org");
        credentialsRepository.save(API_DIRECTORY_SERVICE, PROD_PLAN,"CREDENTIALS_ACCESS_KEY","prodAZERTY");

        //when I get credentials that have been set for a service API_DIRECTORY instance whose plan is dev
        final Credentials credentialsForPlan = credentialsRepository.findByPlan(SERVICE_PLAN_DEV.getPlanUid());

        //then I should only get credentials that have been set for dev plan of service API_DIRECTORY
        assertThat(credentialsForPlan.toMap()).hasSize(2).includes(entry("CREDENTIALS_URI", "http://mydev-api.org"), entry("CREDENTIALS_ACCESS_KEY", "devAZERTY"));
    }

    @Test
    public void should_get_no_credentials_if_no_credentials_have_been_set_for_associated_service_plan() throws Exception {
    	ParsedCredentialsRepository credentialsRepository = new ParsedCredentialsRepository();
        //given credentials have been set for dev plan of service API_DIRECTORY
        credentialsRepository.save(API_DIRECTORY_SERVICE, DEV_PLAN,"CREDENTIALS_URI","http://mydev-api.org");
        credentialsRepository.save(API_DIRECTORY_SERVICE, DEV_PLAN,"CREDENTIALS_ACCESS_KEY","devAZERTY");
        //given credentials have been set for prod plan of service API_DIRECTORY
        credentialsRepository.save(API_DIRECTORY_SERVICE, PROD_PLAN,"CREDENTIALS_URI","http://myprod-api.org");
        credentialsRepository.save(API_DIRECTORY_SERVICE, PROD_PLAN,"CREDENTIALS_ACCESS_KEY","prodAZERTY");

        //when I get credentials that have been set for a service API_DIRECTORY instance whose plan is dummy
        final Credentials credentialsForPlan = credentialsRepository.findByPlan(SERVICE_PLAN_DUMMY.getPlanUid());

        //then I should get no credentials
        Assert.assertNull(credentialsForPlan);

    }

}