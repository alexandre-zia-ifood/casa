package org.gluu.casa.ui.vm.user;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gluu.casa.core.pojo.BrowserInfo;
import org.gluu.casa.core.pojo.FidoDevice;
import org.gluu.casa.core.pojo.PlatformAuthenticator;
import org.gluu.casa.core.pojo.SecurityKey;
import org.gluu.casa.misc.Utils;
import org.gluu.casa.plugins.authnmethod.SecurityKey2Extension;
import org.gluu.casa.plugins.authnmethod.service.Fido2Service;
import org.gluu.casa.ui.UIUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.json.JSONObject;
import org.zkoss.json.JavaScriptValue;
import org.zkoss.util.Pair;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.au.out.AuInvoke;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the ViewModel of page fido2-detail.zul. It controls the CRUD of
 * security keys
 */
public class SecurityKey2ViewModel extends UserViewModel {

	private static final int REGISTRATION_TIMEOUT = 8000;

	private Logger logger = LogManager.getLogger(getClass());

	@WireVariable
	private Fido2Service fido2Service;

	private FidoDevice newDevice;
	private FidoDevice newTouchId;
	private List<FidoDevice> devices;
	private String fido2SupportMessage;

	private String editingId;
	private boolean uiAwaiting;
	private boolean uiEnrolled;

	private String editingIdPlatformAuthenticator;
	private boolean uiAwaitingPlatformAuthenticator;
	private boolean uiEnrolledPlatformAuthenticator;

	private boolean platformAuthenticator;
	
	private boolean showUIPlatformAuthenticator;

	private ObjectMapper mapper;

	public boolean isShowUIPlatformAuthenticator() {
		return showUIPlatformAuthenticator;
	}

	public void setShowUIPlatformAuthenticator(boolean showUIPlatformAuthenticator) {
		this.showUIPlatformAuthenticator = showUIPlatformAuthenticator;
	}

	public FidoDevice getNewDevice() {
		return newDevice;
	}

	public List<FidoDevice> getDevices() {
		return devices;
	}

	public String getFido2SupportMessage() {
		return fido2SupportMessage;
	}

	public boolean getPlatformAuthenticator() {
		return platformAuthenticator;
	}

	public void setPlatformAuthenticator(boolean platformAuthenticator) {
		this.platformAuthenticator = platformAuthenticator;
	}

	public String getEditingId() {
		return editingId;
	}

	public FidoDevice getNewTouchId() {
		return newTouchId;
	}

	public void setNewTouchId(FidoDevice newTouchId) {
		this.newTouchId = newTouchId;
	}

	public void setNewDevice(FidoDevice newDevice) {
		this.newDevice = newDevice;
	}

	public boolean isUiAwaiting() {
		return uiAwaiting;
	}

	public boolean isUiEnrolled() {
		return uiEnrolled;
	}

	public String getEditingIdPlatformAuthenticator() {
		return editingIdPlatformAuthenticator;
	}

	public boolean isUiAwaitingPlatformAuthenticator() {
		return uiAwaitingPlatformAuthenticator;
	}

	public boolean isUiEnrolledPlatformAuthenticator() {
		return uiEnrolledPlatformAuthenticator;
	}

	@Init(superclass = true)
	public void childInit() throws Exception {
		logger.debug("childInit");
		mapper = new ObjectMapper();
		newDevice = new SecurityKey();
		newTouchId = new PlatformAuthenticator();
		devices = fido2Service.getDevices(user.getId(), true);
		checkFido2Support();

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		logger.debug("afterCompose");
		Selectors.wireEventListeners(view, this);

	}

	public void triggerAttestationRequestPlatformAuthenticator()
	{
		platformAuthenticator = true;
		triggerAttestationRequest();
	}
	
	public void triggerAttestationRequest() {
		logger.debug("triggerAttestationRequest : "+platformAuthenticator);
		try {

			if (platformAuthenticator) {
				uiAwaitingPlatformAuthenticator = true;
				BindUtils.postNotifyChange(this, "uiAwaitingPlatforAuthenticator");
			} else {
				uiAwaiting = true;
				BindUtils.postNotifyChange(this, "uiAwaiting");
			}
			String uid = user.getUserName();
			String jsonRequest = fido2Service.doRegister(uid, Optional.ofNullable(user.getGivenName()).orElse(uid),
					platformAuthenticator);
			logger.debug("JSONrequest - " + jsonRequest);
			// Notify browser to exec proper function
			UIUtils.showMessageUI(Clients.NOTIFICATION_TYPE_INFO, Labels.getLabel("usr.fido2_touch"));
			Clients.response(
					new AuInvoke(platformAuthenticator? "triggerFido2AttestationPA" : "triggerFido2Attestation", new JavaScriptValue(jsonRequest), REGISTRATION_TIMEOUT));
		} catch (Exception e) {
			UIUtils.showMessageUI(false);
			logger.error(e.getMessage(), e);
		}

	}

	@Listen("onData=#readyButton")
	public void notified(Event event) throws Exception {
		logger.debug("notified ready" + event.getTarget());
		String errMessage = null;
		try {
			if (fido2Service.verifyRegistration(mapper.writeValueAsString(event.getData()))) {

				if (platformAuthenticator) {
					newTouchId = fido2Service.getLatestSecurityKey(user.getId(), System.currentTimeMillis());
					if (newTouchId != null) {
						uiEnrolledPlatformAuthenticator = true;
						BindUtils.postNotifyChange(this, "uiEnrolledPlatformAuthenticator");

						uiAwaitingPlatformAuthenticator = false;
						BindUtils.postNotifyChange(this, "uiAwaitingPlatformAuthenticator");
					} else {
						errMessage = Labels.getLabel("general.error.general");
					}
				} else {
					// pick the most suitable recent entry
					newDevice = fido2Service.getLatestSecurityKey(user.getId(), System.currentTimeMillis());
					if (newDevice != null) {

						uiEnrolled = true;
						BindUtils.postNotifyChange(this, "uiEnrolled");

						uiAwaiting = false;
						BindUtils.postNotifyChange(this, "uiAwaiting");
					} else {
						errMessage = Labels.getLabel("general.error.general");
					}

				}

			} else {
				errMessage = Labels.getLabel("usr.fido2.error_invalid");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMessage = Labels.getLabel("general.error.detailed", new String[] { e.getMessage() });
		}

		if (errMessage != null) {
			UIUtils.showMessageUI(false, errMessage);
		}

	}

	@Listen("onData=#readyPlatformButton")
	public void notifiedPlatform(Event event) throws Exception {
		logger.debug("notified platform ready" + event.getTarget());
		notified(event);
	}

	@Listen("onError=#readyPlatformButton")
	public void notifiedErrPlatform(Event event) throws Exception {
		logger.debug("notified notifiedErrPlatform" + event.getTarget());
		notifiedErr(event);
	}

	@Listen("onError=#readyButton")
	public void notifiedErr(Event event) throws Exception {
		logger.debug("notifiedErr - " + event.getTarget());
		JSONObject jsonObject = (JSONObject) event.getData();
		boolean excludedCredentials = (boolean) jsonObject.get("excludeCredentials");
		String name = Optional.ofNullable(jsonObject.get("name")).map(Object::toString).orElse("");
		String msg = Optional.ofNullable(jsonObject.get("message")).map(Object::toString).orElse("");

		String message;
		logger.error("An error occurred when enrolling fido2 cred for user {}. {}: {}", user.getUserName(), name, msg);

		if (name.equals("NotAllowedError")) {
			message = Labels.getLabel(excludedCredentials ? "usr.fido2.error_exclude" : "general.error.general");
		} else if (name.equals("AbortError")) {
			message = Labels.getLabel("usr.fido2.error_cancel");
		} else {
			message = Labels.getLabel("general.error.detailed", new String[] { msg });
		}
		if (platformAuthenticator) {
			uiAwaitingPlatformAuthenticator = false;
			BindUtils.postNotifyChange(this, "uiAwaitingPlatformAuthenticator");
		} else {
			uiAwaiting = false;
			BindUtils.postNotifyChange(this, "uiAwaiting");
		}
		UIUtils.showMessageUI(false, message);

	}

	@NotifyChange({ "uiEnrolled", "uiEnrolledPlatformAuthenticator", "newDevice", "newTouchId", "devices" })
	public void add() {
		logger.debug("add - ");
		FidoDevice dev = null;
		if (platformAuthenticator && Utils.isNotEmpty(newTouchId.getNickName())) {
			dev = newTouchId;
		} else if (Utils.isNotEmpty(newDevice.getNickName())) {
			dev = newDevice;
		}
		if (dev != null) {
			try {
				fido2Service.updateDevice(dev);
				devices.add(dev);
				UIUtils.showMessageUI(true, Labels.getLabel("usr.enroll.success"));
				userService.notifyEnrollment(user, SecurityKey2Extension.ACR);
			} catch (Exception e) {
				UIUtils.showMessageUI(false, Labels.getLabel("usr.error_updating"));
				logger.error(e.getMessage(), e);
			}
			resetAddSettings();
		}

	}

	@NotifyChange({ "uiEnrolled", "uiEnrolledPlatformAuthenticator", "newDevice", "newTouchId" })
	public void cancel() {
		logger.info("cancel invoked");
		boolean success = false;
		try {
			/*
			 * Remove the recently enrolled key. This is so because once the user touches
			 * his key button, oxAuth creates the corresponding entry in LDAP, and if the
			 * user regrets adding the current key by not supplying a nickname (and thus
			 * pressing cancel), we need to be obliterate the entry
			 */
			FidoDevice dev = null;
			if (platformAuthenticator && Utils.isNotEmpty(newTouchId.getNickName())) {
				dev = newTouchId;
			} else if (Utils.isNotEmpty(newDevice.getNickName())) {
				dev = newDevice;
			}
			if (dev != null) {
				success = fido2Service.removeDevice(dev);
			}
		} catch (Exception e) {
			success = false;
			logger.error(e.getMessage(), e);
		}
		if (!success) {
			UIUtils.showMessageUI(false);
		}
		resetAddSettings();

	}

	@NotifyChange({ "editingId", "editingIdPlatformAuthenticator", "newDevice" })
	public void prepareForUpdate(FidoDevice dev) {
		logger.debug("prepareForUpdate");
		// This will make the modal window to become visible
		editingId = dev.getId();
		newDevice = new FidoDevice();
		newDevice.setNickName(dev.getNickName());
	}

	@NotifyChange({ "editingId", "editingIdPlatformAuthenticator", "newDevice" })
	public void cancelUpdate(Event event) {
		logger.debug("cancelUpdate");
		newDevice.setNickName(null);
		editingId = null;
		if (event != null && event.getName().equals(Events.ON_CLOSE)) {
			event.stopPropagation();
		}
	}

	@NotifyChange({ "devices", "editingId", "editingIdPlatformAuthenticator", "newDevice" })
	public void update() {
		logger.debug("update");
		String nick = newDevice.getNickName();

		if (Utils.isNotEmpty(nick)) {

			int i = Utils.firstTrue(devices, dev -> dev.getId().equals(editingId));
			FidoDevice dev = devices.get(i);
			dev.setNickName(nick);
			cancelUpdate(null);

			try {
				fido2Service.updateDevice(dev);
				UIUtils.showMessageUI(true);
			} catch (Exception e) {
				UIUtils.showMessageUI(false);
				logger.error(e.getMessage(), e);
			}
		}

	}

	public void delete(FidoDevice device) {
		logger.debug("delete invoked");
		String resetMessages = resetPreferenceMessage(SecurityKey2Extension.ACR, devices.size());
		boolean reset = resetMessages != null;
		Pair<String, String> delMessages = getDeleteMessages(device.getNickName(), resetMessages);

		Messagebox.show(delMessages.getY(), delMessages.getX(), Messagebox.YES | Messagebox.NO,
				reset ? Messagebox.EXCLAMATION : Messagebox.QUESTION, event -> {
					if (Messagebox.ON_YES.equals(event.getName())) {
						try {
							devices.remove(device);
							boolean success = fido2Service.removeDevice(device);

							if (success) {
								if (reset) {
									userService.turn2faOff(user);
								}
								// trigger refresh (this method is asynchronous...)
								BindUtils.postNotifyChange(SecurityKey2ViewModel.this, "devices");
							} else {
								devices.add(device);
							}
							UIUtils.showMessageUI(success);
						} catch (Exception e) {
							UIUtils.showMessageUI(false);
							logger.error(e.getMessage(), e);
						}
					}
				});
	}

	private void resetAddSettings() {
		logger.debug("resetAddSettings");
		uiEnrolled = false;
		uiEnrolledPlatformAuthenticator = false;
		newDevice = new SecurityKey();
		newTouchId = new PlatformAuthenticator();
	}

	private void checkFido2Support() {
		logger.debug("checkFido2Support");
		boolean probablySupported = false;
		try {
			BrowserInfo binfo = getBrowserInfo();
			String name = binfo.getName().toLowerCase();
			int browserVer = binfo.getMainVersion();

			probablySupported = (name.contains("edge") && browserVer >= 18)
					|| (name.contains("firefox") && browserVer >= 64) || (name.contains("chrome") && browserVer >= 71)
					|| (name.contains("opera") && browserVer >= 54);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!probablySupported) {
			fido2SupportMessage = Labels.getLabel("usr.fido2_unsupported_browser");
		}

	}

	@Listen("onData=#platformAuthenticator")
	public void updatePlatform(Event event) throws Exception {
		
		showUIPlatformAuthenticator = Boolean.valueOf(event.getData().toString());
		logger.debug("updatePlatform");
		BindUtils.postNotifyChange(this, "showUIPlatformAuthenticator");

	}

}
