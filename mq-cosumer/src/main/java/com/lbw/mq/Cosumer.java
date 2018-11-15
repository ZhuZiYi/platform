package com.lbw.mq;

import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("cosumer")
public class Cosumer {
	@JmsListener(destination = "qq") // queue名
	public void receiveQueue(String txt) {

		String name = ManagementFactory.getRuntimeMXBean().getName();
		//System.out.println(name);
		// get pid
		String pid = name.split("@")[0];
		System.out.println("receive:" + txt + " --- " + pid);
	}

}