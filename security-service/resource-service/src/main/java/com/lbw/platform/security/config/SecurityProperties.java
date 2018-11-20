package com.lbw.platform.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authorization.security")
public class SecurityProperties {
//是否是JSON返回错误代码模式 0,1:redirect 网页模式
private boolean jsonMode;

public boolean isJsonMode() {
	return jsonMode;
}

public void setJsonMode(boolean jsonMode) {
	this.jsonMode = jsonMode;
}

}
