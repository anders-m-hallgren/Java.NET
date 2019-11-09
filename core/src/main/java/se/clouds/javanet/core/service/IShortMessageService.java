package se.clouds.javanet.core.service;

public interface IShortMessageService extends IService{
	void sendMessage(String msg, String rec);
}

