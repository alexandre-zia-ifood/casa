package org.gluu.casa.conf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jgomer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainSettings {

    @JsonProperty("enable_pass_reset")
    private boolean enablePassReset;

    @JsonProperty("use_branding")
    private boolean useExternalBranding;

    @JsonProperty("log_level")
    private String logLevel;

    @JsonProperty("acr_plugin_mapping")
    private Map<String, String> acrPluginMap;

    @JsonProperty("extra_css")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String extraCssSnippet;

    @JsonProperty("oxd_config")
    private OxdSettings oxdSettings;

    @JsonProperty("allowed_cors_domains")
    private List<String> corsDomains;

    @JsonProperty("basic_2fa_settings")
    private Basic2FASettings basic2FASettings = new Basic2FASettings();
    
    @JsonProperty("plugins_settings")
    private Map<String, Map<String,Object>> pluginSettings = new HashMap<>();

    public boolean isEnablePassReset() {
        return enablePassReset;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public boolean isUseExternalBranding() {
        return useExternalBranding;
    }

    public Map<String, String> getAcrPluginMap() {
        return acrPluginMap;
    }

    public String getExtraCssSnippet() {
        return extraCssSnippet;
    }

    public OxdSettings getOxdSettings() {
        return oxdSettings;
    }

    public List<String> getCorsDomains() {
        return corsDomains;
    }
    
    public Basic2FASettings getBasic2FASettings() {
    	return basic2FASettings;
    }

    public Map<String, Map<String, Object>> getPluginSettings() {
        return pluginSettings;
    }

    public void setEnablePassReset(boolean enablePassReset) {
        this.enablePassReset = enablePassReset;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public void setUseExternalBranding(boolean useExternalBranding) {
        this.useExternalBranding = useExternalBranding;
    }

    public void setAcrPluginMap(Map<String, String> acrPluginMap) {
        this.acrPluginMap = acrPluginMap;
    }

    public void setExtraCssSnippet(String extraCssSnippet) {
        this.extraCssSnippet = extraCssSnippet;
    }

    public void setOxdSettings(OxdSettings oxdSettings) {
        this.oxdSettings = oxdSettings;
    }

    public void setCorsDomains(List<String> corsDomains) {
        this.corsDomains = corsDomains;
    }

    public void setBasic2FASettings(Basic2FASettings basic2FASettings) {
    	this.basic2FASettings = basic2FASettings;
    }
    
    public void setPluginSettings(Map<String, Map<String, Object>> pluginSettings) {
        this.pluginSettings = pluginSettings;
    }

}
