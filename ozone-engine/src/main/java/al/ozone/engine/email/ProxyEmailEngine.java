package al.ozone.engine.email;

import al.ozone.bl.model.Email;

public interface ProxyEmailEngine {

	void addEmail(Email email);

	void init();

	void stopRunningSucceded();

}
