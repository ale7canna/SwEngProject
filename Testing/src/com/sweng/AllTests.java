package com.sweng;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ AddProjectTest.class, AddUserTest.class, RemoveProjectTest.class, CompleteProjectTest.class })
public class AllTests {

}
