package com.ex.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class managerReimbursementDecisionServiceTest {
	
	managerReimbursementDecisionService testObj;
	
	@Before
	public void setUp() throws Exception {
		testObj = new managerReimbursementDecisionService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		testObj = null;
		System.out.println("In tear down method");
	}


	@Test
	public void test() {

	}

}
