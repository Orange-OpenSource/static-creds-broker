package com.orange.model;

import org.fest.assertions.Assertions;
import org.junit.Test;

import java.net.URI;

import static org.fest.assertions.MapAssert.entry;
import static org.junit.Assert.*;

/**
 * Created by YSBU7453 on 28/04/2016.
 */
public class ServiceMetadataTest {

    @Test
    public void asMap() throws Exception {
        ServiceMetadata serviceMetadata = new ServiceMetadata();
        serviceMetadata.setDisplayName("aDisplayName");
        serviceMetadata.setLongDescription("a long description");
        serviceMetadata.setProviderDisplayName("a provider");
        serviceMetadata.setDocumentationUrl("http://localhost/doc");
        serviceMetadata.setImageUrl("http://localhost/image.png");
        serviceMetadata.setSupportUrl("http://localhost/support");

        Assertions.assertThat(serviceMetadata.asMap()).as("get service metatada content as a toServiceDefinition").hasSize(6).includes(
                entry("displayName", "aDisplayName"),
                entry("longDescription", "a long description"),
                entry("providerDisplayName", "a provider"),
                entry("documentationUrl", "http://localhost/doc"),
                entry("imageUrl", "http://localhost/image.png"),
                entry("supportUrl", "http://localhost/support"));
    }

}