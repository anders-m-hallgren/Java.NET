package core.service;

public interface IShortMessageService extends IService{
	void sendMessage(String msg, String rec);
}

