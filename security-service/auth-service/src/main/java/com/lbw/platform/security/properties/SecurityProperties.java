package com.lbw.platform.security.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2018-02-24.
 */
@ConfigurationProperties(prefix = "qh.security")
public class SecurityProperties {
    /**
     * 浏览器环境配置
     */
    private  BrowesProperties brwes = new BrowesProperties();

    private  ValidateCodeProperties valiCode = new ValidateCodeProperties();
    
    private String clientId;
    
    private String clientSecurit;
    

    public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecurit() {
		return clientSecurit;
	}

	public void setClientSecurit(String clientSecurit) {
		this.clientSecurit = clientSecurit;
	}

	public ValidateCodeProperties getValiCode() {
        return valiCode;
    }

    public void setValiCode(ValidateCodeProperties valiCode) {
        this.valiCode = valiCode;
    }
    
    public BrowesProperties getBrwes() {
        return brwes;
    }

    public void setBrwes(BrowesProperties brwes) {
        this.brwes = brwes;
    }

}
