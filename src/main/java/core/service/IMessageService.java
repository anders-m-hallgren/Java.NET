package core.service;

public interface IMessageService extends IService{
	void sendMessage(String msg, String rec);
}

