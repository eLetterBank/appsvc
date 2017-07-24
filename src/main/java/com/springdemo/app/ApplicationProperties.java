package com.springdemo.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("app")
public class ApplicationProperties {
    private HttpHeader httpHeader;

    public ApplicationProperties.HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(ApplicationProperties.HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public static class HttpHeader  {
        private String vSolvNonce;
        private String vSolvSignature;

        @Override
        public String toString() {
            return "httpHeader{" +
                    "vSolvNonce=" + vSolvNonce +
                    ", vSolvSignature=" + vSolvSignature +
                    '}';
        }

        public void setvSolvNonce(String vSolvNonce) {
            this.vSolvNonce = vSolvNonce;
        }

        public String getvSolvNonce() {
            return vSolvNonce;
        }

        public void setvSolvSignature(String vSolvSignature) {
            this.vSolvSignature = vSolvSignature;
        }

        public String getvSolvSignature() {
            return vSolvSignature;
        }
    }

    @Override
    public String toString() {
        return "applicationProperties{" +
                httpHeader.toString() +
                '}';
    }
}
