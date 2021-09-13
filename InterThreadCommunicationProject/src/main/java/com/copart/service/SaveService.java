package com.copart.service;

import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copart.entity.Emoloyee;
import com.copart.repository.EmployeeDataRepo;




@Service
public class SaveService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private EmployeeDataRepo dao;
	
	boolean navCallbackStatus=false;
	private class TimeOutTask extends TimerTask {
	    private Thread t;
	    private Timer timer;

	    TimeOutTask(Thread t, Timer timer){
	        this.t = t;
	        this.timer = timer;
	    }
	 
	    public void run() {
	        if (t != null && t.isAlive()) {
	            t.interrupt();
	            timer.cancel();
	        }
	    }
	}

@Transactional
class LongRunningTask implements Runnable {
	    @Override
	   //added comment here also
	    public void run() {
	        try {
	            while (!Thread.interrupted()&&!navCallbackStatus) {
	               Emoloyee emp= dao.getById(1);
	               System.out.println(emp);
	                
	                if(emp.getStatus().equalsIgnoreCase("TRUE")) {
	                //	if(true) {
	                	navCallbackStatus=true;
	                	return;
	                }
	                Thread.sleep(12*1000);
	            }
	        } catch (InterruptedException e) {
	            System.out.println("we cannot process further batches because wo dotn get response from the nav");
	            
	        }
	    } 
	}
	//we should not consider this timer concept
	public String saveData1() throws InterruptedException {
		Thread t = new Thread(new LongRunningTask());
		Timer timer = new Timer();
		timer.schedule(new TimeOutTask(t, timer), 200*1000);
		t.start();
		t.join();
		String dataSaved=saveData();
		if(navCallbackStatus) {
			navCallbackStatus=false;
			return dataSaved;
			
		}
		else { 
			navCallbackStatus=false;
			return "Data cannot process";
		}
	}

	private synchronized String saveData() throws InterruptedException {
		/*	
		new Thread() {
			public void run(){
				try {
					notifyThis();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		*/
		//wait(15000);
		return "Hello User";
	}
	/*
	private synchronized void notifyThis() throws InterruptedException {
		System.out.println("We have called this method");
		boolean navStatus=false;
		int x=4;
		while(!navStatus) {
			//hit database
			//check status
			//wait 5 sec
			if(x==3) {
				navStatus=true;
			}
			x++;
			//Thread.sleep(2000);
		}
		notify();
	}
	*/
	
	public String checkEmployeeUpdateStatus() {
		boolean isEmployeeUpdate=checkUpdateStatus(1);
		return isEmployeeUpdate?"Employee Record Has Updated":"Employee Record has not updated";
	}

	private boolean checkUpdateStatus(int i) {
		Emoloyee employee=null;
		boolean update=false;
		long startTime=System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime)<=(60*1000)) {
			
			employee=dao.getById(i);
			System.out.print(System.identityHashCode(employee));
			System.out.println(employee);
			if(employee.getStatus().equalsIgnoreCase("TRUE")) {
				update=true;
				break;
			}
			try {
			Thread.sleep(5*1000);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			entityManager.detach(employee);
			System.out.println("Every 10 sec it will print this");
			
		}//while
		return update;
	}
}
