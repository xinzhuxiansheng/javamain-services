package com.javamain.kafkaJMX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

public class JMXUtils {

    private static final Logger log = LoggerFactory.getLogger(JMXUtils.class);

    private JMXConnector jmxc = null;

    public MBeanServerConnection getMBeanServer(String jmxUrl) throws IOException {
        JMXServiceURL url = new JMXServiceURL(jmxUrl);
        jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        return mbsc;
    }

    public void close () {
        if (jmxc != null) {
            try {
                jmxc.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public String getUrl(String ip, String port) {
        return "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/jmxrmi";
    }
}