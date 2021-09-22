package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;
import static by.epamtc.kalimylin.service.util.MessageService.*;

import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.ShipperDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import by.epamtc.kalimylin.service.util.MailSender;

/**
 * The class implements ShipperService interface methods
 */
public class ShipperServiceImpl implements ShipperService {
	
	@Override
	public boolean addShipper(Shipper shipper) throws ServiceException {
		
		checkShipper(shipper);
		
		DAOProvider provider = DAOProvider.getInstance();
		ShipperDAO shipperDAO = provider.getShipperDAO();
		Shipper similarShipper;
		
		try {
			similarShipper = shipperDAO.findShipperByName(shipper);
			
			if (similarShipper != null) {
				throw new ServiceException(ALREADY_IN_USE);
			}			
			return shipperDAO.addShipper(shipper);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void sendQuestionFromClient(Shipper shipper) 
			throws ServiceException {
		
		shipper.setName(DEFAULT_CLIENT_NAME);
		shipper.setContactPhone(DEFAULT_PHONE_NUMBER);		
		checkShipper(shipper);
		
		ResourceBundle mailPropertyBundle = 
				ResourceBundle.getBundle(MAIL_PROPERTIES);
		Properties properties = new Properties();
		properties.setProperty(MAIL_USER_NAME, 
				mailPropertyBundle.getString(MAIL_USER_NAME));

		String companyEmail = properties.getProperty(MAIL_USER_NAME);		
		MailSender.sendEmail(companyEmail, shipper.getContactPersonSurname(), 
				formMssageFromClient(shipper));
	}
	
	/**
	 * Forms message to send from client to company
	 * 
	 * @param shipper  author message
	 * @return {@link String} with message to send
	 */
	private String formMssageFromClient(Shipper shipper) {

		StringBuffer message = new StringBuffer();
		message.append(CLIENT).append(shipper.getContactPersonName())
				.append(ASK_US).append(shipper.getContactPersonSurname())
				.append(CLIENT_MESSAGE).append(shipper.getNote())
				.append(CLIENT_EMAIL).append(shipper.getEmail());
		
		return message.toString();
	}

	@Override
	public List<Shipper> findAllShippers() throws ServiceException {
		
		DAOProvider provider = DAOProvider.getInstance();
		ShipperDAO shipperDAO = provider.getShipperDAO();
		
		try {
			return shipperDAO.findAllShippers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
