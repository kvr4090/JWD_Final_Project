package by.epamtc.kalimylin.dao.connection;

import static by.epamtc.kalimylin.dao.util.MessageDAO.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Custom connection pool.
 */
public class ConnectionPool {
	
	public static final Logger logger = LogManager.getLogger();
	private static final int DEFAULT_POOL_SIZE = 8;
	private static AtomicBoolean isCreated = new AtomicBoolean();
	private static ConnectionPool instance = new ConnectionPool();
	private static Lock locker = new ReentrantLock(true);
	private BlockingQueue<ProxyConnection> freeConnection;
	private Queue<ProxyConnection> givenAwayConnections;

	private ConnectionPool() {
		
		freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
		givenAwayConnections = new ArrayDeque<>();
		
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				Connection connection = ConnectionFactory.getConnection();
				ProxyConnection proxyConnection = 
						new ProxyConnection(connection);
				boolean isAddded = freeConnection.add(proxyConnection);
				logger.log(Level.INFO, CONNECTION_ADDED + isAddded);
			} catch (SQLException e) {
				logger.log(Level.ERROR, 
						FAILED_CREATE_CONNECTION + e.getMessage());
			}
		}
		
		if (freeConnection.size() == 0) {
			logger.log(Level.FATAL, FAILED_CREATE_POOL + freeConnection.size());
			throw new RuntimeException(FAILED_CREATE_POOL);
		}
	}

	public static ConnectionPool getInstance() {
		
		if (!isCreated.get()) {
			locker.lock();
			if (instance == null) {
				instance = new ConnectionPool();
				isCreated.set(true);
			}
			locker.unlock();
		}
		logger.log(Level.DEBUG, CREATED_INSTANSE + instance);
		return instance;
	}

	public Connection getConnection() {
		
		Connection connection = null;
		
		try {
			connection = freeConnection.take();
			logger.log(Level.DEBUG, GAVE_CONNECTION + connection);
			givenAwayConnections.add((ProxyConnection) connection);
		} catch (InterruptedException e) {
			logger.log(Level.ERROR, I_E_GET_CONNECTION + e.getMessage());
			Thread.currentThread().interrupt();
		}
		return connection;
	}
	
	public void releaseResources(Connection connection, 
			Statement statement, ResultSet resultSet) {
		
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, RESULT_SET_CLOSE_ERROR + e.getMessage());
        }
        
        releaseResources(connection, statement); 
	}
	
	public void releaseResources(Connection connection, Statement statement) {
		
		try {
            if (connection != null) {
                connection.setAutoCommit(true);
                releaseConnection(connection);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, CLOSE_CLOSED_CONNECTION + e.getMessage());
        }
		
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, STATEMENT_CLOSE_ERROR + e.getMessage());
        }		
	}

	public void destroyPool() {
		
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				logger.log(Level.DEBUG, "destroyPool");
				freeConnection.take().reallyClose();
			} catch (InterruptedException e) {
				logger.log(Level.ERROR, I_E_DESTROY_POOL + e.getMessage());
			} catch (SQLException e) {
				logger.log(Level.ERROR, SQLEXC_DESTROY_POOL + e.getMessage());
			}
		}
		deregisterDrivers();
	}
	
	public void releaseConnection(Connection connection) {
		
		if (connection instanceof ProxyConnection 
				&& givenAwayConnections.remove(connection)) {
			
			logger.log(Level.DEBUG, RELEASE_CONNECTION + connection);
			
			try {
				freeConnection.put((ProxyConnection) connection);
			} catch (InterruptedException e) {
				logger.log(Level.ERROR, I_E_GET_CONNECTION + e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}

	private void deregisterDrivers() {
		
		DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
			
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				logger.log(Level.ERROR, 
						SQLEXC_DEREGISTER_DRIVERS + e.getMessage());
			}
		});
	}
}
