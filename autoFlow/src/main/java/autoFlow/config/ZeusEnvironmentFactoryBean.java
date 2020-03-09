package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.support.PropertiesLoaderSupport;

public class ZeusEnvironmentFactoryBean extends PropertiesLoaderSupport implements FactoryBean<Properties> {
    private Logger logger = LoggerFactory.getLogger(ZeusEnvironmentFactoryBean.class);

    public ZeusEnvironmentFactoryBean() {
    }

    public Properties getObject() throws Exception {
        Properties props = new Properties();
        Map<String, String> env = System.getenv();
        super.loadProperties(props);
        if (StringUtils.isNotEmpty((String)env.get("instanceName"))) {
            this.logger.info("发现instanceName环境变量,开始从环境变量取值.");
            props.putAll(System.getenv());
        }

        props.putAll(System.getProperties());
        String dpf = props.getProperty("decryptFieldName");
        if (dpf != null && dpf.trim().length() > 0) {
            String[] decryptFieldName = dpf.trim().split(",");

            for(int i = 0; i < decryptFieldName.length; ++i) {
                String s1 = props.getProperty(decryptFieldName[i]);
                String s2 = ThreeDes.decryptMode(decryptFieldName[i], s1);
                props.setProperty(decryptFieldName[i], s2);
            }
        }

        return props;
    }

    public Class<?> getObjectType() {
        return Properties.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
