package morrow.yaml;

import org.apache.commons.text.CaseUtils;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

class CamelCasePropertyUtils extends PropertyUtils {
    @Override
    public Property getProperty(Class<?> type, String name) {
        return super.getProperty(type, toCamelCase(name));
    }

    private String toCamelCase(String name) {
        return CaseUtils.toCamelCase(name, false, '-', '_');
    }
}
